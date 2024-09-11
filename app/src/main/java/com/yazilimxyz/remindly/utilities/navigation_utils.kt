package com.yazilimxyz.remindly.utilities

import android.os.Build
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yazilimxyz.remindly.R
import com.yazilimxyz.remindly.screens.CalendarScreen
import com.yazilimxyz.remindly.screens.HomeScreen
import com.yazilimxyz.remindly.screens.ProfileScreen
import com.yazilimxyz.remindly.screens.SettingsScreen

@Composable
fun NavigationHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Home.route,
        modifier = modifier
    ) {
        composable(BottomNavItem.Home.route) {
            HomeScreen()
        }
        composable(BottomNavItem.Calendar.route) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CalendarScreen()
            }
        }
        composable(BottomNavItem.Profile.route) {
            ProfileScreen("Asistan", R.drawable.avatar1, emptyList(), emptyList())
        }
        composable(BottomNavItem.Settings.route) {
            SettingsScreen()
        }
    }
}

sealed class BottomNavItem(val route: String, val label: String, val icon: ImageVector) {
    object Home : BottomNavItem("home", "Home", Icons.Default.Home)
    object Calendar : BottomNavItem("calendar", "Calendar", Icons.Default.Call)
    object AddMeeting : BottomNavItem("add_meeting", "Add", Icons.Default.AddCircle)
    object Profile : BottomNavItem("profile", "Profile", Icons.Default.Person)
    object Settings : BottomNavItem("settings", "Settings", Icons.Default.Settings)
}
