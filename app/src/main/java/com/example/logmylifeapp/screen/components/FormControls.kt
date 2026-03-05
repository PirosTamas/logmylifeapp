package com.example.logmylifeapp.screen.components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.logmylifeapp.ui.theme.LocalAppColors
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun labelText(text: String) {
    val colors = LocalAppColors.current
    Text(
        text = text.uppercase(),
        color = colors.onSurfaceVariant,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 20.sp,
        fontSize = 14.sp
    )
}

@Composable
fun FormControl(inputField: InputField, modifier: Modifier = Modifier) {
    val colors = LocalAppColors.current
    Column(
        modifier = modifier
            .padding(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {


        when (inputField) {
            is InputField.TextField -> {
                labelText(text = inputField.label)
                var text by remember { mutableStateOf(inputField.value) }
                OutlinedTextField(
                    value = text,
                    onValueChange = {
                        text = it
                        inputField.value = it
                    },
                    placeholder = {
                        Text(
                            text = inputField.placeholder,
                            color = colors.onSurfaceVariant
                        )
                    },
                    textStyle = LocalTextStyle.current.copy(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = colors.onBackground
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .shadow(
                            elevation = 2.dp,
                            shape = RoundedCornerShape(24.dp),
                            clip = false,
                            spotColor = Color(0x0D000000),
                            ambientColor = Color.Transparent
                        ),
                    shape = RoundedCornerShape(24.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = colors.surface,
                        unfocusedContainerColor = colors.surface,
                        focusedBorderColor = colors.inputBorder,
                        unfocusedBorderColor = colors.inputBorder,
                        cursorColor = colors.primary
                    ),
                )
            }

            is InputField.NumberField -> {
                labelText(text = inputField.label)
                var number by remember { mutableStateOf(inputField.value?.toString() ?: "") }
                OutlinedTextField(
                    value = number,
                    onValueChange = {
                        number = it.filter { char -> char.isDigit() }
                        inputField.value = number.toIntOrNull()
                    },
                    placeholder = {
                        Text(
                            text = inputField.placeholder,
                            color = colors.onSurfaceVariant
                        )
                    },
                    textStyle = LocalTextStyle.current.copy(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = colors.onBackground
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .shadow(
                            elevation = 2.dp,
                            shape = RoundedCornerShape(24.dp),
                            clip = false,
                            spotColor = Color(0x0D000000),
                            ambientColor = Color.Transparent
                        ),
                    shape = RoundedCornerShape(24.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = colors.surface,
                        unfocusedContainerColor = colors.surface,
                        focusedBorderColor = colors.inputBorder,
                        unfocusedBorderColor = colors.inputBorder,
                        cursorColor = colors.primary
                    ),
                )
            }

            is InputField.DropdownField -> {
                labelText(text = inputField.label)
                var expanded by remember { mutableStateOf(false) }
                var selectedText by remember { mutableStateOf(inputField.selected ?: "") }
                val arrowRotation by animateFloatAsState(
                    targetValue = if (expanded) 180f else 0f,
                    animationSpec = tween(durationMillis = 200, easing = LinearOutSlowInEasing),
                    label = "arrowRotation"
                )

                val green = colors.primary

                val borderColor = if (expanded) green else colors.inputBorder
                val borderWidth = if (expanded) 1.5.dp else 1.dp

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(
                            elevation = 2.dp,
                            shape = RoundedCornerShape(16.dp),
                            clip = false,
                            spotColor = Color(0x0D000000),
                            ambientColor = Color.Transparent
                        )
                        .border(width = borderWidth, color = borderColor, shape = RoundedCornerShape(16.dp))
                        .clip(RoundedCornerShape(16.dp))
                        .background(colors.surface)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expanded = !expanded }
                            .padding(horizontal = 16.dp, vertical = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = selectedText.ifBlank { "Select ${inputField.label.lowercase()}" },
                            color = if (selectedText.isBlank()) colors.onSurfaceVariant else colors.onSurface,
                            fontSize = 16.sp
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            tint = if (expanded) green else colors.onSurfaceVariant,
                            modifier = Modifier
                                .size(24.dp)
                                .rotate(arrowRotation)
                        )
                    }
                    if (expanded) {
                        HorizontalDivider(color = green.copy(alpha = 0.4f), thickness = 1.dp)
                        inputField.options.forEach { option ->
                            val isSelected = option == selectedText
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(if (isSelected) green.copy(alpha = 0.15f) else colors.surface)
                                    .clickable {
                                        selectedText = option
                                        inputField.selected = option
                                        expanded = false
                                    }
                                    .padding(horizontal = 16.dp, vertical = 16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = option,
                                    fontSize = 16.sp,
                                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                                    color = colors.onSurface
                                )
                            }
                        }
                    }
                }
            }

            is InputField.DateField -> {
                var showDatePicker by remember { mutableStateOf(false) }
                var selectedDate by remember { mutableStateOf(inputField.date) }

                labelText(text = inputField.label)
                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = selectedDate?.format(DateTimeFormatter.ofPattern("yyyy. MM.dd.")) ?: "",
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = null,
                                tint = colors.primary
                            )
                        },
                        textStyle = LocalTextStyle.current.copy(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = colors.onBackground
                        ),
                        placeholder = {
                            Text(text = "Select date", color = colors.onSurfaceVariant)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = colors.inputBackground,
                            unfocusedContainerColor = colors.inputBackground,
                            focusedBorderColor = colors.inputBorder,
                            unfocusedBorderColor = colors.inputBorder,
                            cursorColor = colors.primary
                        )
                    )
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .clip(RoundedCornerShape(24.dp))
                            .clickable { showDatePicker = true }
                    )
                }
                if (showDatePicker) {
                    DatePickerModal(
                        onDateSelected = { millis ->
                            selectedDate = millis?.let {
                                Instant.ofEpochMilli(it)
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate()
                            }
                            inputField.date = selectedDate
                            showDatePicker = false
                        },
                        onDismiss = { showDatePicker = false }
                    )
                }
            }

            is InputField.MultiSelectDays -> {
                labelText(text = inputField.label)
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
    data class TextField(
        val label: String,
        val placeholder: String = "",
        var value: String = "",
    ) : InputField()

    data class NumberField(
        val label: String,
        val placeholder: String = "",
        var value: Int? = null
    ) : InputField()

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

    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        days.forEach { day ->
            val isSelected = day in selectedDays
            DayCircleChip(
                day = day,
                selected = isSelected,
                onClick = { onDayToggle(day) }
            )
        }
    }
}

