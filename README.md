# MusicHub V1.2.1 Gecko

MusicHub Classic is an Android wrapper for the MusicHub web player using Mozilla GeckoView.

- Android 7.0 (API 24) or newer.
- One universal Classic APK with ARM and x86 ABI support.
- GeckoView adapts to phone and tablet screen sizes.
- Foreground media service provides notification and lock-screen controls when supported by Android and the player.
- Uses zrok's documented `skip_zrok_interstitial: 1` request header.

## Build and distribution

The `main` branch is the single source of truth. GitHub Actions builds the **Debug APK only** with `gradle assembleDebug --no-daemon --stacktrace`.

On pushes to `main`, the workflow:
1. Builds the universal Debug APK.
2. Uploads `musichub-classic-debug` as a GitHub Actions artifact (downloaded by GitHub as a ZIP containing the APK).
3. Replaces the APK asset in the existing GitHub Release `v1.2.1`.

The Release contains the `.apk` file directly, not a ZIP. The current published file is:

- **File:** `MusicHub-1.2.1-Classic-Universal.apk`
- **Size:** 335,524,315 bytes (about 320 MiB / 335.5 MB)
- **Download:** [GitHub Release v1.2.1](https://github.com/Spreaker489/MusicHub-V1.2.1-Gecko/releases/tag/v1.2.1)

No Release build, R8 shrinking, or APK size optimization is performed. Physical-device behavior must be tested separately for each new build; a successful CI build alone does not prove device compatibility.

## Despre aplicație

MusicHub Classic aduce playerul web MusicHub într-o aplicație Android dedicată, folosind motorul Mozilla GeckoView.

Aplicația oferă comenzi media în notificare și pe ecranul de blocare, atunci când playerul și versiunea Android le permit. Proiectul folosește `minSdk 24` (Android 7.0 sau mai nou).

**Ediție:** MusicHub Classic – APK universal  
**Versiune documentată:** 1.2.1  
**Dezvoltator:** marius_489_spk

### Compilare și distribuție

Ramura `main` este sursa unică de cod. GitHub Actions compilează numai varianta **Debug**. La fiecare push pe `main`, workflow-ul compilează APK-ul universal, îl încarcă drept artefact Actions (`musichub-classic-debug`; descărcarea Actions este un ZIP care conține APK-ul) și înlocuiește APK-ul din Release-ul existent `v1.2.1`.

În Release este publicat fișierul `.apk` direct, nu arhiva ZIP. APK-ul publicat în prezent are **335.524.315 bytes** (aproximativ **320 MiB / 335,5 MB**).

Nu se execută build Release, R8 shrinking sau optimizări pentru reducerea dimensiunii. Un build CI reușit nu înlocuiește testarea pe dispozitive fizice.

## About the app

MusicHub Classic brings the MusicHub web player into a dedicated Android application powered by Mozilla GeckoView.

The app provides media controls in the notification and on the lock screen when supported by the player and Android version. The project uses `minSdk 24` (Android 7.0 or newer).

**Edition:** MusicHub Classic – universal APK  
**Documented version:** 1.2.1  
**Developer:** marius_489_spk

### Build and distribution

The `main` branch is the single source of truth. GitHub Actions builds the **Debug** variant only. On each push to `main`, the workflow builds the universal APK, uploads it as the `musichub-classic-debug` Actions artifact (downloaded as a ZIP containing the APK), and replaces the APK asset in the existing `v1.2.1` Release.

The Release contains the `.apk` file directly, not a ZIP. The currently published APK is **335,524,315 bytes** (about **320 MiB / 335.5 MB**).

No Release build, R8 shrinking, or APK size optimization is performed. A successful CI build does not replace physical-device testing.
