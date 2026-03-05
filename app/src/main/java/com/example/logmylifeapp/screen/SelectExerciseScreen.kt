package com.example.logmylifeapp.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.logmylifeapp.R
import com.example.logmylifeapp.ui.theme.LocalAppColors
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.logmylifeapp.enums.WorkoutExerciseType
import com.example.logmylifeapp.viewmodel.AddWorkoutPlanViewModel

@Composable
fun SelectExerciseScreen(viewModel: AddWorkoutPlanViewModel, navigateToAddWorkoutPlan: () -> Unit, workoutExerciseType: WorkoutExerciseType) {
    val exercises by viewModel.workoutExercises.collectAsState()
    var query by remember { mutableStateOf("") }

    val filteredExercises by remember(query, exercises) {
        derivedStateOf {
            exercises.filter {
                it.name.contains(query, ignoreCase = true)
            }
        }
    }

    val selectedExerciseIds by viewModel.selectedExerciseIds.collectAsState()

//    val exercises = listOf(
//        WorkoutExercise(
//            name = "Squats",
//            description = "Leg exercise",
//            equipmentNeeded = setOf("None"),
//            predictedTimeInMinutes = 10,
//            illustrationResId = R.drawable.warmup2,
//            illustrationUri = null,
//            numberOfSets = 3,
//            restTimeBetweenSets = 30
//        ),
//        WorkoutExercise(
//            name = "Push-ups",
//            description = "Upper body exercise",
//            equipmentNeeded = setOf("None"),
//            predictedTimeInMinutes = 5,
//            illustrationResId = R.drawable.warmup1,
//            illustrationUri = null,
//            numberOfSets = 3,
//            restTimeBetweenSets = 30
//        ),
//    )

    val colors = LocalAppColors.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = colors.background,
        topBar = {
            val borderColor = colors.primary.copy(alpha = 0.1f)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(74.dp)
                    .drawBehind {
                        drawLine(
                            color = borderColor,
                            start = Offset(0f, size.height),
                            end = Offset(size.width, size.height),
                            strokeWidth = 1.dp.toPx()
                        )
                    }
            )
            {
                IconButton(
                    onClick = { /*TODO close */ },
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close, // X icon
                        contentDescription = "Close",
                        tint = Color(0xFF94A3BB)
                    )
                }
                Text(
                    text = "Select " + workoutExerciseType.name.lowercase(),
                    modifier = Modifier.align(Alignment.Center),
                    fontSize = 18.sp,
                    lineHeight = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = colors.onBackground
                )
            }
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    SearchBar(
                        query = query,
                        onQueryChange = { query = it },
                    )
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(filteredExercises) { exercise ->
                            SelectExerciseElement(
                                id = exercise.id,
                                name = exercise.name,
                                imageResourceId = exercise.illustrationResId,
                                predictedTimeInMinutes = exercise.predictedTimeInMinutes,
                                active = selectedExerciseIds.contains(exercise.id),
                                selectedExerciseIds = selectedExerciseIds,
                                onClick = { clickedId ->
                                    viewModel.toggleExerciseSelection(clickedId)
                                }
                            )

                        }


                    }
                }
                ConfirmLayout(
                    selectedExerciseIds.size,
                    modifier = Modifier.align(Alignment.BottomCenter),
                    onConfirm = {
                        viewModel.saveExercises(workoutExerciseType)
                        navigateToAddWorkoutPlan() })
            }
        },
    )
}


@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = LocalAppColors.current
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = {
            Text(
                text = "Search...",
                color = colors.onSurfaceVariant
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = colors.onSurfaceVariant
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Clear",
                        tint = colors.primary
                    )
                }
            }
        },
        singleLine = true,
        textStyle = LocalTextStyle.current.copy(
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = colors.onBackground
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = colors.inputBackground,
            unfocusedContainerColor = colors.inputBackground,
            focusedBorderColor = colors.inputBorder,
            unfocusedBorderColor = colors.inputBorder,
            cursorColor = colors.primary,
            focusedLeadingIconColor = colors.primary,
            unfocusedLeadingIconColor = colors.primary
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                // Trigger search here
            }
        )
    )
}

@Composable
fun ConfirmLayout(
    selectedExerciseIdsLength: Int,
    modifier: Modifier = Modifier,
    onConfirm: () -> Unit
) {
    val colors = LocalAppColors.current

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(16.dp))
                .background(colors.primary.copy(alpha = 0.4f))
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(colors.primary.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = selectedExerciseIdsLength.toString(),
                    color = colors.primary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Column(verticalArrangement = Arrangement.Center) {
                Text(
                    text = "Exercises Selected",
                    color = colors.onBackground,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Tap save to add to plan".uppercase(),
                    color = colors.onSurfaceVariant,
                    fontSize = 10.sp,
                    lineHeight = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Button(
                onClick = { onConfirm() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.primary,
                    contentColor = colors.onPrimary
                ),
                contentPadding = PaddingValues(vertical = 8.dp, horizontal = 24.dp)
            ) {
                Text(
                    text = "Confirm",
                    fontSize = 10.sp,
                    lineHeight = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


@Composable
fun SelectExerciseElement(
    id: Int,
    name: String,
    imageResourceId: Int?,
    predictedTimeInMinutes: Int,
    active: Boolean,
    selectedExerciseIds: Set<Int>,
    onClick: (id: Int) -> Unit
) {
    val colors = LocalAppColors.current
    Box(
        modifier = Modifier
            .width(164.dp)
            .height(202.dp)
            .border(
                width = if (active) 0.5.dp else 0.dp,
                color = colors.primary.copy(alpha = 0.2f),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onClick(id) }
    ) {
        Image(
            painter = painterResource(
                id = imageResourceId ?: R.drawable.baseline_fitness_center_24
            ),
            contentScale = ContentScale.Crop,
            contentDescription = name,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp))

        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp))
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.6f),
                        )
                    )
                )

        )
        Text(
            text = ("$predictedTimeInMinutes mins"),
            fontSize = 10.sp,
            color = Color.White.copy(alpha = 0.8f),
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 9.dp, bottom = 9.dp)
        )
        if(selectedExerciseIds.contains(id)) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .clip(CircleShape)
                    .background(colors.primary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Selected",
                    tint = colors.onPrimary,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }

}

//
//@Preview(showBackground = true)
//@Composable
//fun SelectExerciseScreenPreview() {
//    SelectExerciseScreen({}, WorkoutExerciseType.WARMUP)
//}


