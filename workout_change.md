# Workout Plan: Warmup & Stretch Support

## Goal

Extend the workout session flow so that:
1. Warmup exercises are shown first (simple "Done" tap per exercise, no logging)
2. Main exercises follow (existing weight/reps logging)
3. Stretch exercises come last (simple "Done" tap per exercise, no logging)
4. Summary is shown after all stretches are completed

---

## Bugs Found (Fix Before or During Implementation)

| # | File | Line | Bug |
|---|------|------|-----|
| 1 | `Graph.kt` | 97 | `initialWorkoutPlanId` is assigned from `initialQuestionIds.first()` instead of `initialWorkoutIds.first()`. The plan's seed data is linked to a question ID by accident. |
| 2 | `WorkoutHomeViewModel.kt` | 22–28 | `workoutPlansForToday` is `lateinit var` assigned inside a coroutine. Any collector that accesses it before the coroutine finishes crashes with `UninitializedPropertyAccessException`. Should be a `StateFlow` or initialized synchronously. |
| 3 | `WorkoutSessionViewModel.kt` | 44–51 | `numberOfExercises` is loaded in `init` via a coroutine. If `onSuccessClicked` is called before that coroutine finishes, `numberOfExercises` is still 0, causing the session to jump straight to summary after the first set. Needs a loading guard or should be a `StateFlow`. |
| 4 | `WorkoutPreviewScreen.kt` | 76 | `Text("valami szar")` — debug text, should be a proper loading/error state. |
| 5 | `WorkoutSetScreen.kt` | 87 | Same `Text("valami szar")` debug text. |
| 6 | `WorkoutSetScreen.kt` | 139 | Title says `"Select " + workout.exerciseName` — "Select" prefix makes no sense here. Should just be the exercise name. |
| 7 | `WorkoutSetScreen.kt` | 146 | Subtitle is hardcoded `"Legs day"`. Should be the plan name or exercise type label. |
| 8 | `AddWorkoutPlanScreen.kt` | 170–172 | Only warmup section is shown. Main exercises and stretches sections are missing. The `addMoreClick` always passes `WorkoutExerciseType.WARMUP` regardless of section. |

---

## Naming Decision

Rename `WorkoutExerciseType.WORKOUT` → `WorkoutExerciseType.MAIN` to clearly distinguish "a main set exercise" from "a workout session". Update all references.

---

## Step-by-Step Changes

### Step 1 — Database: Two New Cross-Ref Tables

Create two new entity files, mirroring `WorkoutPlanExerciseCrossRef`:

**`model/WorkoutPlanWarmupCrossRef.kt`**
```kotlin
@Entity(
    tableName = "workout_plan_warmup_cross_ref",
    primaryKeys = ["planId", "exerciseId"]
)
data class WorkoutPlanWarmupCrossRef(
    val planId: Int,
    val exerciseId: Int,
    val orderInWarmup: Int
)
```

**`model/WorkoutPlanStretchCrossRef.kt`**
```kotlin
@Entity(
    tableName = "workout_plan_stretch_cross_ref",
    primaryKeys = ["planId", "exerciseId"]
)
data class WorkoutPlanStretchCrossRef(
    val planId: Int,
    val exerciseId: Int,
    val orderInStretch: Int
)
```

Register both in `LogMyLifeDatabase` — add to the `entities = [...]` list and bump the version.
Since `Graph.kt` calls `deleteDatabase` on every launch, no migration is needed yet.

---

### Step 2 — DAOs for the New Tables

**`dao/WorkoutPlanWarmupCrossRefDao.kt`** — same shape as `WorkoutPlanExerciseCrossRefDao`, plus:
```sql
-- Get warmup exercise for a session by order
SELECT we.* FROM workout_session ws
JOIN workout_plan_warmup_cross_ref wpc ON ws.planId = wpc.planId
JOIN workout_exercise we ON wpc.exerciseId = we.id
WHERE ws.id = :sessionId AND wpc.orderInWarmup = :order
```
```sql
-- Count warmups for a session
SELECT COUNT(*) FROM workout_plan_warmup_cross_ref
WHERE planId = (SELECT planId FROM workout_session WHERE id = :sessionId)
```

**`dao/WorkoutPlanStretchCrossRefDao.kt`** — same, using `workout_plan_stretch_cross_ref` and `orderInStretch`.

