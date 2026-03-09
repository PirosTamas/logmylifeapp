package com.example.logmylifeapp.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.logmylifeapp.shared.R
import com.example.logmylifeapp.model.AchievementProgress
import com.example.logmylifeapp.ui.theme.LocalAppColors
import com.example.logmylifeapp.ui.theme.Orange
import com.example.logmylifeapp.viewmodel.ProgressViewModel

@Composable
fun DailyAchievementItem(
    achievement: AchievementProgress,
    modifier: Modifier = Modifier,
    viewModel: ProgressViewModel
) {
    val colors = LocalAppColors.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, colors.surfaceVariant, RoundedCornerShape(24.dp))
            .background(colors.surface, RoundedCornerShape(24.dp))
            .padding(vertical = 16.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(shape = RoundedCornerShape(16.dp))
                    .background(color = Orange._500.copy(0.1f))
            ) {
                Icon(
                    painter = painterResource(R.drawable.outline_celebration_24),
                    contentDescription = null,
                    modifier = Modifier
                        .size(18.dp)
                        .align(Alignment.Center),
                    tint = Orange._500
                )
            }

            Column(modifier = modifier) {
                Text(
                    text = achievement.name,
                    style = if (achievement.dayChecked) {
                        TextStyle(textDecoration = TextDecoration.LineThrough)
                    } else {
                        TextStyle(textDecoration = TextDecoration.None)
                    },
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = colors.onSurface
                )

                Text(
                    text = "Day ${achievement.currentSession}",
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    fontWeight = FontWeight.ExtraLight,
                    color = colors.onSurfaceVariant
                )
            }
        }
        Checkbox(
            checked = achievement.dayChecked,
            onCheckedChange = { isChecked ->
                val updated = if (isChecked) {
                    achievement.copy(
                        dayChecked = true,
                        currentSession = (achievement.currentSession + 1).coerceAtMost(achievement.numberOfSessions)
                    )
                } else {
                    achievement.copy(
                        dayChecked = false,
                        currentSession = (achievement.currentSession - 1).coerceAtLeast(0)
                    )
                }
                viewModel.updateAchievementProgress(updated)
            },
            colors = CheckboxDefaults.colors(
                checkedColor = colors.primary,
                checkmarkColor = colors.onPrimary
            ),
            modifier = Modifier.scale(1.3f).clip(RoundedCornerShape(8.dp))
        )
    }
}
