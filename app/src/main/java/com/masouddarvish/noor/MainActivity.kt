package com.masouddarvish.noor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import com.masouddarvish.noor.model.Screen
import com.masouddarvish.noor.ui.screens.*
import com.masouddarvish.noor.ui.theme.NoorPalettes
import com.masouddarvish.noor.ui.theme.NoorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState); setContent { ZikranApp() } }
}

data class BottomItem(val screen: Screen, val label: String, val icon: ImageVector)

@Composable
private fun ZikranApp() {
    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences("noor", 0) }
    var screen by remember { mutableStateOf<Screen>(Screen.Home) }
    val history = remember { mutableStateListOf<Screen>() }
    var themeIndex by remember { mutableIntStateOf(prefs.getInt("theme", 0).coerceIn(NoorPalettes.indices)) }
    val palette = NoorPalettes[themeIndex]
    val bottom = listOf(
        BottomItem(Screen.Home,"خانه",Icons.Filled.Home),
        BottomItem(Screen.AyatKursi,"آیت‌الکرسی",Icons.Filled.AutoStories),
        BottomItem(Screen.Counter,"اذکار",Icons.Filled.Favorite),
        BottomItem(Screen.More,"بیشتر",Icons.Filled.MoreHoriz)
    )
    fun go(target: Screen) { if (target != screen) { history.add(screen); screen = target } }
    fun back() { if (history.isNotEmpty()) screen = history.removeAt(history.lastIndex) else screen = Screen.Home }
    BackHandler(enabled = screen != Screen.Home) { back() }

    NoorTheme(
        palette = palette,
        content = {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Scaffold(
                containerColor = MaterialTheme.colorScheme.background,
                bottomBar = {
                    NavigationBar(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp).shadow(12.dp, RoundedCornerShape(30.dp)),
                        containerColor = MaterialTheme.colorScheme.primary,
                        tonalElevation = 10.dp
                    ) {
                        bottom.forEach { item ->
                            val selected = screen == item.screen
                            NavigationBarItem(
                                selected = selected,
                                onClick = { go(item.screen) },
                                icon = { Icon(item.icon, item.label) },
                                label = { Text(item.label) },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = MaterialTheme.colorScheme.onSecondary,
                                    selectedTextColor = MaterialTheme.colorScheme.secondary,
                                    indicatorColor = MaterialTheme.colorScheme.secondary,
                                    unselectedIconColor = androidx.compose.ui.graphics.Color.White.copy(alpha=.82f),
                                    unselectedTextColor = androidx.compose.ui.graphics.Color.White.copy(alpha=.82f)
                                )
                            )
                        }
                    }
                }
            ) { innerPadding ->
                Surface(Modifier.padding(innerPadding), color = MaterialTheme.colorScheme.background) {
                    when (screen) {
                        Screen.Home -> HomeScreen(::go)
                        Screen.AyatKursi -> AyatKursiScreen()
                        Screen.Counter -> CounterScreen()
                        Screen.More -> MoreScreen(::go)
                        Screen.PrayerTimes -> PrayerTimesScreen()
                        Screen.Qibla -> QiblaScreen()
                        Screen.Favorites -> FavoritesScreen()
                        Screen.Settings -> SettingsScreen(themeIndex) { selected -> themeIndex=selected; prefs.edit().putInt("theme",selected).apply() }
                        Screen.About -> AboutScreen()
                    }
                }
            }
        }
        }
    )
}
