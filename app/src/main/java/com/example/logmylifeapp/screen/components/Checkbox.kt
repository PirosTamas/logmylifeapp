package com.example.logmylifeapp.screen.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.logmylifeapp.ui.theme.LocalAppColors

@Composable
fun CustomCheckbox(
    value: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    size: Dp
) {
    val colors = LocalAppColors.current

    FilledIconButton(
        onClick = { onCheckedChange(!value) },
        modifier = Modifier.size(size).border(
            width = if (!value) 2.dp else 0.dp,
            color = colors.surfaceVariant,
            shape = RoundedCornerShape(8.dp)
        ),
        shape = RoundedCornerShape(8.dp),
        colors = IconButtonDefaults.filledIconButtonColors(
            containerColor = if (value) colors.primary else colors.surfaceVariant,
            contentColor = colors.onPrimary
        ),
    ) {
        if (value) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Checked"
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun CheckboxPreview() {
    CustomCheckbox(
        value = true,
        onCheckedChange = {},
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        size = 28.dp
    )
}
