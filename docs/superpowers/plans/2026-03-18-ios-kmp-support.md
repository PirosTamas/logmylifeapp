# iOS KMP Support Implementation Plan

> **For agentic workers:** REQUIRED: Use superpowers:subagent-driven-development (if subagents available) or superpowers:executing-plans to implement this plan. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Add iOS targets to the KMP shared module and create a native SwiftUI iOS app that consumes shared ViewModels, DAOs, and Room database.

**Architecture:** The `shared` module compiles to an XCFramework for iOS. All business logic (ViewModels, Repositories, DAOs, Room DB) lives in `commonMain` and is shared. iOS-specific platform factories (`DatabaseBuilder`, `DataStoreFactory`) live in `iosMain`. A new `iosApp/` Xcode project contains the SwiftUI UI layer only.

**Tech Stack:** Kotlin Multiplatform, Room 2.8.4 (KMP), kotlinx-coroutines 1.8.0, DataStore preferences-core 1.1.1, SKIE 0.9.0 (Flow → Swift async), BundledSQLiteDriver, SwiftUI

---

## Key Facts Before You Start

- `SettingsViewModel.kt` already uses **manual DI** — no Hilt annotations to remove
- `Dispatchers.IO` **is available on iOS** in coroutines 1.8.0 (it's an alias for `Dispatchers.Default` on Native) — no ViewModel changes needed
- Android side (`androidMain/`, `app/`) requires **zero changes**
- The Xcode project (`iosApp/`) **must be created on a Mac** — Tasks 1–6 are done on Windows, Task 7 onwards on Mac

---

## File Map

| Action | File |
|--------|------|
| Modify | `gradle/libs.versions.toml` |
| Modify | `build.gradle.kts` (root) |
| Modify | `shared/build.gradle.kts` |
| Create | `shared/src/iosMain/kotlin/com/example/logmylifeapp/DatabaseBuilder.kt` |
| Create | `shared/src/iosMain/kotlin/com/example/logmylifeapp/DataStoreFactory.kt` |
| Create | `iosApp/` — Xcode project (on Mac) |
| Create | `iosApp/iosApp/App/LogMyLifeApp.swift` |
| Create | `iosApp/iosApp/App/ContentView.swift` |
| Create | `iosApp/iosApp/ViewModels/` — one wrapper per KMP ViewModel |
| Create | `iosApp/iosApp/Screens/` — SwiftUI screens |

---

## Task 1: Add SKIE and sqlite-bundled to version catalog

**Files:**
- Modify: `gradle/libs.versions.toml`

- [ ] **Step 1: Add versions**

Open `gradle/libs.versions.toml` and add to the `[versions]` section:

```toml
skie = "0.9.0"
sqlite = "2.5.0"
```

- [ ] **Step 2: Add library entries**

Add to the `[libraries]` section:

```toml
androidx-sqlite-bundled = { group = "androidx.sqlite", name = "sqlite-bundled", version.ref = "sqlite" }
```

- [ ] **Step 3: Add plugin entry**

Add to the `[plugins]` section:

```toml
skie = { id = "co.touchlab.skie", version.ref = "skie" }
```

- [ ] **Step 4: Commit**

```bash
git add gradle/libs.versions.toml
git commit -m "build: add SKIE and sqlite-bundled to version catalog"
```

---

## Task 2: Declare SKIE plugin in root build.gradle.kts

**Files:**
- Modify: `build.gradle.kts` (project root)

- [ ] **Step 1: Add SKIE plugin declaration**

Open `build.gradle.kts` and add one line inside `plugins { }`:

```kotlin
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.skie) apply false   // ADD THIS LINE
}
```

- [ ] **Step 2: Commit**

```bash
git add build.gradle.kts
git commit -m "build: declare SKIE plugin in root build file"
```

---

## Task 3: Update shared/build.gradle.kts for iOS

**Files:**
- Modify: `shared/build.gradle.kts`

This is the most important task. It adds iOS compilation targets, Room KSP processors for each iOS target, SKIE, sqlite-bundled, and the XCFramework packaging task.

- [ ] **Step 1: Replace shared/build.gradle.kts with the full iOS-enabled version**

```kotlin
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.skie)
    id("com.google.devtools.ksp")
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
        }
    }

    val xcf = XCFramework()
    listOf(iosX64(), iosArm64(), iosSimulatorArm64()).forEach { target ->
        target.binaries.framework {
            baseName = "shared"
            xcf.add(this)
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.androidx.room.runtime)
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.datastore.preferences.core)
        }
        androidMain.dependencies {
            implementation(project.dependencies.platform(libs.androidx.compose.bom))
            implementation(libs.androidx.compose.ui)
            implementation(libs.androidx.compose.ui.graphics)
            implementation(libs.androidx.compose.ui.tooling.preview)
            implementation(libs.androidx.compose.material3)
            implementation(libs.androidx.navigation.navigation.compose)
            implementation(libs.androidx.datastore.preferences)
            implementation(libs.androidx.lifecycle.runtime.ktx)
            implementation(libs.kotlinx.coroutines.android)
            implementation(libs.androidx.room.ktx)
            implementation("com.google.accompanist:accompanist-permissions:0.37.3")
            implementation("sh.calvin.reorderable:reorderable:2.4.3")
        }
        iosMain.dependencies {
            implementation(libs.androidx.sqlite.bundled)
        }
    }
}

android {
    namespace = "com.example.logmylifeapp.shared"
    compileSdk = 36

    defaultConfig {
        minSdk = 26
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    add("kspAndroid", libs.androidx.room.compiler)
    add("kspIosX64", libs.androidx.room.compiler)
    add("kspIosArm64", libs.androidx.room.compiler)
    add("kspIosSimulatorArm64", libs.androidx.room.compiler)
}
```

- [ ] **Step 2: Sync Gradle and verify it resolves without errors**

In Android Studio: File → Sync Project with Gradle Files

Expected: Gradle sync completes. You will see new iOS-related tasks in the Gradle panel.
If sync fails with "plugin not found for SKIE": verify `build.gradle.kts` root has `alias(libs.plugins.skie) apply false` and the version catalog entry is correct.

- [ ] **Step 3: Commit**

```bash
git add shared/build.gradle.kts
git commit -m "build: add iOS targets, SKIE, sqlite-bundled, XCFramework to shared module"
```

---

## Task 4: Create iOS DatabaseBuilder

**Files:**
- Create: `shared/src/iosMain/kotlin/com/example/logmylifeapp/DatabaseBuilder.kt`

This is the iOS equivalent of `shared/src/androidMain/kotlin/com/example/logmylifeapp/DatabaseBuilder.kt`. It uses `BundledSQLiteDriver` instead of Android's `SupportSQLiteDatabase`.

**Key differences from Android version:**
- No `android.content.Context` parameter needed
- Uses `BundledSQLiteDriver` (SQLite bundled into the app)
- File path built from `NSHomeDirectory()` (iOS sandbox)
- No `deleteDatabase` call (we want persistent data on iOS)
- Seed data runs via a separate coroutine after build (Room KMP callbacks use `SQLiteConnection`, not DAOs)

- [ ] **Step 1: Create the directory and file**

Create `shared/src/iosMain/kotlin/com/example/logmylifeapp/DatabaseBuilder.kt`:

```kotlin
package com.example.logmylifeapp

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.example.logmylifeapp.data.LogMyLifeDatabase
import com.example.logmylifeapp.model.WorkoutExerciseLog
import com.example.logmylifeapp.model.WorkoutExerciseSetLog
import com.example.logmylifeapp.model.WorkoutPlanExerciseCrossRef
import com.example.logmylifeapp.model.WorkoutPlanStretchCrossRef
import com.example.logmylifeapp.model.WorkoutPlanWarmupCrossRef
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import platform.Foundation.NSHomeDirectory

fun buildDatabase(): LogMyLifeDatabase {
    val dbPath = NSHomeDirectory() + "/Documents/logmylife.db"
    val db = Room.databaseBuilder<LogMyLifeDatabase>(name = dbPath)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.Default)
        .build()

    CoroutineScope(Dispatchers.Default).launch {
        val plans = db.workoutPlanDao().getAllWorkoutPlans().first()
        if (plans.isEmpty()) {
            seedDatabase(db)
        }
    }

    return db
}

private suspend fun seedDatabase(db: LogMyLifeDatabase) {
    val questionDao = db.dailyLifeDataQuestionDao()
    val answerDao = db.dailyLifeDataAnswerDao()
    val workoutPlanDao = db.workoutPlanDao()
    val workoutExerciseDao = db.workoutExerciseDao()
    val crossRefDao = db.workoutPlanExerciseCrossRefDao()
    val warmupCrossRefDao = db.workoutPlanWarmupCrossRefDao()
    val stretchCrossRefDao = db.workoutPlanStretchCrossRefDao()
    val workoutSessionDao = db.workoutSessionDao()
    val workoutExerciseLogDao = db.workoutExerciseLogDao()
    val workoutExerciseSetLogDao = db.workoutExerciseSetLogDao()

    val initialQuestionIds = questionDao.addQuestions(InitialData.questions())
    questionDao.addQuestions(DummyData.questions())
    val moodQuestionId = initialQuestionIds.first().toInt()
    answerDao.addAnswers(InitialData.answers(moodQuestionId))

    val initialWorkoutIds = workoutPlanDao.addWorkoutPlans(DummyData.workoutPlans())
    val initialWorkoutPlanId = initialWorkoutIds.first().toInt()

    val exerciseIds = workoutExerciseDao.addWorkoutExercises(DummyData.workoutExercises())
    val warmupExerciseIds = workoutExerciseDao.addWorkoutExercises(DummyData.warmupExercises())
    val stretchExerciseIds = workoutExerciseDao.addWorkoutExercises(DummyData.stretchExercises())

    exerciseIds.forEachIndexed { index, exerciseId ->
        crossRefDao.addWorkoutPlanExerciseCrossRef(
            WorkoutPlanExerciseCrossRef(planId = initialWorkoutPlanId, exerciseId = exerciseId.toInt(), orderInWorkout = index)
        )
    }
    warmupExerciseIds.forEachIndexed { index, exerciseId ->
        warmupCrossRefDao.addWorkoutPlanWarmupCrossRef(
            WorkoutPlanWarmupCrossRef(planId = initialWorkoutPlanId, exerciseId = exerciseId.toInt(), orderInWarmup = index)
        )
    }
    stretchExerciseIds.forEachIndexed { index, exerciseId ->
        stretchCrossRefDao.addWorkoutPlanStretchCrossRef(
            WorkoutPlanStretchCrossRef(planId = initialWorkoutPlanId, exerciseId = exerciseId.toInt(), orderInStretch = index)
        )
    }

    val workoutSessionId = workoutSessionDao.addWorkoutSessions(
        DummyData.workoutSessions(initialWorkoutPlanId)
    ).first()

    exerciseIds.forEach { exerciseId ->
        val workoutExerciseLogId = workoutExerciseLogDao.addWorkoutExerciseLog(
            WorkoutExerciseLog(sessionId = workoutSessionId.toInt(), exerciseId = exerciseId.toInt())
        )
        workoutExerciseSetLogDao.addWorkoutExerciseSetLogs(
            listOf(
                WorkoutExerciseSetLog(exerciseLogId = workoutExerciseLogId.toInt(), order = 0, targetReps = 10, completedReps = 10, weight = 40f, success = true, description = "SUCCESS"),
                WorkoutExerciseSetLog(exerciseLogId = workoutExerciseLogId.toInt(), order = 1, targetReps = 10, completedReps = 10, weight = 50f, success = true, description = "SUCCESS"),
                WorkoutExerciseSetLog(exerciseLogId = workoutExerciseLogId.toInt(), order = 2, targetReps = 10, completedReps = 8, weight = 60f, success = false, description = "Nem ment")
            )
        )
    }
}
```

- [ ] **Step 2: Commit**

```bash
git add shared/src/iosMain/
git commit -m "feat: add iOS DatabaseBuilder using BundledSQLiteDriver"
```

---

## Task 5: Create iOS DataStoreFactory

**Files:**
- Create: `shared/src/iosMain/kotlin/com/example/logmylifeapp/DataStoreFactory.kt`

iOS equivalent of `shared/src/androidMain/kotlin/com/example/logmylifeapp/DataStoreFactory.kt`. Uses file-based path instead of Android `Context`.

- [ ] **Step 1: Create the file**

```kotlin
package com.example.logmylifeapp

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import com.example.logmylifeapp.repository.SettingsRepository
import okio.Path.Companion.toPath
import platform.Foundation.NSHomeDirectory

fun buildSettingsRepository(): SettingsRepository {
    val dataStore = PreferenceDataStoreFactory.createWithPath {
        (NSHomeDirectory() + "/Documents/settings.preferences_pb").toPath()
    }
    return SettingsRepository(dataStore)
}
```

- [ ] **Step 2: Commit**

```bash
git add shared/src/iosMain/kotlin/com/example/logmylifeapp/DataStoreFactory.kt
git commit -m "feat: add iOS DataStoreFactory using file path"
```

---

## Task 6: Verify the Android build still works

This verifies the shared module changes don't break the existing Android app.

- [ ] **Step 1: Build the Android app**

In Android Studio: Build → Make Project
OR run from terminal:
```bash
./gradlew :app:assembleDebug
```
Expected: BUILD SUCCESSFUL

- [ ] **Step 2: If the build fails**

Common issues and fixes:
- `Unresolved reference: iosMain` → Make sure SKIE plugin is declared in root `build.gradle.kts`
- `No such module` for sqlite-bundled → Check `libs.versions.toml` library entry name matches exactly
- Room KSP error → Verify `kspIosX64`, `kspIosArm64`, `kspIosSimulatorArm64` are in the `dependencies {}` block

- [ ] **Step 3: Commit if any fixes were needed**

```bash
git add -p
git commit -m "fix: resolve build issues after iOS target addition"
```

---

## Task 7: Build the XCFramework (on Mac)

> ⚠️ **Switch to Mac from here.** Pull the branch: `git pull origin multiplatform_2`

The XCFramework packages the `shared` Kotlin module into a format that Xcode can import.

- [ ] **Step 1: Run XCFramework build**

For simulator development use the debug variant (faster, no bitcode stripping):
```bash
./gradlew :shared:assembleDebugXCFramework
```
Expected: `shared/build/XCFrameworks/debug/shared.xcframework` is created.

For App Store / device builds use:
```bash
./gradlew :shared:assembleReleaseXCFramework
```

- [ ] **Step 2: Update the Xcode project to point to the debug framework path**

When adding the XCFramework in Task 8, use: `shared/build/XCFrameworks/debug/shared.xcframework`

- [ ] **Step 3: Verify the framework contains iOS slices**

```bash
ls shared/build/XCFrameworks/release/shared.xcframework/
```

Expected output includes: `ios-arm64/`, `ios-arm64_x86_64-simulator/`

---

## Task 8: Create the Xcode project (on Mac)

- [ ] **Step 1: Create the project in Xcode**

1. Open Xcode → File → New → Project
2. Choose: **iOS → App**
3. Settings:
   - Product Name: `iosApp`
   - Team: your Apple ID
   - Organization Identifier: `com.example.logmylifeapp`
   - Interface: **SwiftUI**
   - Language: **Swift**
4. Save location: **inside `LogMyLifeApp/` project root** → creates `LogMyLifeApp/iosApp/`

- [ ] **Step 2: Add the XCFramework to the Xcode project**

1. In Xcode, select the `iosApp` target → General tab
2. Scroll to **Frameworks, Libraries, and Embedded Content**
3. Click `+` → Add Other → Add Files
4. Navigate to: `LogMyLifeApp/shared/build/XCFrameworks/release/shared.xcframework`
5. Set embed: **Do Not Embed** (it's a static framework)

- [ ] **Step 3: Verify import works**

In `ContentView.swift`, add at top: `import shared`
Build (Cmd+B). Expected: no errors.

- [ ] **Step 4: Commit the Xcode project**

```bash
git add iosApp/
git commit -m "feat: create iosApp Xcode project and link shared XCFramework"
```

---

## Task 9: iOS app entry point and Graph initialization (on Mac)

**Files:**
- Modify: `iosApp/iosApp/iosAppApp.swift` (auto-generated entry point)
- Create: `iosApp/iosApp/App/ContentView.swift`

- [ ] **Step 1: Update the app entry point to initialize Graph**

Replace the auto-generated `iosAppApp.swift` content:

```swift
import SwiftUI
import shared

@main
struct iosAppApp: App {
    init() {
        Graph.shared.database = DatabaseBuilderKt.buildDatabase()
        Graph.shared.settingsRepository = DataStoreFactoryKt.buildSettingsRepository()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
```

- [ ] **Step 2: Create ContentView with TabView**

```swift
import SwiftUI
import shared

struct ContentView: View {
    var body: some View {
        TabView {
            WorkoutTab()
                .tabItem {
                    Label("Workout", systemImage: "figure.strengthtraining.traditional")
                }
            DailyLifeTab()
                .tabItem {
                    Label("Daily", systemImage: "list.bullet")
                }
            YearInPixelsTab()
                .tabItem {
                    Label("Year", systemImage: "calendar")
                }
            SettingsTab()
                .tabItem {
                    Label("Settings", systemImage: "gearshape")
                }
        }
        .tint(Color(red: 0.075, green: 0.925, blue: 0.357))
    }
}
```

- [ ] **Step 3: Build to confirm Graph initializes**

Run on simulator. Expected: app launches, no crash on startup.

- [ ] **Step 4: Commit**

```bash
git add iosApp/
git commit -m "feat: initialize Graph in iOS app entry point, add root TabView"
```

---

## Task 10: Workout tab screens (on Mac)

**Files:**
- Create: `iosApp/iosApp/ViewModels/WorkoutHomeViewModelWrapper.swift`
- Create: `iosApp/iosApp/Screens/Workout/WorkoutTab.swift`
- Create: `iosApp/iosApp/Screens/Workout/WorkoutHomeScreen.swift`

The Swift "wrapper" pattern: a `@MainActor ObservableObject` class holds published state and collects the Kotlin `Flow` via SKIE's `AsyncSequence`.

- [ ] **Step 1: Create WorkoutHomeViewModelWrapper**

```swift
import shared

@MainActor
class WorkoutHomeViewModelWrapper: ObservableObject {
    private let vm = WorkoutHomeViewModel()
    @Published var workoutPlans: [WorkoutPlan] = []

    func startObserving() async {
        for await plans in vm.workoutPlansForToday {
            workoutPlans = plans
        }
    }

    func addWorkoutSession(planId: Int32) async -> Int64 {
        return await vm.addWorkoutSession(planId: planId)
    }
}
```

- [ ] **Step 2: Create WorkoutHomeScreen**

```swift
import SwiftUI
import shared

struct WorkoutHomeScreen: View {
    @StateObject private var wrapper = WorkoutHomeViewModelWrapper()
    @Binding var selectedSessionId: Int64?

    var body: some View {
        List(wrapper.workoutPlans, id: \.id) { plan in
            Button(plan.name) {
                Task {
                    let sessionId = await wrapper.addWorkoutSession(planId: plan.id)
                    selectedSessionId = sessionId
                }
            }
        }
        .navigationTitle("Workout")
        .task { await wrapper.startObserving() }
    }
}
```

- [ ] **Step 3: Create WorkoutTab with NavigationStack**

```swift
import SwiftUI
import shared

struct WorkoutTab: View {
    @State private var selectedSessionId: Int64? = nil

    var body: some View {
        NavigationStack {
            WorkoutHomeScreen(selectedSessionId: $selectedSessionId)
                .navigationDestination(item: $selectedSessionId) { sessionId in
                    WorkoutPreviewScreen(sessionId: sessionId)
                }
        }
    }
}
```

- [ ] **Step 4: Build and run on simulator**

Expected: Workout tab shows list of workout plans.

- [ ] **Step 5: Commit**

```bash
git add iosApp/
git commit -m "feat: add WorkoutHomeScreen and WorkoutTab for iOS"
```

---

## Task 11: Remaining screens (on Mac)

Repeat the same wrapper + screen pattern for each remaining feature. Each screen follows the same structure as Task 10.

**Order of implementation:**

1. **WorkoutPreviewScreen** — ViewModel: `WorkoutPreviewViewModel`, shows exercises in a plan
2. **WorkoutSetScreen** — ViewModel: `WorkoutSessionViewModel`, records sets
3. **WorkoutSummaryScreen** — ViewModel: `WorkoutSummaryViewModel`
4. **DailyLifeTab** — `DailyLifeDataViewModel`
5. **YearInPixelsTab** — `YearInPixelsViewModel`
6. **SettingsTab** — `SettingsViewModel`

For each screen:
- [ ] Create `ViewModels/<Name>ViewModelWrapper.swift` — collect Flows, expose as `@Published`
- [ ] Create `Screens/<Feature>/<Name>Screen.swift` — SwiftUI view using the wrapper
- [ ] Build on simulator after each screen
- [ ] Commit after each screen

**Navigation note for WorkoutPreviewScreen:**
`WorkoutPreviewViewModel` needs both `planId` and `sessionId`. When navigating from `WorkoutHomeScreen`, the session is created first (returns `sessionId`), then you need to fetch the `planId` from the session. The cleanest approach: pass both ids as a struct via `navigationDestination`:

```swift
struct WorkoutSessionDestination: Hashable {
    let planId: Int32
    let sessionId: Int64
}
```

Store a `@State var destination: WorkoutSessionDestination?` in `WorkoutTab` and populate both values after `addWorkoutSession` returns. The ViewModel already has `getWorkoutPlanBySessionId(sessionId:)` in the DAO — expose it via `WorkoutPreviewViewModel` or resolve it in the wrapper before navigating.

**ViewModels that need a parameter (not default-constructible):**
- `WorkoutPreviewViewModel` — requires `planId: Int` and `sessionId: Int` → use `WorkoutPreviewViewModelFactory`
- `WorkoutSessionViewModel` — requires `sessionId: Int` → use `WorkoutSessionViewModelFactory`
- `WorkoutSummaryViewModel` — requires `sessionId: Int` → use `WorkoutSummaryViewModelFactory`

On Swift side, pass the parameter via the wrapper's `init`:

```swift
@MainActor
class WorkoutPreviewViewModelWrapper: ObservableObject {
    private let vm: WorkoutPreviewViewModel

    init(planId: Int32, sessionId: Int32) {
        self.vm = WorkoutPreviewViewModelFactory().create(planId: planId, sessionId: sessionId)
    }
    // ... collect flows
}
```

---

## Task 12: Update changelog.md

- [ ] **Step 1: Update changelog with completed work**

Open `changelog.md` and move items from "Planned" to actual entries with dates and notes about what was done.

- [ ] **Step 2: Commit**

```bash
git add changelog.md
git commit -m "docs: update changelog with iOS KMP implementation progress"
```

---

## Troubleshooting Reference

| Problem | Likely cause | Fix |
|---------|-------------|-----|
| `Unresolved reference: BundledSQLiteDriver` | Missing sqlite-bundled in iosMain | Check `libs.versions.toml` and `iosMain.dependencies` |
| `Cannot find 'shared' in scope` in Swift | XCFramework not linked | Re-add framework in Xcode target settings |
| `Graph.shared.database` is not settable from Swift | Room `lateinit var` exposure issue | Try calling `Graph.shared.doInitDatabase(...)` via a helper `fun` instead |
| Kotlin Flow not iterable in Swift | SKIE not applied | Verify `alias(libs.plugins.skie)` is in `shared/build.gradle.kts` |
| Room KSP error: no `@Database` found for iOS | KSP not wired for iOS targets | Check `kspIosX64`, `kspIosArm64`, `kspIosSimulatorArm64` in `dependencies {}` |
| XCFramework build fails | iOS targets not resolving | Run `./gradlew dependencies` to check resolution errors |
