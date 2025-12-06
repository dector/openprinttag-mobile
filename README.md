# OpenPrintTag Mobile

> ![WARNING]
> This is Work-In-Progress project. Expect bugs.
>
> Also it's build in cooperation with Claude Code and is only partially verified.

A Kotlin Multiplatform mobile application for reading and writing NFC tags on 3D printing filament spools using the [OpenPrintTag](https://openprinttag.org/) standard.

## About OpenPrintTag

OpenPrintTag is an open-source universal NFC standard for 3D printing filament spools, created by Prusa Research. It enables automatic filament detection and eliminates proprietary "smart spool" ecosystems through one interoperable standard.

### Key Features

- **Universal Compatibility**: One open standard that works across all brands and ecosystems
- **Material Recognition**: Automatically detect filament type, brand, and properties
- **Live Tracking**: Monitor remaining filament amounts in real-time
- **Inventory Management**: Track and log custom data for your filament collection
- **Reusable Tags**: Transfer tags between spools without vendor lock-in
- **Offline Operation**: No cloud dependency required
- **Cost-Effective**: Uses inexpensive NFC tags

## Project Overview

This mobile app allows (when ready) users to:

- Scan NFC tags on filament spools to read material data
- Configure and write filament information to NFC tags
- Manage material classification (PLA, PETG, ABS, etc.)
- Set temperature profiles (print, preheat, bed temperatures)
- Track weight and material properties
- Store certifications and material tags
- Generate compliant OpenPrintTag NFC data

## Technology Stack

- **Kotlin Multiplatform**: Share code across Android, iOS, and Desktop
- **Compose Multiplatform**: Modern declarative UI framework
- **Target Platforms**:
  - Android (Primary target)
  - iOS (Arm64 & Simulator)
  - Desktop/JVM (Development and testing)

## Project Structure

```
openprinttag-mobile/
├── composeApp/
│   └── src/
│       ├── commonMain/kotlin/     # Shared code for all platforms
│       │   ├── model/             # Data models (TagState, CountryCode, etc.)
│       │   ├── ui/                # UI components and theme
│       │   ├── App.kt             # Main app entry point
│       │   └── TagInfoViewModel.kt # State management
│       ├── androidMain/kotlin/    # Android-specific code
│       ├── iosMain/kotlin/        # iOS-specific code
│       └── jvmMain/kotlin/        # Desktop-specific code
├── iosApp/                        # iOS app wrapper
└── build.gradle.kts
```

## Building and Running

### Prerequisites

- JDK 17 or higher
- Android Studio or IntelliJ IDEA with Kotlin Multiplatform plugin
- For iOS: Xcode (macOS only)

### Android

Build and run the Android app:

```shell
# macOS/Linux
./gradlew :composeApp:assembleDebug

# Windows
.\gradlew.bat :composeApp:assembleDebug
```

Or use the run configuration in Android Studio.

### Desktop (JVM)

Run the desktop version for development and testing:

```shell
# macOS/Linux
./gradlew :composeApp:run

# Windows
.\gradlew.bat :composeApp:run
```

### iOS

Open the `/iosApp` directory in Xcode and run from there, or use the run configuration in IntelliJ IDEA.

## Features Status

### Current Features

- Material classification selection (PLA, PETG, ABS, etc.)
- Basic information input (brand, material name, color, density)
- Temperature settings configuration
- Weight tracking
- Material properties and certifications
- NFC URL options
- Data validation and verification
- Desktop preview/testing support

### Planned Features

- NFC tag reading functionality
- NFC tag writing functionality
- Filament inventory management
- QR code generation
- Material database integration
- Multilingual support

## Contributing

Contributions are welcome! This project follows the OpenPrintTag open-source initiative.

### Development Guidelines

1. Follow Kotlin coding conventions
2. Use Compose best practices for UI components
3. Keep platform-specific code minimal (in platform folders)
4. Test on multiple platforms when possible

## OpenPrintTag Standard

This app implements the OpenPrintTag specification. For more information:

- Official website: [openprinttag.org](https://openprinttag.org/)
- Specification: Check the official OpenPrintTag documentation

## License

This project is open-source. Please check the LICENSE file for details.

## Resources

- [OpenPrintTag Official Site](https://openprinttag.org/)
- [Kotlin Multiplatform Documentation](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)
- [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)

## Acknowledgments

- Prusa Research for creating the OpenPrintTag standard
- The open-source 3D printing community
