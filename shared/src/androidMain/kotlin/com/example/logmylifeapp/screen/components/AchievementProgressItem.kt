package com.example.logmylifeapp.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.logmylifeapp.shared.R
import com.example.logmylifeapp.model.AchievementCategory
import com.example.logmylifeapp.model.AchievementProgress
import com.example.logmylifeapp.ui.theme.Green
import com.example.logmylifeapp.ui.theme.LocalAppColors
import kotlinx.datetime.Clock
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

@Composable
fun AchievementProgressItem(
    achievement: AchievementProgress,
    modifier: Modifier = Modifier,
    onEdit: (() -> Unit)? = null
) {
    val colors = LocalAppColors.current
    val progress = achievement.currentSession.toFloat() / achievement.numberOfSessions.toFloat()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(color = colors.surface, shape = RoundedCornerShape(24.dp))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(shape = RoundedCornerShape(8.dp))
                        .background(color = colors.primary.copy(0.1f))
                ) {
                    Icon(
                        painter = painterResource(R.drawable.outline_celebration_24),
                        contentDescription = null,
                        modifier = Modifier.size(18.dp).align(Alignment.Center),
                        tint = Green._600
                    )
                }
                Text(text = achievement.name, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium, color = colors.onSurface)
            }
            val percentage = (achievement.currentSession.toFloat() / achievement.numberOfSessions) * 100
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "${achievement.currentSession} / ${achievement.numberOfSessions} (${percentage.toInt()}%)",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(top = 4.dp),
                    color = colors.primary
                )
//                if (onEdit != null) {
//                    IconButton(onClick = onEdit, modifier = Modifier.size(32.dp)) {
//                        Icon(
//                            imageVector = Icons.Default.Edit,
//                            contentDescription = "Edit",
//                            tint = colors.onSurfaceVariant,
//                            modifier = Modifier.size(16.dp)
//                        )
//                    }
//                }
            }
        }
        LinearProgressIndicator(
            progress = { progress },
            color = colors.primary,
            trackColor = colors.surfaceVariant,
            gapSize = 0.dp,
            drawStopIndicator = {},
            modifier = Modifier.fillMaxWidth().height(10.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AchievementProgressItemPreview() {
    val dummyAchievement = AchievementProgress(
        name = "Reading", startDate = Clock.System.todayIn(TimeZone.currentSystemDefault()),
        scheduledDays = setOf(DayOfWeek.MONDAY), currentSession = 3,
        numberOfSessions = 5, dayChecked = false, category = AchievementCategory.READ
    )
    AchievementProgressItem(achievement = dummyAchievement)
}
