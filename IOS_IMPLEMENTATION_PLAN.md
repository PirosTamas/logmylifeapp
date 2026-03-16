# iOS Implementation Plan — LogMyLifeApp

## Current State
- Android-only Kotlin Multiplatform project (only `androidTarget` configured)
- UI: Jetpack Compose (Android-only)
- DB: Room (Android-only)
- DI: Manual `Graph.kt` + Hilt for Settings
- No `iosApp/`, no `iosMain/`, no Swift files

## Target
- iPhone 15 (iOS 17+, arm64 simulator + device)
- **Native SwiftUI** for iOS UI
- **Android keeps Jetpack Compose unchanged**
- Shared business logic (ViewModels, Repositories, Models) in `commonMain`

---

## Strategy: Shared Logic, Native UIs

```
┌──────────────────────────────────────────────────┐
│                  commonMain (KMP)                │
│  ViewModels · Repositories · DAOs · DTOs         │
│  SQLDelight · DataStore · Domain logic           │
└────────────────┬─────────────────────────────────┘
                 │ compiled as framework
       ┌─────────┴─────────┐
       ▼                   ▼
  androidMain           iosMain
  Jetpack Compose      SwiftUI (Xcode)
  (unchanged)          Swift wraps KMP ViewModels
```

---

## The Problem: What Doesn't Work on iOS

| Current (Android-only)         | Must Be Replaced With                        |
|--------------------------------|----------------------------------------------|
| `Room` (Android DB)            | `SQLDelight` (KMP DB)                        |
| `@HiltAndroidApp` / `hiltViewModel()` | Manual DI only (Hilt = Android-only)  |
| `androidx.datastore` (Android) | `androidx.datastore` (has KMP support — keep)|

**Not affected (Android keeps these as-is):**
- Jetpack Compose screens stay in `androidMain`
- `painterResource()`, drawables, XML vectors — unchanged
- All `R.drawable.*` / `R.string.*` references — unchanged

---

## Step-by-Step Plan

### Step 1 — Add iOS Targets to shared/build.gradle.kts
- Add `iosX64()`, `iosArm64()`, `iosSimulatorArm64()` targets
- Create `iosMain` source set
- Keep existing Android Compose setup untouched
- Add KMP-compatible library versions to `libs.versions.toml`

### Step 2 — Migrate Room → SQLDelight
Room uses KSP and generates Android-specific code. SQLDelight works on all KMP targets.

**Changes:**
1. Add SQLDelight Gradle plugin + runtime dependencies
2. Create `.sq` files in `commonMain/sqldelight/` to replace Room `@Entity` and `@Query`
3. Replace `@Dao` interfaces with SQLDelight-generated query classes
4. Replace `LogMyLifeDatabase` (Room) with SQLDelight `Database` class
5. Create `iosMain` `DatabaseDriverFactory` using `NativeSqliteDriver`
6. Create `androidMain` `DatabaseDriverFactory` using `AndroidSqliteDriver`
7. Update `Graph.kt` to use the new driver factory
8. Update all Repository classes to use SQLDelight queries instead of Room DAOs
9. Keep custom type converters (e.g., `Set<String>`) as SQLDelight adapters

**Files to rewrite:**
- `data/LogMyLifeDatabase.kt`
- `data/Converters.kt`
- All `dao/*.kt` files → replaced by `.sq` SQL files
- All `model/*.kt` entity classes → replaced by SQLDelight schema
- All `repository/*.kt` classes → updated to use SQLDelight

### Step 3 — Make ViewModels iOS-Compatible
KMP ViewModels need to be consumable from Swift.

**Options (pick one):**
- **KMP-NativeCoroutines** — converts `Flow` → Swift `AsyncStream` / `async/await`
- **SKIE** (Square) — zero-boilerplate Swift-friendly coroutine/Flow bridging (recommended)

**Changes:**
1. Add SKIE or KMP-NativeCoroutines plugin to build.gradle.kts
2. Annotate `Flow` properties and `suspend` functions in ViewModels with `@NativeCoroutines` (if using KMP-NativeCoroutines) — SKIE requires no annotations
3. ViewModels stay in `commonMain` — no changes to logic
4. iOS consumes them via generated Swift extensions

### Step 4 — Update DI (Graph.kt) for iOS
- Remove Hilt from Settings feature (Hilt is Android-only)
- Convert `SettingsViewModel` from `@HiltViewModel` to manual factory pattern (same as other ViewModels)
- `Graph.kt` becomes fully platform-agnostic in `commonMain`
- Platform-specific `DatabaseDriverFactory` injected at startup from `androidMain` / `iosMain`

