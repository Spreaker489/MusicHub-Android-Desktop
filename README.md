# MusicHub V1.2.1 Gecko

MusicHub Classic is an Android wrapper for the MusicHub web player using Mozilla GeckoView.

- Android 7.0 (API 24) or newer.
- One Classic APK, retaining ARM and x86 ABI support.
- GeckoView adapts to phone and tablet screen sizes.
- Foreground media service exposes lock-screen and notification transport controls.
- Uses zrok's documented `skip_zrok_interstitial: 1` request header only.

## Build and optimization

The `musichub-classic-optimization` branch is used for safe packaging work. The stable `main` branch is not changed by this optimization work until the APK has been tested.

The Classic build:
- Keeps all four supported ABIs (`armeabi-v7a`, `arm64-v8a`, `x86`, and `x86_64`) in one APK.
- Compresses native GeckoView libraries inside the APK. Android extracts these libraries during installation, so the downloaded APK can be smaller while installed storage usage may remain similar.
- Enables R8 code shrinking and resource shrinking for the release build.
- Keeps GeckoView classes conservatively to reduce the risk of breaking its runtime/JNI integration.

GitHub Actions builds:
- `musichub-classic-debug`: installable debug APK for functional testing.
- `musichub-classic-release-unsigned`: optimized release APK; it must be signed with the developer's own release key before distribution.

The workflow reports the generated APK file sizes. Do not treat the release build as validated until background playback, media notifications, lock-screen artwork, and transport controls have been tested on a physical Android device.

## Despre aplicație

MusicHub 1.2.1 aduce playerul web MusicHub într-o aplicație Android dedicată, folosind motorul Mozilla GeckoView.

Aplicația include comenzi pentru piesa anterioară, redare/pauză și piesa următoare, precum și afișarea informațiilor despre melodie și a copertei albumului în notificare și pe ecranul de blocare, atunci când playerul și dispozitivul permit acest lucru.

Configurația proiectului folosește `minSdk 24` (Android 7.0 sau mai nou).

**Ediție:** MusicHub Classic – un singur APK universal  
**Versiune documentată:** 1.2.1  
**Dezvoltator:** marius_489_spk

## About the app

MusicHub 1.2.1 brings the MusicHub web player into a dedicated Android application powered by Mozilla GeckoView.

The app includes previous-track, play/pause, and next-track controls, together with track information and album artwork in the media notification and on the lock screen whenever supported by the player and device.

**Edition:** MusicHub Classic – one universal APK  
**Documented version:** 1.2.1  
**Developer:** marius_489_spk
