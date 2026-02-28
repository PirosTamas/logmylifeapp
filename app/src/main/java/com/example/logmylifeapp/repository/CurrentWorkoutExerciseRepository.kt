package com.example.logmylifeapp.repository

import com.example.logmylifeapp.dao.CurrentWorkoutExerciseDao
import com.example.logmylifeapp.dto.CurrentWorkoutExerciseDTO
import kotlinx.coroutines.flow.Flow

class CurrentWorkoutExerciseRepository(
    private val dao: CurrentWorkoutExerciseDao,
) {

//    fun getWorkoutExerciseByWorkoutExerciseLogIdAndSetIndex(exerciseLogId: Int, setIndex: Int): Flow<CurrentWorkoutExerciseDTO?> =
//        dao.getWorkoutExerciseByWorkoutExerciseLogIdAndSetIndex(exerciseLogId,  setIndex);

    fun getWorkoutExerciseBySessionIdAndExerciseIdAndSetIndex(
        sessionId: Int,
        exerciseId: Int,
        setIndex: Int
    ): Flow<CurrentWorkoutExerciseDTO?>{
        return dao.getWorkoutExerciseBySessionIdAndExerciseIdAndSetIndex(sessionId, exerciseId, setIndex)
    }


    
}