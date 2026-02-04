package com.example.logmylifeapp.screen.components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
import kotlin.text.ifEmpty

@Composable
fun FormControl(inputField: InputField) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {


        when (inputField) {
            is InputField.TextField -> {
                Text(text = inputField.label)
                var text by remember { mutableStateOf(inputField.value) }
                OutlinedTextField(
                    value = text,
                    onValueChange = {
                        text = it
                        inputField.value = it
                    },
                )
            }

            is InputField.NumberField -> {
                Text(text = inputField.label)
                var number by remember { mutableStateOf(inputField.value?.toString() ?: "") }
                OutlinedTextField(
                    value = number,
                    onValueChange = {
                        number = it.filter { char -> char.isDigit() }
                        inputField.value = number.toIntOrNull()
                    },
                )
            }

            is InputField.DropdownField -> {
                Text(text = inputField.label)
                var expanded by remember { mutableStateOf(false) }
                var selectedText by remember { mutableStateOf(inputField.selected ?: "") }

                Box {
                    Button(onClick = {
                        expanded = true
                    }) {
                        Text(selectedText.ifEmpty { "Select category" })
                        val rotation by animateFloatAsState(
                            targetValue = if (expanded) 180f else 0f,
                            animationSpec = tween(
                                durationMillis = 600,
                                easing = LinearOutSlowInEasing
                            ),
                            label = "ArrowRotation"
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            modifier = Modifier.rotate(rotation)
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        inputField.options.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    selectedText = option
                                    inputField.selected = option
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }

            is InputField.DateField -> {
                var showDatePicker by remember { mutableStateOf(false) }
                var selectedDate by remember { mutableStateOf(inputField.date) }

                Text(text = inputField.label)
                OutlinedTextField(
                    value = selectedDate?.format(DateTimeFormatter.ofPattern("yyyy. MM.dd.")) ?: "",
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { showDatePicker = !showDatePicker }) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "Select date"
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )
                if(showDatePicker){
                    DatePickerModal(
                        onDateSelected = { millis ->
                            selectedDate = millis?.let{
                                Instant.ofEpochMilli(it)
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate()
                            }
                            inputField.date = selectedDate
                            showDatePicker = false
                        },
                        onDismiss = { showDatePicker = false}
                    )
                }
            }

            is InputField.MultiSelectDays -> {
                Text(text = inputField.label)
                var selectedDays by remember { mutableStateOf(inputField.selectedDays) }
                WeekDayPicker(selectedDays = selectedDays, onDayToggle = { day ->
                    selectedDays =
                        if (day in selectedDays) selectedDays - day
                        else selectedDays + day
                    inputField.selectedDays = selectedDays
                }, modifier = Modifier.fillMaxWidth())
            }
        }
    }
}

sealed class InputField {
    data class TextField(val label: String, var value: String = "") : InputField()
    data class NumberField(val label: String, var value: Int? = null) : InputField()
    data class DropdownField(
        val label: String,
        val options: List<String>,
        var selected: String? = null
    ) : InputField()

    data class DateField(val label: String, var date: LocalDate? = null) : InputField()
    data class MultiSelectDays(val label: String, var selectedDays: Set<DayOfWeek> = emptySet()) :
        InputField()
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}


@Composable
fun WeekDayPicker(
    selectedDays: Set<DayOfWeek>,
    onDayToggle: (DayOfWeek) -> Unit,
    modifier: Modifier
) {
    val days = DayOfWeek.entries.toTypedArray()

    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        days.forEach { day ->
            val isSelected = day in selectedDays

            FilterChip(
                selected = isSelected,
                onClick = { onDayToggle(day) },
                label = {
                    Text(day.getDisplayName(TextStyle.NARROW, Locale.getDefault()))
                }
            )

        }
    }
}

