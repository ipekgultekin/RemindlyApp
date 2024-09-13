package com.yazilimxyz.remindly.screens.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yazilimxyz.remindly.screens.*
import com.yazilimxyz.remindly.screens.pages.HomePage
import com.yazilimxyz.remindly.screens.pages.profile_pages.ProfilePage
import com.yazilimxyz.remindly.screens.pages.profile_pages.role_pages.admin_page.AdminPanel
import com.yazilimxyz.remindly.screens.pages.profile_pages.role_pages.admin_page.AdminProfilePage
import com.yazilimxyz.remindly.ui.theme.ThemeViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(themeViewModel: ThemeViewModel) {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route ?: "home"

    Scaffold(bottomBar = {
        NavigationBar {
            NavigationBarItem(
                icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                label = { Text("Home") },
                selected = currentRoute == "home",
                onClick = {
                    navController.navigate("home") {
                        popUpTo("home") { saveState = true }
                    }
                })
            NavigationBarItem(
                icon = { Icon(Icons.Filled.DateRange, contentDescription = "Calendar") },
                label = { Text("Calendar") },
                selected = currentRoute == "calendar",
                onClick = {
                    navController.navigate("calendar") {
                        popUpTo("calendar") { saveState = true }
                    }
                })
            NavigationBarItem(
                icon = { Icon(Icons.Filled.AddCircle, contentDescription = "Add") },
                label = { Text("Add") },
                selected = currentRoute == "add",
                onClick = {
                    navController.navigate("add") {
                        popUpTo("add") { saveState = true }
                    }
                })
            NavigationBarItem(
                icon = { Icon(Icons.Filled.Person, contentDescription = "Profile") },
                label = { Text("Profile") },
                selected = currentRoute == "profile",
                onClick = {
                    navController.navigate("profile") {
                        popUpTo("profile") { saveState = true }
                    }
                })
            NavigationBarItem(
                icon = { Icon(Icons.Filled.Settings, contentDescription = "Settings") },
                label = { Text("Settings") },
                selected = currentRoute == "settings",
                onClick = {
                    navController.navigate("settings") {
                        popUpTo("settings") { saveState = true }
                    }
                })
        }
    }) { innerPadding ->
        NavHost(
            navController = navController, startDestination = "home", Modifier.padding(innerPadding)
        ) {
            composable("home") { HomePage() }
            composable("calendar") { CalendarPage() }
            composable("adminProfilePage") { AdminProfilePage(navController) }
            composable("adminPanel") { AdminPanel(navController) }
            composable("add") { AddPage(LocalContext.current) }
            composable("profile") { ProfilePage(navController) }
            composable("settings") { SettingsPage(themeViewModel) } // `themeViewModel` eklenmeli
        }
    }
}