---

### Step 3 — Repositories

Add `WorkoutPlanWarmupCrossRefRepository` and `WorkoutPlanStretchCrossRefRepository` (same wrapper pattern as existing repos).

Register both in `Graph.kt`.

---

### Step 4 — Update `WorkoutPlanWithExercises`

Add warmup and stretch relations:
```kotlin
data class WorkoutPlanWithExercises(
    @Embedded val plan: WorkoutPlan,

    @Relation(parentColumn = "id", entityColumn = "id",
        associateBy = Junction(WorkoutPlanExerciseCrossRef::class, "planId", "exerciseId"))
    val mainExercises: List<WorkoutExercise>,   // renamed from "exercises"

    @Relation(parentColumn = "id", entityColumn = "id",
        associateBy = Junction(WorkoutPlanWarmupCrossRef::class, "planId", "exerciseId"))
    val warmups: List<WorkoutExercise>,

    @Relation(parentColumn = "id", entityColumn = "id",
        associateBy = Junction(WorkoutPlanStretchCrossRef::class, "planId", "exerciseId"))
    val stretches: List<WorkoutExercise>
)
```

---

### Step 5 — Session Phase Logic in `WorkoutSessionViewModel`

Add a phase enum:
```kotlin
enum class ExercisePhase { WARMUP, MAIN, STRETCH }
```

The ViewModel now needs counts for all three types. Load them in `init`:
```kotlin
var warmupCount: Int = 0
var mainExerciseCount: Int = 0
var stretchCount: Int = 0
```

Replace the single `_exerciseIndex` with a combined concept of `phase + indexInPhase`:
```kotlin
private val _phase = MutableStateFlow(ExercisePhase.WARMUP)
val phase: StateFlow<ExercisePhase> = _phase

private val _indexInPhase = MutableStateFlow(0)
val indexInPhase: StateFlow<Int> = _indexInPhase
```

`workoutExercise` flow: switch the query based on `phase`:
- WARMUP → `getWarmupBySessionIdAndOrder(sessionId, indexInPhase)`
- MAIN → `getWorkoutExerciseBySessionIdAndOrderInWorkout(sessionId, indexInPhase)` (existing)
- STRETCH → `getStretchBySessionIdAndOrder(sessionId, indexInPhase)`

**Advance logic** (shared for warmup/stretch "done" and main "success"):
```
if WARMUP:
    if indexInPhase < warmupCount - 1  → indexInPhase++, stay in WARMUP
    else if mainExerciseCount > 0      → phase = MAIN, indexInPhase = 0
    else if stretchCount > 0           → phase = STRETCH, indexInPhase = 0
    else                               → navigateToSummary()

if MAIN:
    (existing set logic — when last set of last exercise done)
    if stretchCount > 0  → phase = STRETCH, indexInPhase = 0
    else                 → navigateToSummary()

if STRETCH:
    if indexInPhase < stretchCount - 1 → indexInPhase++, stay in STRETCH
    else                               → navigateToSummary()
```

Add a new public function:
```kotlin
fun onWarmupOrStretchDone(navigateToPreview: () -> Unit, navigateToSummary: () -> Unit)
```
This advances the phase/index using the logic above (no DB writes).

---

### Step 6 — New Screen: `WarmupStretchScreen`

A simple screen reusing `WorkoutPreviewScreen`'s layout, but with a "Done" button instead of "Start workout".

```kotlin
@Composable
fun WarmupStretchScreen(
    viewModel: WorkoutSessionViewModel,
    navigateToPreview: () -> Unit,   // reuse same preview screen after done
    navigateToSummary: () -> Unit
)
```

Shows: exercise image, name, equipment, time.
Button: "Done" → calls `viewModel.onWarmupOrStretchDone(...)`.

The existing `WorkoutPreviewScreen` becomes the preview for MAIN exercises only.

---

### Step 7 — Navigation Updates

The `add_workout_graph` nested nav already handles the session. The phase-aware routing works like this:

In Navigation.kt, `WorkoutPreviewScreen` route — check `viewModel.phase`:
- WARMUP or STRETCH → show `WarmupStretchScreen`
- MAIN → show `WorkoutPreviewScreen` (current)

Alternatively, `WorkoutPreviewScreen` itself can branch internally based on `viewModel.phase`. This is simpler since it avoids adding a new route.

