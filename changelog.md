# Changelog

## [Unreleased] — branch: multiplatform_2

### Goal
Add iOS support via Kotlin Multiplatform. Share all business logic (ViewModels, DAOs, Repositories, DB) between Android and iOS. Build native SwiftUI UI for iOS.

### Planned Changes
- Add iOS targets to `shared/build.gradle.kts` (iosX64, iosArm64, iosSimulatorArm64)
- Add SKIE plugin for Kotlin Flow → Swift async interop
- Add `sqlite-bundled` for Room on iOS
- Add `shared/src/iosMain/` with iOS-specific `DatabaseBuilder` and `DataStoreFactory`
- Remove `@HiltViewModel`/`@Inject` from `SettingsViewModel` (Hilt is Android-only)
- Update Android `SettingsViewModel` instantiation to manual DI
- Create `iosApp/` Xcode project with SwiftUI screens

---

## Previous releases

### Android App (main branch)
- Workout tracking: plans, sessions, sets, summary
- Daily life data tracking with custom questions
- Year in Pixels view
- Achievements / Progress tracking
- Settings: name, weight unit, water reminder, dark mode
- Dark/light theme system via `LocalAppColors`
- Room 2.8.4 database (KMP-ready)
- Manual DI via `Graph.kt`
