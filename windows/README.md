# MusicHub Desktop for Windows

MusicHub Desktop opens the official MusicHub web application in a dedicated desktop window.

## Supported systems and architectures

- Windows 7, 8 and 8.1: x64 (64-bit)
- Windows 10 and 11: x64 (64-bit)

Only the **x64** installer is built and published. No 32-bit installer is provided.

## Build locally

Requires Node.js and npm.

```powershell
cd windows
npm install
npm run dist
```

Installers are written to `windows/dist/`.

## Important compatibility and security note

This build uses Electron 22.3.27 because it is the final Electron major line supporting Windows 7/8/8.1. Electron 22 is end-of-life and no longer receives Chromium/Electron security fixes. Use this legacy build only if compatibility with older Windows versions is essential; avoid using it for sensitive browsing or entering passwords you would not otherwise expose to an outdated browser engine.

The Windows client is separate from the Android application and does not change the Android build.
