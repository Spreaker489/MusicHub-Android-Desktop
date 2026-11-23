package com.musichub.geckoview;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import org.mozilla.geckoview.GeckoRuntime;
import org.mozilla.geckoview.GeckoSession;
import org.mozilla.geckoview.GeckoSessionSettings;
import org.mozilla.geckoview.GeckoView;
import org.mozilla.geckoview.MediaSession;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    private static final String HOME_URL = "https://musichub.shares.zrok.io";
    private static final Handler MAIN = new Handler(Looper.getMainLooper());
    private static GeckoRuntime runtime;
    private static GeckoSession session;
    private static MediaSession activeMedia;
    private static double pendingRestorePositionSeconds = -1.0;
    private static boolean restoreAttempted = false;

    @Override protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= 33 &&
                checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 42);
        }
        Intent service = new Intent(this, MusicPlaybackService.class);
        if (Build.VERSION.SDK_INT >= 26) startForegroundService(service); else startService(service);

        if (runtime == null) runtime = GeckoRuntime.create(getApplicationContext());
        if (session == null) {
            GeckoSessionSettings settings = new GeckoSessionSettings.Builder()
                    .userAgentOverride("MusicHubAndroid/1.2.1").build();
            session = new GeckoSession(settings);
            // Recover automatically if Android or Gecko kills the content process
            // while MusicHub is in the background. GeckoView documents that the
            // session is closed after onCrash/onKill and can be reopened.
            session.setContentDelegate(new GeckoSession.ContentDelegate() {
                @Override public void onCrash(@NonNull GeckoSession crashedSession) {
                    recoverGeckoSession(crashedSession);
                }
                @Override public void onKill(@NonNull GeckoSession killedSession) {
                    recoverGeckoSession(killedSession);
                }
            });
            session.setMediaSessionDelegate(new MediaSession.Delegate() {
                @Override public void onActivated(@NonNull GeckoSession s, @NonNull MediaSession media) {
                    activeMedia = media;
                }
                @Override public void onDeactivated(@NonNull GeckoSession s, @NonNull MediaSession media) {
                    if (activeMedia == media) activeMedia = null;
                }
                @Override public void onMetadata(@NonNull GeckoSession s, @NonNull MediaSession media,
                                                 @NonNull MediaSession.Metadata metadata) {
                    activeMedia = media;
                    MusicPlaybackService svc = MusicPlaybackService.getInstance();
                    if (svc != null) {
                        if (!restoreAttempted && metadata.title != null &&
                                !metadata.title.isEmpty() && !"MusicHub".equals(metadata.title)) {
                            long savedPosition = svc.getSavedPositionForTrack(metadata.title, metadata.artist);
                            if (savedPosition > 0L) pendingRestorePositionSeconds = savedPosition / 1000.0;
                            restoreAttempted = true;
                        }
                        svc.updateMetadata(metadata.title, metadata.artist, metadata.album);
                        if (metadata.artwork != null) {
                            metadata.artwork.getBitmap(512).accept(
                                    bitmap -> {
                                        MusicPlaybackService current = MusicPlaybackService.getInstance();
                                        if (current != null) current.updateArtwork(bitmap);
                                    },
                                    error -> android.util.Log.w("MusicHub", "Could not load track artwork", error));
                        } else {
                            svc.updateArtwork(null);
                        }
                    }
                }
                @Override public void onPlay(@NonNull GeckoSession s, @NonNull MediaSession media) {
                    activeMedia = media;
                    MusicPlaybackService svc = MusicPlaybackService.getInstance();
                    if (svc != null) svc.updatePlaybackState(true);
                }
                @Override public void onPause(@NonNull GeckoSession s, @NonNull MediaSession media) {
                    activeMedia = media;
                    MusicPlaybackService svc = MusicPlaybackService.getInstance();
                    if (svc != null) svc.updatePlaybackState(false);
                }
                @Override public void onStop(@NonNull GeckoSession s, @NonNull MediaSession media) {
                    activeMedia = media;
                    MusicPlaybackService svc = MusicPlaybackService.getInstance();
                    if (svc != null) svc.updatePlaybackState(false);
                }
                @Override public void onPositionState(@NonNull GeckoSession s, @NonNull MediaSession media,
                                                       @NonNull MediaSession.PositionState state) {
                    activeMedia = media;
                    MusicPlaybackService svc = MusicPlaybackService.getInstance();
                    if (pendingRestorePositionSeconds >= 0.0 && state.duration > pendingRestorePositionSeconds) {
                        // Restore only when the site's current track matches the last saved title/artist.
                        // Seeking does not start playback.
                        media.seekTo(pendingRestorePositionSeconds, false);
                        pendingRestorePositionSeconds = -1.0;
                    }
                    if (svc != null) {
                        svc.updatePositionState(state.duration, state.position, state.playbackRate);
                    }
                }
            });
            session.open(runtime);
            // Documented zrok interstitial opt-in only; it does not bypass authentication.
            session.load(new GeckoSession.Loader().uri(HOME_URL)
                    .headerFilter(GeckoSession.HEADER_FILTER_UNRESTRICTED_UNSAFE)
                    .additionalHeaders(Collections.singletonMap("skip_zrok_interstitial", "1")));
        }
        GeckoView view = findViewById(R.id.geckoview);
        view.setSession(session);
        MusicPlaybackService.setCommandListener(new MusicPlaybackService.CommandListener() {
            @Override public void play() { MAIN.post(() -> { if (activeMedia != null) activeMedia.play(); }); }
            @Override public void pause() { MAIN.post(() -> { if (activeMedia != null) activeMedia.pause(); }); }
            @Override public void next() { MAIN.post(() -> { if (activeMedia != null) activeMedia.nextTrack(); }); }
            @Override public void previous() { MAIN.post(() -> { if (activeMedia != null) activeMedia.previousTrack(); }); }
        });
    }
    private void recoverGeckoSession(@NonNull GeckoSession crashedSession) {
        // ContentDelegate callbacks run on the UI thread.
        if (runtime == null || crashedSession != session) return;
        try {
            crashedSession.open(runtime);
            crashedSession.load(new GeckoSession.Loader().uri(HOME_URL)
                    .headerFilter(GeckoSession.HEADER_FILTER_UNRESTRICTED_UNSAFE)
                    .additionalHeaders(Collections.singletonMap("skip_zrok_interstitial", "1")));
        } catch (RuntimeException error) {
            android.util.Log.e("MusicHub", "Could not recover GeckoView after content process exit", error);
        }
    }

    @Override protected void onResume() {
        super.onResume();
        if (session != null) {
            session.setActive(true);
            session.setFocused(true);
        }
    }

    @Override protected void onPause() {
        if (session != null) session.setFocused(false);
        super.onPause();
    }

    @Override protected void onStop() {
        // The page is no longer visible, but keep the Gecko session alive so
        // background audio and notification controls can continue.
        if (session != null) session.setActive(false);
        super.onStop();
    }

    @Override protected void onDestroy() {
        // Detach the persistent GeckoSession from this Activity's view before
        // the Activity is destroyed. Keep the session/runtime alive so audio
        // playback and its media state can continue in the background.
        GeckoView view = findViewById(R.id.geckoview);
        if (view != null) {
            view.setSession(null);
        }
        super.onDestroy();
    }
}
