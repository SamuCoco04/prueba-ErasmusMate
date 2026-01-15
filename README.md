# ErasmusMate Prototype

## Overview
Local-only Android prototype demonstrating ErasmusMate requirements with Clean Architecture + MVVM, Room, DataStore, and WorkManager.

## How to run
1. Open the project in Android Studio.
2. Sync Gradle and run the `app` configuration on an emulator/device.

## Demo accounts / role switching
- Use the **Login** screen to authenticate with stubbed university credentials.
- Use the **Home** screen buttons to switch roles between Student, Coordinator, and Admin (local-only role toggle).

## Integration stubs toggles
All integrations are local-only and controllable via DataStore flags (see `SettingsDataStore`).
- UniversityAuthProvider stub (REQ-1)
- SocialAuthProvider stub (REQ-44)
- DocumentVerificationService (REQ-8/34)
- UniversityDataExchangeClient (REQ-21)
- AcademicRecordsVerifier (REQ-51)
- NotificationGateway (REQ-14/42/58)
- ExternalCalendarGateway (REQ-56)

Update the flags in DataStore using `SettingsDataStore` methods (use temporary UI or debug tooling).

## simulateDbFailure
To simulate DB failure handling (REQ-65), set `simulateDbFailure` to `true` using:
- `SettingsDataStore.setSimulateDbFailure(true)`

When enabled, repository writes throw a DB failure error and the UI displays recovery guidance.

## Notes
- Document uploads are designed for secure storage via Android Keystore-backed encryption (see `SecureStorage`).
- External integrations are stubs only; no real network calls are made.
- TODO markers indicate ambiguous requirements that need further detail before full implementation.
