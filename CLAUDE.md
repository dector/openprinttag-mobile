# Claude Context

## Project

Kotlin Multiplatform app implementing [OpenPrintTag](https://openprinttag.org/) NFC standard for 3D printing filament spools. Read/write filament data (material, temperature, weight, etc.) to NFC tags.

**Stack**: KMP + Compose Multiplatform (Android primary, iOS, Desktop/JVM for testing)
**Pattern**: MVVM with Intent/State pattern, StateFlow

## Structure

```
composeApp/src/
├── commonMain/         # Shared code
│   ├── model/         # TagState, CountryCode
│   ├── ui/components/ # Modular section components
│   ├── App.kt         # Main UI
│   └── TagInfoViewModel.kt # State management
├── androidMain/       # Android-specific (NFC)
├── iosMain/          # iOS-specific
└── jvmMain/          # Desktop
```

## Key Patterns

- **State**: Immutable data classes, single ViewModel per screen
- **UI**: Material3, small focused composables, extract reusable components
- **KMP**: Platform code in platform folders, expect/actual for NFC
- **Validation**: GTIN (13-14 digits), material tags (comma-separated, max 4, max 15 chars each)

## Common Tasks

**Add UI section**: Create component in `ui/components/`, add to TagState, add intent to ViewModel, include in App.kt
**Platform code**: `expect` in commonMain, `actual` in platform folders
**Test**: Desktop target for quick UI testing, device for NFC

## Current Status

**WIP**: UI for tag config, material classification, temperature, weight, validation
**TODO**: NFC read/write (Android/iOS), QR codes, inventory, OpenPrintTag encoding/decoding

## Build

```bash
./gradlew :composeApp:run          # Desktop
./gradlew :composeApp:assembleDebug # Android
```
