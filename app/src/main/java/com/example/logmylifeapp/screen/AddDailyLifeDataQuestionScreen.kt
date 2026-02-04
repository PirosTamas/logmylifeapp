package com.example.logmylifeapp.screen

import android.widget.Toast
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.logmylifeapp.viewmodel.HomeViewModel
import com.example.logmylifeapp.model.AchievementCategory
import com.example.logmylifeapp.model.AchievementProgress
import com.example.logmylifeapp.model.DailyLifeDataQuestion
import com.example.logmylifeapp.screen.components.FormControl
import com.example.logmylifeapp.screen.components.InputField
import com.example.logmylifeapp.viewmodel.AddDailyLifeQuestionViewModel
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
import kotlin.collections.minus
import kotlin.collections.plus

@Composable
fun AddDailyLifeDataQuestionScreen(navigateToHome: () -> Unit) {
    val viewModel: AddDailyLifeQuestionViewModel = viewModel()
    val context = LocalContext.current
    val fields = remember {listOf(
        InputField.TextField(label = "Question"),
        InputField.MultiSelectDays(label = "Scheduled Days"),
        InputField.DateField(label = "Start Date")
    )}
    var customAnswerAllowed by remember { mutableStateOf(false) }
    val predefinedAnswers = remember { mutableStateListOf<String>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "Add day finish question",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp),
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold
        )
        fields.forEach { field ->
            FormControl(field)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Allow custom answer",
                fontSize = 16.sp
            )

            Switch(
                checked = customAnswerAllowed,
                onCheckedChange = { customAnswerAllowed = it }
            )
        }
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            predefinedAnswers.forEachIndexed { index, answer ->
                OutlinedTextField(value = answer,
                    onValueChange = { predefinedAnswers[index] = it },
                    label = { Text("Predefined Answer ${index + 1}") },
                    modifier = Modifier.fillMaxWidth())
            }
        }
        Button(onClick = {
            predefinedAnswers.add("")
        }, modifier = Modifier.padding(top = 8.dp)) { Text("Add predefined question") }
        Button(onClick = {
            val question = (fields[0] as InputField.TextField).value
            val scheduledDays = (fields[1] as InputField.MultiSelectDays).selectedDays
            val startDate = (fields[2] as InputField.DateField).date ?: LocalDate.now()

            if (question.isNotBlank()) {
                val newDailyLifeDataQuestion = DailyLifeDataQuestion(
                    id = 0,
                    question = question,
                    scheduledDays = scheduledDays,
                    startDate = startDate,
                    customAnswerAllowed = customAnswerAllowed,
                    predefinedAnswers = predefinedAnswers.filter{ it.isNotBlank()}.toSet()
                )

                viewModel.addDailyLifeDataQuestion(newDailyLifeDataQuestion)

                navigateToHome()
            } else {
                Toast.makeText(context, "Please fill all required fields", Toast.LENGTH_SHORT).show()
            }

        }) { Text("Save") }
    }
}

