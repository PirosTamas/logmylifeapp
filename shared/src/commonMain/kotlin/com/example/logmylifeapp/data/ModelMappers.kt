package com.example.logmylifeapp.data

fun Workout_plan.toModel() = com.example.logmylifeapp.model.WorkoutPlan(
    id = id,
    name = name,
    scheduledDays = dayOfWeekSetAdapter.decode(scheduledDays),
    currentSession = currentSession,
    numberOfSessions = numberOfSessions,
    startDate = localDateAdapter.decode(startDate)
)

fun Workout_exercise.toModel() = com.example.logmylifeapp.model.WorkoutExercise(
    id = id,
    name = name,
    description = description,
    equipmentNeeded = stringSetAdapter.decode(equipmentNeeded),
    predictedTimeInMinutes = predictedTimeInMinutes,
    illustrationResId = illustrationResId,
    illustrationUri = illustrationUri,
    numberOfSets = numberOfSets,
    restTimeBetweenSets = restTimeBetweenSets
)

fun Workout_session.toModel() = com.example.logmylifeapp.model.WorkoutSession(
    id = id,
    planId = planId,
    date = localDateAdapter.decode(date),
    completed = completed
)

fun Workout_exercise_log.toModel() = com.example.logmylifeapp.model.WorkoutExerciseLog(
    id = id,
    sessionId = sessionId,
    exerciseId = exerciseId
)

fun Workout_exercise_set_log.toModel() = com.example.logmylifeapp.model.WorkoutExerciseSetLog(
    id = id,
    exerciseLogId = exerciseLogId,
    order = order,
    targetReps = targetReps,
    completedReps = completedReps,
    weight = weight,
    success = success,
    description = description
)

fun Workout_plan_exercise_cross_ref.toModel() = com.example.logmylifeapp.model.WorkoutPlanExerciseCrossRef(
    planId = planId,
    exerciseId = exerciseId,
    orderInWorkout = orderInWorkout
)

fun Workout_plan_warmup_cross_ref.toModel() = com.example.logmylifeapp.model.WorkoutPlanWarmupCrossRef(
    planId = planId,
    exerciseId = exerciseId,
    orderInWarmup = orderInWarmup
)

fun Workout_plan_stretch_cross_ref.toModel() = com.example.logmylifeapp.model.WorkoutPlanStretchCrossRef(
    planId = planId,
    exerciseId = exerciseId,
    orderInStretch = orderInStretch
)

fun Achievement_progress.toModel() = com.example.logmylifeapp.model.AchievementProgress(
    id = id,
    name = name,
    category = achievementCategoryAdapter.decode(category),
    scheduledDays = dayOfWeekSetAdapter.decode(scheduledDays),
    currentSession = currentSession,
    numberOfSessions = numberOfSessions,
    dayChecked = dayChecked,
    startDate = localDateAdapter.decode(startDate)
)

fun Daily_life_data_question.toModel() = com.example.logmylifeapp.model.DailyLifeDataQuestion(
    id = id,
    question = question,
    scheduledDays = dayOfWeekSetAdapter.decode(scheduledDays),
    startDate = localDateAdapter.decode(startDate),
    predefinedAnswers = stringSetAdapter.decode(predefinedAnswers),
    customAnswerAllowed = customAnswerAllowed
)

fun Daily_life_data_answer.toModel() = com.example.logmylifeapp.model.DailyLifeDataAnswer(
    id = id,
    questionId = questionId,
    answer = answer,
    createdAt = localDateAdapter.decode(createdAt)
)
