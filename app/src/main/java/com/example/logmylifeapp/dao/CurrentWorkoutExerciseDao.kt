package com.example.logmylifeapp.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.logmylifeapp.dto.CurrentWorkoutExerciseDTO
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrentWorkoutExerciseDao {
    @Query(
        """
    SELECT
        we.name AS exerciseName,
        wesl.targetReps,
        wesl.completedReps,
        wesl.weight,
        wesl.`order`,
        we.numberOfSets,
        we.restTimeBetweenSets,
        ws.date
    FROM workout_exercise we
    JOIN workout_plan_exercise_cross_ref wpe
        ON we.id = wpe.exerciseId
    JOIN workout_plan wp
        ON wpe.planId = wp.id
    JOIN workout_session ws
        ON wp.id = ws.planId
    JOIN workout_exercise_log wel
        ON ws.id = wel.sessionId
        AND we.id = wel.exerciseId
    JOIN workout_exercise_set_log wesl
        ON wel.id = wesl.exerciseLogId
    WHERE ws.id = (
        SELECT id
        FROM workout_session
        WHERE planId = (
            SELECT ws2.planId
            FROM workout_session ws2
            WHERE ws2.id = (
                SELECT sessionId
                FROM workout_exercise_log
                WHERE id = :workoutExerciseLogId
            )
        )
        AND completed = 1
        ORDER BY date DESC
        LIMIT 1
    )
    AND
    we.id = (
         SELECT exerciseId
                         FROM workout_exercise_log
                         WHERE id = :workoutExerciseLogId
    ) AND
   wesl.`order` = :setIndex
    ORDER BY we.name, wesl.`order`;
    """
    )
    fun getWorkoutExerciseByWorkoutExerciseLogIdAndSetIndex(workoutExerciseLogId: Int, setIndex: Int): Flow<CurrentWorkoutExerciseDTO?>

    @Query(
        """
    SELECT we.name AS exerciseName, wesl.targetReps, wesl.completedReps, wesl.weight, wesl.`order`, we.numberOfSets, we.restTimeBetweenSets, ws.date FROM workout_exercise we JOIN workout_plan_exercise_cross_ref wpe ON we.id = wpe.exerciseId JOIN workout_plan wp ON wpe.planId = wp.id JOIN workout_session ws ON wp.id = ws.planId JOIN workout_exercise_log wel ON ws.id = wel.sessionId AND we.id = wel.exerciseId JOIN workout_exercise_set_log wesl ON wel.id = wesl.exerciseLogId WHERE ws.id = ( SELECT id FROM workout_session WHERE planId = ( SELECT ws2.planId FROM workout_session ws2 WHERE ws2.id = :sessionId ) AND completed = 1 ORDER BY date DESC LIMIT 1 ) AND we.id = :exerciseId and wesl.`order` = :setIndex;
    """
    )
    fun getWorkoutExerciseBySessionIdAndExerciseIdAndSetIndex(
        sessionId: Int,
        exerciseId: Int,
        setIndex: Int
    ): Flow<CurrentWorkoutExerciseDTO?>
}