### Step 5 — Create iosApp (Xcode Project)
1. Create `iosApp/` directory with Xcode project targeting iOS 17+
2. Link the shared KMP framework (`shared.framework`) in Xcode
3. Configure `embedAndSignAppleFrameworkForXcode` Gradle task in `build.gradle.kts`
4. Create `iOSApp.swift` — `@main` SwiftUI app entry point
5. Initialise `Graph` (KMP DI) inside `iOSApp.swift` on startup

**iosApp file structure:**
```
iosApp/
  iosApp.xcodeproj/
    project.pbxproj
  iosApp/
    iOSApp.swift
    ContentView.swift          ← root SwiftUI nav
    Assets.xcassets/
    Info.plist
    Screens/
      Home/
        HomeScreen.swift
      Workout/
        WorkoutHomeScreen.swift
        AddWorkoutPlanScreen.swift
      YearInPixels/
        YearInPixelsScreen.swift
      Settings/
        SettingsScreen.swift
    ViewModels/
      ObservableWorkoutViewModel.swift   ← @Observable wrapper over KMP VM
      ObservableSettingsViewModel.swift
      ...
```

### Step 6 — Build SwiftUI Screens
Each SwiftUI screen wraps the corresponding KMP ViewModel using `@Observable` (iOS 17+) or `ObservableObject`.

**Pattern:**
```swift
// Thin Swift wrapper — just bridges KMP Flow → @Published
@Observable
class ObservableWorkoutViewModel {
    private let kmpVM = Graph.shared.workoutHomeViewModel

    var workoutPlans: [WorkoutPlan] = []

    init() {
        // collect KMP Flow using SKIE async sequence
        Task {
            for await plans in kmpVM.workoutPlans {
                self.workoutPlans = plans
            }
        }
    }

    func deleteWorkout(id: Int64) {
        kmpVM.deleteWorkout(id: id)
    }
}
```

**Screens to build (one SwiftUI file per Android screen):**
- `WorkoutHomeScreen.swift`
- `AddWorkoutPlanScreen.swift`
- `WorkoutDetailScreen.swift`
- `YearInPixelsScreen.swift`
- `HomeScreen.swift` (analytics)
- `SettingsScreen.swift`

### Step 7 — iOS Navigation
Use SwiftUI native `NavigationStack` + `TabView` — mirrors the 4-tab Android nav bar.

```swift
TabView {
    HomeScreen()        .tabItem { Label("Home", systemImage: "chart.bar") }
    WorkoutScreen()     .tabItem { Label("Workout", systemImage: "figure.run") }
    YearInPixels()      .tabItem { Label("Pixels", systemImage: "calendar") }
    SettingsScreen()    .tabItem { Label("Settings", systemImage: "gear") }
}
```

### Step 8 — Test on iPhone 15 Simulator
1. Run `./gradlew :shared:linkDebugFrameworkIosSimulatorArm64`
2. Open `iosApp/iosApp.xcodeproj` in Xcode
3. Select iPhone 15 simulator
4. Build and run

---

## Library Version Changes Required

| Library | Current | Change |
|---------|---------|--------|
| `androidx.room` | 2.8.4 | Remove → add `sqldelight` |
| `ksp` (Room) | 2.3.6 | Keep or replace with SQLDelight Gradle plugin |
| `hilt` | (current) | Remove from Settings feature |
| **SKIE** | — | Add (Swift/Kotlin Flow bridge) |
| **SQLDelight** | — | Add |

---

## Execution Order
1. Step 1 — Gradle / build setup (iOS targets, no app code changes)
2. Step 2 — Migrate Room → SQLDelight (biggest change, verify Android still works)
3. Step 4 — Fix DI (remove Hilt from Settings)
4. Step 3 — Add SKIE plugin, verify ViewModels compile for iOS target
5. Step 5 — Create iosApp Xcode project + link framework
6. Step 6 — Build SwiftUI screens one by one
7. Step 7 — Wire up iOS navigation
8. Step 8 — Test on simulator

---

## Risk / Notes
- **SQLDelight migration is the most complex step** — rewrites the entire data layer; verify Android after each DAO migration
- Android Compose UI is completely untouched — zero regression risk on Android UI
- SKIE is the smoothest Flow→Swift bridge and requires no annotation changes in KMP code
- `WorkoutSummaryElement.kt` has `illustrationResId: Int?` — iOS will need a separate image strategy (SF Symbols or bundled PNG in iosApp/Assets.xcassets)
- DataStore has KMP support but needs a platform-specific path for iOS (`iosMain` factory)
- KMP ViewModels should NOT inherit `androidx.lifecycle.ViewModel` — use plain classes with `CoroutineScope` managed manually for KMP compatibility