package com.example.logmylifeapp.screen.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.logmylifeapp.shared.R
import com.example.logmylifeapp.model.WorkoutExercise
import com.example.logmylifeapp.ui.theme.LocalAppColors
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState

@Composable
fun AddWorkoutPlanExercise(
    label: String,
    addMoreClick: () -> Unit,
    exercises: List<WorkoutExercise>,
    emptyListMessage: String,
    onRemove: (WorkoutExercise) -> Unit,
    onReorder: (List<WorkoutExercise>) -> Unit
) {
    val colors = LocalAppColors.current
    val green = colors.primary
    val haptic = LocalHapticFeedback.current


    var localList by remember { mutableStateOf(exercises) }

    LaunchedEffect(exercises) {
        localList = exercises
    }

    val lazyListState = rememberLazyListState()

    val reorderState = rememberReorderableLazyListState(
        lazyListState = lazyListState,
        onMove = { from, to ->
            localList = localList.toMutableList().apply {
                add(to.index, removeAt(from.index))
            }
        }
    )

    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_fitness_center_24),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = colors.primary
                )
                Text(
                    text = label,
                    color = colors.onBackground,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    lineHeight = 28.sp
                )
            }
            Button(
                onClick = { addMoreClick() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.surfaceVariant,
                    contentColor = colors.primary
                ),
                contentPadding = PaddingValues(vertical = 6.dp, horizontal = 12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                    Text(text = "Add More")
                }
            }
        }

        if (localList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(96.dp)
                    .background(
                        color = green.copy(alpha = 0.05f),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .drawBehind {
                        val strokeWidth = 1.dp.toPx()
                        drawRoundRect(
                            color = green.copy(alpha = 0.1f),
                            style = Stroke(
                                width = strokeWidth,
                                pathEffect = PathEffect.dashPathEffect(
                                    floatArrayOf(6.dp.toPx(), 4.dp.toPx()),
                                    0f
                                ),
                                join = StrokeJoin.Miter,
                                miter = 28.96f
                            ),
                            cornerRadius = CornerRadius(12.dp.toPx())
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = emptyListMessage,
                    color = colors.onSurfaceVariant,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        } else {
            LazyRow(
                state = lazyListState,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                items(localList, key = { it.id }) { exercise ->
                    ReorderableItem(reorderState, key = exercise.id) { isDragging ->
                        val scale by animateFloatAsState(
                            targetValue = if (isDragging) 1.08f else 1f,
                            label = "drag_scale"
                        )
                        AddWorkoutPlanExerciseElement(
                            name = exercise.name,
                            imageResourceId = exercise.illustrationResId,
                            predictedTimeInMinutes = exercise.predictedTimeInMinutes,
                            onRemove = { onRemove(exercise) },
                            modifier = with(this) {
                                Modifier
                                    .scale(scale)
                                    .longPressDraggableHandle(
                                        onDragStarted = {
                                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                        },
                                        onDragStopped = {
                                            onReorder(localList)
                                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                        }
                                    )
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AddWorkoutPlanExerciseElement(
    name: String,
    imageResourceId: Int?,
    predictedTimeInMinutes: Int,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = LocalAppColors.current

    Column(
        modifier = modifier
            .width(128.dp)
            .height(152.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .width(126.dp)
                .height(126.dp)
                .border(
                    width = 0.5.dp,
                    color = colors.primary.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(24.dp)
                )
        ) {
            Image(
                painter = painterResource(id = imageResourceId ?: R.drawable.baseline_fitness_center_24),
                contentScale = ContentScale.Crop,
                contentDescription = name,
                modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(24.dp))
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(24.dp))
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.6f))
                        )
                    )
            )
            Text(
                text = ("$predictedTimeInMinutes mins"),
                fontSize = 10.sp,
                color = Color.White.copy(alpha = 0.8f),
                modifier = Modifier.align(Alignment.BottomStart).padding(start = 9.dp, bottom = 9.dp)
            )
            IconButton(
                onClick = onRemove,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .size(36.dp)
                    .background(Color.Black.copy(alpha = 0.6f), CircleShape)
                    .border(0.5.dp, colors.primary.copy(alpha = 0.3f), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Remove exercise",
                    modifier = Modifier.size(18.dp),
                    tint = Color(0xFFFF4757)
                )
            }
        }
        Text(text = name, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = colors.onBackground)
    }
}

@Preview(showBackground = true)
@Composable
fun AddWorkoutPlanExercisePreview() {
    Surface(
        color = Color(0xFF102216),
        modifier = Modifier.fillMaxWidth()
    ) {
        AddWorkoutPlanExercise(
            label = "Stretch",
            exercises = listOf(),
            addMoreClick = {},
            emptyListMessage = "No stretches added yet",
            onRemove = {},
            onReorder = {}
        )
    }
}
