# MusicHub V1.2.1 Gecko

MusicHub Classic is an Android wrapper for the MusicHub web player using Mozilla GeckoView.

- Android 7.0 (API 24) or newer.
- One universal Classic APK with ARM and x86 ABI support.
- GeckoView adapts to phone and tablet screen sizes.
- Foreground media service provides notification and lock-screen controls when supported by Android and the player.
- Uses zrok's documented `skip_zrok_interstitial: 1` request header.

## Build and distribution

The stable source is maintained on the `main` branch. GitHub Actions builds the Debug APK only using `gradle assembleDebug --no-daemon --stacktrace`.

On every push to `main`, the workflow builds the APK, publishes it as a GitHub Actions artifact, and replaces the asset in the existing GitHub Release `v1.2.1`. The Release receives the `.apk` file itself, not the Actions artifact ZIP.

The published file is named `MusicHub-1.2.1-Classic-Universal.apk`. No Release build, R8 shrinking, or APK size optimization is performed. Physical-device behavior must be tested separately.

## Despre aplicație

MusicHub Classic aduce playerul web MusicHub într-o aplicație Android dedicată, folosind motorul Mozilla GeckoView.

Aplicația oferă comenzi media în notificare și pe ecranul de blocare, atunci când playerul și versiunea Android le permit. Proiectul folosește `minSdk 24` (Android 7.0 sau mai nou).

**Ediție:** MusicHub Classic – APK universal  
**Versiune documentată:** 1.2.1  
**Dezvoltator:** marius_489_spk

### Compilare și distribuție

Workflow-ul GitHub Actions compilează numai varianta Debug. La fiecare actualizare pe `main`, APK-ul este publicat ca artefact și înlocuiește fișierul APK din Release-ul existent `v1.2.1`. În Release se încarcă fișierul `.apk` direct, nu arhiva ZIP. Nu se execută build Release sau optimizări de dimensiune.

## About the app

MusicHub Classic brings the MusicHub web player into a dedicated Android application powered by Mozilla GeckoView.

The app provides media controls in the notification and on the lock screen when supported by the player and Android version. The project uses `minSdk 24` (Android 7.0 or newer).

**Edition:** MusicHub Classic – universal APK   
**Documented version:** 1.2.1  
**Developer:** marius_489_spk

### Build and distribution

GitHub Actions builds the Debug variant only. On each update to `main`, the APK is published as a workflow artifact and replaces the APK file in the existing `v1.2.1` Release. The Release receives the `.apk` file directly, not the Actions artifact ZIP. No Release build or APK size optimization is performed.
