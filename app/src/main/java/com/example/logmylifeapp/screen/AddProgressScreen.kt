package com.example.logmylifeapp.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import java.time.LocalDate

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


