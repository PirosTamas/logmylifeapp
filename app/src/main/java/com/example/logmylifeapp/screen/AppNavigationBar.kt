package com.example.logmylifeapp.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.logmylifeapp.R
import com.example.logmylifeapp.Screen

@Composable
fun AppNavigationBar(navController: NavController){
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val selectedIndex = when (currentRoute){
        Screen.ProgressHomeScreen.route -> 0
        Screen.WorkoutHomeScreen.route -> 1
        Screen.YearInPixelsScreen.route -> 2
        Screen.SettingsHomeScreen.route -> 3
        else -> 0
    }

    SingleChoiceSegmentedButtonRow(
        modifier = Modifier.padding(16.dp)
    ) {
        SegmentedButton(
            shape = SegmentedButtonDefaults.itemShape(index = 0, count = 4),
            selected = selectedIndex == 0,
            onClick = {
                navController.navigate(Screen.ProgressHomeScreen.route) { launchSingleTop = true }
            },
            label = { Icon(painterResource(R.drawable.outline_analytics_24), null) }
        )
        SegmentedButton(
            shape = SegmentedButtonDefaults.itemShape(index = 1, count = 4),
            selected = selectedIndex == 1,
            onClick = {
                navController.navigate(Screen.WorkoutHomeScreen.route) { launchSingleTop = true }
            },
            label = { Icon(painterResource(R.drawable.outline_exercise_24), null) }
        )
        SegmentedButton(
            shape = SegmentedButtonDefaults.itemShape(index = 2, count = 4),
            selected = selectedIndex == 2,
            onClick = {
                navController.navigate(Screen.YearInPixelsScreen.route) { launchSingleTop = true }
            },
            label = { Icon(painterResource(R.drawable.outline_calendar_view_month_24), null) }
        )
        SegmentedButton(
            shape = SegmentedButtonDefaults.itemShape(index = 3, count = 4),
            selected = selectedIndex == 3,
            onClick = {
                navController.navigate(Screen.SettingsHomeScreen.route) { launchSingleTop = true }
            },
            label = { Icon(Icons.Default.Settings, contentDescription = "Settings") }
        )
    }

}