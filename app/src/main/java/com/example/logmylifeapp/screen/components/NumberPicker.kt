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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.logmylifeapp.R

@Composable
fun NumberPicker(
    minValue: Int = 0,
    value: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(
                color = colorResource(R.color.blue_800).copy(0.5f),
                shape = RoundedCornerShape(24.dp)
            )
            .padding(horizontal = 16.dp, vertical = 20.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        IconButton(
            onClick = { if (value > minValue) onValueChange(value - 1) },
            modifier = Modifier.size(32.dp),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = colorResource(R.color.blue_400),
                contentColor = colorResource(R.color.blue_200)
            )
        ) {
            Icon(
                painter = painterResource(R.drawable.outline_remove_24),
                contentDescription = "Decrease"
            )
        }

        Text(
            text = value.toString(),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.off_white)
        )

        IconButton(
            onClick = { onValueChange(value + 1) },
            modifier = Modifier.size(32.dp),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = colorResource(R.color.blue_400),
                contentColor = colorResource(R.color.blue_200)
            )
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Increase"
            )
        }
    }
}

//@Preview(showBackground = true, backgroundColor = 0xFF0F172A)
//@Composable
//fun NumberPickerPreview() {
//
//    var value = 5
//
//    NumberPicker(
//        value = value,
//        onIncrease = { value++ },
//        onDecrease = { if (value > 0) value-- },
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp)
//    )
//}