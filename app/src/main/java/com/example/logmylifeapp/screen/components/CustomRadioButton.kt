package com.example.logmylifeapp.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.logmylifeapp.ui.theme.LocalAppColors

@Composable
fun CustomRadioButton(
    options: Set<String>,
    selected: String?,
    onSelectedChange: ((String) -> Unit),
    otherAllowed: Boolean = false,
    otherValue: String = "",
    onOtherValueChange: (String) -> Unit = {}
) {
    val colors = LocalAppColors.current
    val allOptions = if (otherAllowed) options + "Other" else options

    Column(
        modifier = Modifier.selectableGroup(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        allOptions.forEach { option ->
            val isSelected = selected == option

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = isSelected,
                        onClick = { onSelectedChange(option) },
                        role = Role.RadioButton
                    )
                    .background(color = colors.surface, shape = RoundedCornerShape(24.dp))
                    .border(
                        color = if (isSelected) colors.primary else Color.Transparent,
                        width = 2.dp,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = option,
                    fontSize = 18.sp,
                    lineHeight = 28.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.onSurface
                )

                RadioButton(
                    selected = isSelected,
                    onClick = { onSelectedChange(option) },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = colors.primary,
                        unselectedColor = colors.onSurfaceVariant
                    )
                )
            }

            if (otherAllowed && option == "Other" && isSelected) {
                TextField(
                    minLines = 5,
                    value = otherValue,
                    onValueChange = onOtherValueChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = colors.surfaceVariant
                    ),
                    placeholder = {
                        Text(
                            text = "Tell us more about how you feel...",
                            fontSize = 16.sp,
                            lineHeight = 24.sp,
                            color = colors.onSurfaceVariant
                        )
                    },
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CustomRadioButtonPreview() {
    CustomRadioButton(
        setOf("Great", "Not so well"),
        "Other",
        {},
        true,
        "",
        {}
    )
}
