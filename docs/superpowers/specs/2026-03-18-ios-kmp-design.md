# iOS KMP Design — LogMyLifeApp

**Date:** 2026-03-18
**Approach:** Kotlin Multiplatform — shared business logic + native SwiftUI UI

---

## Goals

- Add iOS support to the existing Android app
- Reuse all business logic (ViewModels, DAOs, Repositories, models, DB) via KMP shared module
- Build native SwiftUI UI on iOS (feature parity with Android, UI can differ)
- Learn both KMP and SwiftUI simultaneously

---

## Architecture

```
shared/commonMain   — ViewModels, DAOs, Repositories, models, DB schema
shared/androidMain  — Android Compose UI, Android-specific factories (unchanged)
shared/iosMain      — iOS-specific factories (DatabaseBuilder, DataStoreFactory)
app/                — Android entry point (unchanged except SettingsViewModel DI)
iosApp/             — Xcode project, SwiftUI screens, Swift ViewModel wrappers
```

**Data flow (iOS):**
```
SwiftUI View → Swift ObservableObject wrapper
    → KMP ViewModel (via SKIE: Flow → AsyncSequence, suspend → async)
        → Repository → DAO → Room DB (BundledSQLiteDriver on iOS)
```

---

## Changes Required

### 1. `shared/build.gradle.kts`
- Add iOS targets: `iosX64()`, `iosArm64()`, `iosSimulatorArm64()`
- Add KSP for all iOS targets (Room annotation processor)
- Add `androidx.sqlite:sqlite-bundled` to `iosMain` dependencies
- Add SKIE plugin for Kotlin Flow → Swift async interop
- Add `XCFramework` packaging task

### 2. `shared/src/commonMain/` — small fixes
- `SettingsViewModel.kt` — remove `@HiltViewModel` / `@Inject` (Hilt is Android-only); use manual DI like all other ViewModels
- Any `Dispatchers.IO` usage in ViewModels → change to `Dispatchers.Default`

### 3. `shared/src/iosMain/` — new files
- `DatabaseBuilder.kt` — creates `LogMyLifeDatabase` using `BundledSQLiteDriver`
- `DataStoreFactory.kt` — creates `DataStore<Preferences>` using iOS file path

### 4. `app/` — minor update
- Update SettingsViewModel instantiation: replace `hiltViewModel()` with manual factory since `@HiltViewModel` annotation is removed

### 5. `iosApp/` — new Xcode project (on Mac)
- Entry point: `LogMyLifeApp.swift` (calls `Graph.provide()` equivalent)
- Root: `ContentView.swift` with `TabView` (4 tabs)
- Swift `ObservableObject` wrapper per ViewModel in `ViewModels/`
- SwiftUI screens in `Screens/` mirroring Android feature structure

---

## iOS Navigation Structure

```
TabView
├── WorkoutTab       → NavigationStack
│   ├── WorkoutHomeScreen
│   ├── WorkoutPreviewScreen
│   ├── WorkoutSetScreen
│   └── WorkoutSummaryScreen / WorkoutFailureScreen
├── DailyLifeTab     → NavigationStack
│   ├── DailyLifeDataScreen
│   ├── AddDailyLifeDataQuestionScreen
│   └── PredefinedAnswersScreen
├── YearInPixelsTab  → NavigationStack
│   └── YearInPixelsScreen
└── SettingsTab      → NavigationStack
    └── SettingsHomeScreen
```

---

## Dev Workflow

| Platform | Tool | Responsibility |
|----------|------|----------------|
| Windows | Android Studio | Edit Kotlin (commonMain, androidMain, iosMain), run Android |
| Mac | Xcode | Run `./gradlew shared:assembleXCFramework`, build SwiftUI, run iOS |

---

## Key Dependencies to Add

| Dependency | Purpose |
|---|---|
| `androidx.sqlite:sqlite-bundled` | `BundledSQLiteDriver` for Room on iOS |
| SKIE `co.touchlab.skie` | Kotlin Flow/suspend → Swift AsyncSequence/async |
| Room KSP for iOS targets | Annotation processing for iOS |
