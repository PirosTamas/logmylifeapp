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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.logmylifeapp.screen.components.FormControl
import com.example.logmylifeapp.screen.components.InputField
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun AddProgressScreen(navigateToHome: () -> Unit) {
    val homeViewModel: HomeViewModel = viewModel()
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "Add progress",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp),
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold
        )
        fields.forEach { field ->
            FormControl(field)
        }
        Button(onClick = {
            val name = (fields[0] as InputField.TextField).value
            val category = (fields[1] as InputField.DropdownField).selected
            val scheduledDays = (fields[2] as InputField.MultiSelectDays).selectedDays
            val numberOfSessions = (fields[3] as InputField.NumberField).value
            val startDate = (fields[4] as InputField.DateField).date ?: LocalDate.now()

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

                homeViewModel.addAchievementProgress(newProgress)

                navigateToHome()
            } else {
                Toast.makeText(context, "Please fill all required fields", Toast.LENGTH_SHORT).show()
            }

            navigateToHome()
        }) { Text("Save") }
    }
}