@Composable
fun DayCircleChip(
    day: DayOfWeek,
    selected: Boolean,
    onClick: () -> Unit
) {
    val colors = LocalAppColors.current
    val green = colors.primary
    val borderColor = green.copy(alpha = if (selected) 1f else 0.2f)
    val backgroundColor = if (selected) green.copy(alpha = 0.2f) else Color.Transparent
    val textColor = if (selected) green else colors.onSurfaceVariant

    Box(
        modifier = Modifier
            .size(44.dp)
            .shadow(
                elevation = if (selected) 15.dp else 0.dp,
                shape = CircleShape,
                spotColor = green.copy(alpha = 0.2f)
            )
            .background(backgroundColor, CircleShape)
            .border(
                width = 2.dp,
                color = borderColor,
                shape = CircleShape
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.getDisplayName(TextStyle.NARROW, Locale.getDefault()),
            color = textColor,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, backgroundColor = 0xF6F8F6)
@Composable
fun FormControlPreview() {

    val fields = listOf(
        InputField.TextField(
            label = "Name",
            value = "Sample Text"
        ),

        InputField.NumberField(
            label = "Age",
            value = 25
        ),

        InputField.DropdownField(
            label = "Category",
            options = listOf("Chest", "Back", "Legs"),
            selected = ""
        ),

        InputField.DateField(
            label = "Start Date",
            date = LocalDate.now()
        ),

        InputField.MultiSelectDays(
            label = "Workout Days",
            selectedDays = setOf(
                DayOfWeek.MONDAY,
                DayOfWeek.WEDNESDAY
            )
        )
    )

    Column(modifier = Modifier.padding(32.dp)) {
        fields.forEach { field ->
            FormControl(inputField = field)
        }
    }
}

