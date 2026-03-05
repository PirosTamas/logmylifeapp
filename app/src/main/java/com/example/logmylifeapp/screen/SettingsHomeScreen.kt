package com.example.logmylifeapp.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.logmylifeapp.R
import com.example.logmylifeapp.ui.theme.LocalAppColors
import com.example.logmylifeapp.viewmodel.SettingsViewModel

// hiltViewModel() is the Hilt-aware replacement for viewModel().
// Hilt automatically injects all constructor parameters — no factory needed.
@Composable
fun SettingsHomeScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val colors = LocalAppColors.current
    val name by viewModel.name.collectAsState()
    val weightUnit by viewModel.weightUnit.collectAsState()
    val waterReminderEnabled by viewModel.waterReminderEnabled.collectAsState()
    val isDarkMode by viewModel.isDarkMode.collectAsState()

    var showNameDialog by remember { mutableStateOf(false) }
    var nameInput by remember { mutableStateOf("") }

    if (showNameDialog) {
        AlertDialog(
            onDismissRequest = { showNameDialog = false },
            title = { Text("Edit name") },
            text = {
                OutlinedTextField(
                    value = nameInput,
                    onValueChange = { nameInput = it },
                    label = { Text("Your name") },
                    singleLine = true
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.setName(nameInput)
                    showNameDialog = false
                }) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { showNameDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colors.background)
            .padding(start = 24.dp, top = 56.dp, end = 24.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // ── Header ──────────────────────────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.profile),
                contentDescription = "profile picture",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(
                        width = 1.dp,
                        color = colors.primary.copy(alpha = 0.3f),
                        shape = CircleShape
                    )
            )
            Column {
                Text(
                    text = "Settings".uppercase(),
                    color = colors.onSurfaceVariant,
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = if (name.isBlank()) "My Profile" else name,
                    color = colors.onBackground,
                    fontSize = 18.sp,
                    lineHeight = 28.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // ── Sections ─────────────────────────────────────────────────────────
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            // Profile section
            SettingsSection(title = "Profile") {
                SettingsRow(label = "Name") {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = if (name.isBlank()) "Not set" else name,
                            color = colors.onSurfaceVariant,
                            fontSize = 14.sp
                        )
                        IconButton(
                            onClick = {
                                nameInput = name
                                showNameDialog = true
                            },
                            modifier = Modifier
                                .size(32.dp)
                                .shadow(elevation = 1.dp, CircleShape)
                                .clip(CircleShape),
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = colors.primary,
                                contentColor = colors.onPrimary
                            )
                        ) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit name", modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }

            // Workout section
            SettingsSection(title = "Workout") {
                SettingsRow(label = "Weight unit") {
                    SingleChoiceSegmentedButtonRow {
                        listOf("kg", "lbs").forEachIndexed { index, unit ->
                            SegmentedButton(
                                shape = SegmentedButtonDefaults.itemShape(index = index, count = 2),
                                selected = weightUnit == unit,
                                onClick = { viewModel.setWeightUnit(unit) },
                                icon = {
                                    if (weightUnit == unit) {
                                        Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                                    }
                                },
                                label = {
                                    Text(unit, fontSize = 13.sp)
                                }
                            )
                        }
                    }
                }
            }

            // Appearance section
            SettingsSection(title = "Appearance") {
                SettingsRow(label = "Dark mode") {
                    Switch(
                        checked = isDarkMode,
                        onCheckedChange = { viewModel.setDarkMode(it) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = colors.onPrimary,
                            checkedTrackColor = colors.primary
                        )
                    )
                }
            }

            // Notifications section
            SettingsSection(title = "Notifications") {
                SettingsRow(label = "Water reminder") {
                    Switch(
                        checked = waterReminderEnabled,
                        onCheckedChange = { viewModel.setWaterReminderEnabled(it) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = colors.onPrimary,
                            checkedTrackColor = colors.primary
                        )
                    )
                }
            }
        }
    }
}

// ── Reusable composables ──────────────────────────────────────────────────────

@Composable
private fun SettingsSection(
    title: String,
    content: @Composable () -> Unit
) {
    val colors = LocalAppColors.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colors.surface, shape = RoundedCornerShape(16.dp))
            .border(
                width = 1.dp,
                color = colors.surfaceVariant,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = title.uppercase(),
            color = colors.onSurfaceVariant,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )
        content()
    }
}

@Composable
private fun SettingsRow(
    label: String,
    control: @Composable () -> Unit
) {
    val colors = LocalAppColors.current
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = colors.onSurface,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium
        )
        control()
    }
}
