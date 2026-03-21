# Fix commonMain JVM-Only Code Implementation Plan

> **For agentic workers:** REQUIRED: Use superpowers:subagent-driven-development (if subagents available) or superpowers:executing-plans to implement this plan. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Move all ViewModels and ViewModelFactories from `commonMain` to `androidMain` so the shared KMP module compiles cleanly for iOS targets.

**Architecture:** ViewModels use `androidx.lifecycle.ViewModel` + `viewModelScope`. While `lifecycle-viewmodel` 2.8.0 ships a KMP artifact, `viewModelScope` requires `Dispatchers.Main` wired on iOS (not set up yet), and ViewModels are Android UI-lifecycle-bound — iOS has no use for them currently. The cleanest fix is to move all 11 ViewModel/Factory files to `androidMain` and remove the `lifecycle-viewmodel` dependency from `commonMain`. Repositories, models, DAOs, and the database stay in `commonMain` — they use fully KMP-compatible library versions (Room 2.7+, DataStore 1.1+).

**Tech Stack:** Kotlin Multiplatform, androidMain source set, `shared/build.gradle.kts`

---

### Background: What is and isn't a problem

| Code | Status | Reason |
|------|--------|--------|
| `@Entity`, `@Dao`, `@Database` | ✅ Fine in commonMain | Room 2.7+ KMP |
| `DataStore`, preference keys | ✅ Fine in commonMain | DataStore 1.1+ KMP |
| `ViewModel`, `viewModelScope` | ❌ Move to androidMain | `viewModelScope` needs `Dispatchers.Main` on iOS; no iOS UI exists yet |
| `ViewModelProvider.Factory` (factories) | ❌ Move to androidMain | `Class<T>` is `java.lang.Class` — JVM-only |

---

### File Map

**Files to move: commonMain → androidMain (11 total)**

ViewModels:
- `viewmodel/SettingsViewModel.kt`
- `viewmodel/WorkoutHomeViewModel.kt`
- `viewmodel/AddWorkoutPlanViewModel.kt`
- `viewmodel/DailyLifeDataViewModel.kt`
- `viewmodel/WorkoutPreviewViewModel.kt`
- `viewmodel/WorkoutSessionViewModel.kt`
- `viewmodel/WorkoutSummaryViewModel.kt`
- `viewmodel/YearInPixelsViewModel.kt`

Factories:
- `viewmodel/WorkoutPreviewViewModelFactory.kt`
- `viewmodel/WorkoutSessionViewModelFactory.kt`
- `viewmodel/WorkoutSummaryViewModelFactory.kt`

**File to modify:**
- `shared/build.gradle.kts` — move `androidx.lifecycle.viewmodel` from `commonMain` to `androidMain`

---

### Task 1: Read all ViewModel files from commonMain

Before moving, read each file to capture its exact content.

**Files:**
- Read: all 11 files in `shared/src/commonMain/kotlin/com/example/logmylifeapp/viewmodel/`

- [ ] **Step 1: Read all ViewModel files**

Read each of the following files:
- `shared/src/commonMain/kotlin/com/example/logmylifeapp/viewmodel/SettingsViewModel.kt`
- `shared/src/commonMain/kotlin/com/example/logmylifeapp/viewmodel/WorkoutHomeViewModel.kt`
- `shared/src/commonMain/kotlin/com/example/logmylifeapp/viewmodel/AddWorkoutPlanViewModel.kt`
- `shared/src/commonMain/kotlin/com/example/logmylifeapp/viewmodel/DailyLifeDataViewModel.kt`
- `shared/src/commonMain/kotlin/com/example/logmylifeapp/viewmodel/WorkoutPreviewViewModel.kt`
- `shared/src/commonMain/kotlin/com/example/logmylifeapp/viewmodel/WorkoutSessionViewModel.kt`
- `shared/src/commonMain/kotlin/com/example/logmylifeapp/viewmodel/WorkoutSummaryViewModel.kt`
- `shared/src/commonMain/kotlin/com/example/logmylifeapp/viewmodel/YearInPixelsViewModel.kt`
- `shared/src/commonMain/kotlin/com/example/logmylifeapp/viewmodel/WorkoutPreviewViewModelFactory.kt`
- `shared/src/commonMain/kotlin/com/example/logmylifeapp/viewmodel/WorkoutSessionViewModelFactory.kt`
- `shared/src/commonMain/kotlin/com/example/logmylifeapp/viewmodel/WorkoutSummaryViewModelFactory.kt`

