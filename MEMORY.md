# LogMyLifeApp Memory

## Project Overview
Android Kotlin app (Jetpack Compose + Room + Navigation) — fitness/life tracker.
Package: `com.example.logmylifeapp`
Source root: `app/src/main/java/com/example/logmylifeapp/`

## Architecture
- MVVM: Model → Repository → ViewModel → Compose Screen
- Manual DI via `Graph.kt` object (existing features)
- Hilt DI added for Settings feature (as example/learning)
- Room 2.5.0, Navigation Compose, Coroutines

## Key Files
- `Graph.kt` — manual DI container, also initializes Room + seed data
- `Navigation.kt` — full NavGraph (nested graphs for workout, add_workout)
- `Screen.kt` — sealed class with all route strings
- `AppNavigationBar.kt` — SegmentedButton bottom nav (4 tabs)
- `MainActivity.kt` — @AndroidEntryPoint, showBottomBar logic
- `LogMyLifeApp.kt` — @HiltAndroidApp, Graph.provide(), notification channel
- `screen/components/FormControls.kt` — reusable form components (TextField, NumberField, DropdownField, DateField, MultiSelectDays)

## Hilt Setup (Settings feature — learning example)
- `@HiltAndroidApp` on LogMyLifeApp
- `@AndroidEntryPoint` on MainActivity
- `di/AppModule.kt` — @Module @InstallIn(SingletonComponent) provides DataStore<Preferences>
- `repository/SettingsRepository.kt` — @Inject constructor, uses DataStore
- `viewmodel/SettingsViewModel.kt` — @HiltViewModel + @Inject constructor
- Screen uses `hiltViewModel()` instead of `viewModel()`
- Keys/defaults extracted to `model/UserSettings.kt` (UserSettingsKeys + UserSettingsDefaults objects)

## Settings Feature
- DataStore (NOT Room) — key-value file storage for simple preferences
- Keys: name (String), weight_unit (String "kg"/"lbs"), water_reminder (Boolean)
- Screen: SettingsHomeScreen (Profile / Workout / Notifications sections)
- Route: "settings_home_screen"

## Nav Bar
4 tabs: Home (analytics icon), Workout (exercise icon), YearInPixels (calendar icon), Settings (Icons.Default.Settings)
showBottomBar in MainActivity checks all 4 routes.

## FormControls / DropdownField
- Redesigned to match Figma screenshot (inline expansion, NOT Material DropdownMenu popup)
- Green border (0xFF13EC5B) wraps header + list in one Column
- Chevron (ArrowDropDown) rotates 0°→180° via animateFloatAsState on expand
- Selected item gets green.copy(alpha=0.15f) background + SemiBold text
- HorizontalDivider separates header from list
- Placeholder auto-generated: "Select ${label.lowercase()}"
- Data model unchanged: DropdownField(label, options: List<String>, selected: String?)

## Colors
- App theme: off_white_200 (background), blue_900 (titles), green_200 (accents), grey_700 (subtitles)
- Green accent hex: 0xFF13EC5B (used in FormControls directly)
- White: used as dropdown background

## User Preferences
- Beginner Android developer — learning through building
- Wants explanations with comments in code
- Can share screenshots/images by providing file path (e.g. C:\path\to\file.png)
- Goal: learn patterns (Hilt, MVVM, DataStore, Compose) while building a real app