**Recommended**: Branch inside `WorkoutPreviewScreen`:
```kotlin
val phase by viewModel.phase.collectAsState()

if (phase == ExercisePhase.WARMUP || phase == ExercisePhase.STRETCH) {
    WarmupStretchContent(exercise = workoutExercise, onDone = { ... })
} else {
    // existing preview content with "Start workout" button
}
```

---

### Step 8 — `AddWorkoutPlanScreen` & `AddWorkoutPlanViewModel`

**ViewModel**: `_warmups`, `_workouts`, `_stretches` are already there. Rename `_workouts` → `_mainExercises` to match the new naming.

**Screen**: Add three `AddWorkoutPlanExercise` sections:
```kotlin
AddWorkoutPlanExercise(
    label = "Warmup",
    exercises = warmups,
    addMoreClick = { navigateToSelectExercise(WorkoutExerciseType.WARMUP) },
    emptyListMessage = "No warmups added yet"
)
AddWorkoutPlanExercise(
    label = "Exercises",
    exercises = mainExercises,
    addMoreClick = { navigateToSelectExercise(WorkoutExerciseType.MAIN) },
    emptyListMessage = "No exercises added yet"
)
AddWorkoutPlanExercise(
    label = "Stretches",
    exercises = stretches,
    addMoreClick = { navigateToSelectExercise(WorkoutExerciseType.STRETCH) },
    emptyListMessage = "No stretches added yet"
)
```

`AddWorkoutPlanExercise` currently hardcodes the label "Warmup" — add a `label: String` parameter.

---

### Step 9 — Save to Database on Plan Creation

In `AddWorkoutPlanViewModel.saveExercises()`, when saving WARMUP/STRETCH, insert into the new cross-ref tables instead of only updating `_warmups`/`_stretches` in memory.

In `addWorkoutPlan()`, after inserting the plan, also insert all cross-refs:
```kotlin
// main exercises
workoutPlanExerciseCrossRefRepository.addWorkoutPlanExerciseCrossRefs(...)
// warmups
workoutPlanWarmupCrossRefRepository.addWorkoutPlanWarmupCrossRefs(...)
// stretches
workoutPlanStretchCrossRefRepository.addWorkoutPlanStretchCrossRefs(...)
```

---

### Step 10 — Seed Data Update

In `Graph.kt` seed, fix the `initialWorkoutPlanId` bug (Step 1 of bug fixes), then add warmup and stretch cross-refs so the demo session includes all three phases.

---

## File Change Summary

| File | Change |
|------|--------|
| `model/WorkoutPlanWarmupCrossRef.kt` | NEW |
| `model/WorkoutPlanStretchCrossRef.kt` | NEW |
| `model/WorkoutPlanWithExercises.kt` | Add warmups/stretches relations, rename exercises → mainExercises |
| `dao/WorkoutPlanWarmupCrossRefDao.kt` | NEW |
| `dao/WorkoutPlanStretchCrossRefDao.kt` | NEW |
| `dao/WorkoutExerciseDao.kt` | Add warmup/stretch query methods |
| `dao/WorkoutPlanExerciseCrossRefDao.kt` | No change |
| `repository/WorkoutPlanWarmupCrossRefRepository.kt` | NEW |
| `repository/WorkoutPlanStretchCrossRefRepository.kt` | NEW |
| `data/LogMyLifeDatabase.kt` | Add new entities and DAOs, bump version |
| `Graph.kt` | Register new repos, fix seed bug |
| `enums/WorkoutExerciseType.kt` | Rename WORKOUT → MAIN |
| `model/ExercisePhase.kt` | NEW enum: WARMUP, MAIN, STRETCH |
| `viewmodel/WorkoutSessionViewModel.kt` | Phase logic, new onWarmupOrStretchDone(), fix init race condition |
| `screen/WorkoutPreviewScreen.kt` | Branch on phase to show WarmupStretchContent or MainPreviewContent |
| `screen/components/AddWorkoutPlanExercise.kt` | Add `label` parameter |
| `screen/AddWorkoutPlanScreen.kt` | Add all 3 exercise sections |
| `viewmodel/AddWorkoutPlanViewModel.kt` | Rename _workouts → _mainExercises, save cross-refs to DB |
| `viewmodel/WorkoutHomeViewModel.kt` | Fix lateinit race condition |
