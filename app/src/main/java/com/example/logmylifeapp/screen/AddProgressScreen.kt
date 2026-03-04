package com.example.logmylifeapp.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.logmylifeapp.R
import com.example.logmylifeapp.viewmodel.HomeViewModel
import com.example.logmylifeapp.model.AchievementCategory
import com.example.logmylifeapp.model.AchievementProgress
import com.example.logmylifeapp.screen.components.FormControl
import com.example.logmylifeapp.screen.components.InputField
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun AddProgressScreen(navigateToHome: () -> Unit) {
//    val homeViewModel: HomeViewModel = viewModel()
    val context = LocalContext.current
    val fields = listOf(
        InputField.TextField(label = "Name"),
        InputField.DropdownField(
            label = "Category",
            options = AchievementCategory.entries.map { it.name }),
        InputField.MultiSelectDays(label = "Scheduled Days"),
        InputField.NumberField(label = "Number of Sessions"),
        InputField.DateField(label = "Start Date")
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = colorResource(R.color.off_white_200),
        topBar = {
            val borderColor = colorResource(R.color.green_200).copy(alpha = 0.1f)
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
                    onClick = {
                        navigateToHome()
                    },
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color(0xFF94A3BB)
                    )
                }
                Text(
                    text = "Add New Progress",
                    modifier = Modifier.align(Alignment.Center),
                    fontSize = 18.sp,
                    lineHeight = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.blue_900)
                )
            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {

                Column(modifier = Modifier.weight(1f)) {
                    fields.forEach { field ->
                        FormControl(field)
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 24.dp),
                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.green_200),
                            contentColor = colorResource(R.color.green_900)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        onClick = {
                            val name = (fields[0] as InputField.TextField).value
                            val category = (fields[1] as InputField.DropdownField).selected
                            val scheduledDays =
                                (fields[2] as InputField.MultiSelectDays).selectedDays
                            val numberOfSessions = (fields[3] as InputField.NumberField).value
                            val startDate =
                                (fields[4] as InputField.DateField).date ?: LocalDate.now()

                            if (name.isNotBlank() && category != null && numberOfSessions != null) {
                                val newProgress = AchievementProgress(
                                    id = 0,
                                    name = name,
                                    category = AchievementCategory.valueOf(category),
                                    scheduledDays = scheduledDays,
                                    currentSession = 0,
                                    numberOfSessions = numberOfSessions,
                                    startDate = startDate
                                )

//                        homeViewModel.addAchievementProgress(newProgress)

                                navigateToHome()
                            } else {
                                Toast.makeText(
                                    context,
                                    "Please fill all required fields",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            navigateToHome()

                        }) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp),
                            )
                            Text(
                                text = "Back to home",
                                fontSize = 18.sp,
                                lineHeight = 28.sp,
                                fontWeight = FontWeight.Bold,
                            )

                        }

                    }
                }
            }
        }
    )


}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
fun AddProgressScreenPreview() {
    AddProgressScreen({})
}

