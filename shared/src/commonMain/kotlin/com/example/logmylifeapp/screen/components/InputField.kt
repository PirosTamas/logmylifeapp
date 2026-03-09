package com.example.logmylifeapp.screen.components

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate

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