---

### Task 2: Create all ViewModel files in androidMain

**Files:**
- Create: `shared/src/androidMain/kotlin/com/example/logmylifeapp/viewmodel/` (11 files)

- [ ] **Step 1: Create each file in androidMain**

For each of the 11 files read in Task 1, create a new file at the same relative path but under `androidMain` instead of `commonMain`. Content is identical — no changes needed.

Example path transformation:
```
shared/src/commonMain/kotlin/com/example/logmylifeapp/viewmodel/SettingsViewModel.kt
                    ↓
shared/src/androidMain/kotlin/com/example/logmylifeapp/viewmodel/SettingsViewModel.kt
```

---

### Task 3: Delete all ViewModel files from commonMain

**Files:**
- Delete: all 11 files in `shared/src/commonMain/kotlin/com/example/logmylifeapp/viewmodel/`

- [ ] **Step 1: Delete each file**

Delete all 11 files from `shared/src/commonMain/kotlin/com/example/logmylifeapp/viewmodel/`.

After deletion, the `viewmodel/` folder should not exist under `commonMain`.

---

### Task 4: Update build.gradle.kts

**Files:**
- Modify: `shared/build.gradle.kts`

- [ ] **Step 1: Move lifecycle-viewmodel dependency**

In `shared/build.gradle.kts`, move `implementation(libs.androidx.lifecycle.viewmodel)` from `commonMain.dependencies` to `androidMain.dependencies`.

Before:
```kotlin
commonMain.dependencies {
    implementation(libs.androidx.room.runtime)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.datastore.preferences.core)
}
androidMain.dependencies {
    // ... existing androidMain deps
}
```

After:
```kotlin
commonMain.dependencies {
    implementation(libs.androidx.room.runtime)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.androidx.datastore.preferences.core)
}
androidMain.dependencies {
    implementation(libs.androidx.lifecycle.viewmodel)
    // ... existing androidMain deps
}
```

---

### Task 5: Verify and build

- [ ] **Step 1: Confirm commonMain viewmodel folder is empty**

```bash
ls shared/src/commonMain/kotlin/com/example/logmylifeapp/viewmodel/ 2>/dev/null && echo "PROBLEM: folder still exists" || echo "OK: folder gone"
```
Expected: `OK: folder gone`

- [ ] **Step 2: Confirm androidMain viewmodel folder has all 11 files**

```bash
ls shared/src/androidMain/kotlin/com/example/logmylifeapp/viewmodel/
```
Expected: 11 files listed.

- [ ] **Step 3: Confirm no lifecycle imports remain in commonMain**

```bash
grep -r "androidx.lifecycle" shared/src/commonMain/
```
Expected: no output.

- [ ] **Step 4: Build Android target**

```bash
./gradlew :shared:compileDebugKotlinAndroid
```
Expected: BUILD SUCCESSFUL

- [ ] **Step 5: Build iOS framework**

```bash
./gradlew :shared:linkDebugFrameworkIosSimulatorArm64
```
Expected: BUILD SUCCESSFUL

- [ ] **Step 6: Commit**

```bash
git add shared/src/androidMain/kotlin/com/example/logmylifeapp/viewmodel/
git add shared/src/commonMain/kotlin/com/example/logmylifeapp/viewmodel/
git add shared/build.gradle.kts
git commit -m "fix(kmp): move ViewModels and factories to androidMain — not KMP-ready for iOS yet"
```
