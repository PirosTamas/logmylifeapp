package com.example.logmylifeapp.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.logmylifeapp.shared.R
import com.example.logmylifeapp.ui.theme.LocalAppColors

@Composable
fun NumberPicker(
    minValue: Int = 0,
    value: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = LocalAppColors.current

    Row(
        modifier = modifier
            .background(color = colors.surfaceVariant, shape = RoundedCornerShape(24.dp))
            .padding(horizontal = 16.dp, vertical = 20.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = { if (value > minValue) onValueChange(value - 1) },
            modifier = Modifier.size(32.dp),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = colors.surface,
                contentColor = colors.onSurfaceVariant
            )
        ) {
            Icon(painter = painterResource(R.drawable.outline_remove_24), contentDescription = "Decrease")
        }

        Text(text = value.toString(), fontSize = 24.sp, fontWeight = FontWeight.Bold, color = colors.onBackground)

        IconButton(
            onClick = { onValueChange(value + 1) },
            modifier = Modifier.size(32.dp),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = colors.surface,
                contentColor = colors.onSurfaceVariant
            )
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Increase")
        }
    }
}
