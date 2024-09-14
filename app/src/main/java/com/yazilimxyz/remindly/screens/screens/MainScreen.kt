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
import androidx.navigation.compose.currentBackStackEntryAsState
import com.yazilimxyz.remindly.ui.theme.setAppLocal
import androidx.compose.ui.res.stringResource
import com.yazilimxyz.remindly.R


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(themeViewModel: ThemeViewModel) {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route ?: "home"
    val context = LocalContext.current

    Scaffold(bottomBar = {
        NavigationBar {
            NavigationBarItem(
                icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                label = { Text(stringResource(id = R.string.home)) },
                selected = currentRoute == "home",
                onClick = { navController.navigate("home") }
            )
            NavigationBarItem(
                icon = { Icon(Icons.Filled.DateRange, contentDescription = "Calendar") },
                label = { Text(stringResource(id = R.string.calendar)) },
                selected = currentRoute == "calendar",
                onClick = { navController.navigate("calendar") }
            )
            NavigationBarItem(
                icon = { Icon(Icons.Filled.AddCircle, contentDescription = "Add") },
                label = { Text(stringResource(id = R.string.add_meeting)) },
                selected = currentRoute == "add",
                onClick = {
                    navController.navigate("add") {
                        popUpTo("add") { saveState = true }
                    }
                }
            )
            NavigationBarItem(
                icon = { Icon(Icons.Filled.Person, contentDescription = "Profile") },
                label = { Text(stringResource(id = R.string.profile)) },
                selected = currentRoute == "profile",
                onClick = { navController.navigate("profile") }
            )
            NavigationBarItem(
                icon = { Icon(Icons.Filled.Settings, contentDescription = "Settings") },
                label = { Text(stringResource(id = R.string.settings)) },
                selected = currentRoute == "settings",
                onClick = { navController.navigate("settings") }
            )
        }
    }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            Modifier.padding(innerPadding)
        ) {
            composable("home") { HomePage() }
            composable("calendar") { CalendarPage() }
            composable("adminProfilePage") { AdminProfilePage(navController) }
            composable("adminPanel") { AdminPanel(navController) }
            composable("add") { AddPage(LocalContext.current) }
            composable("profile") { ProfilePage(navController) }
            composable("settings") {
                SettingsPage(
                    navController = navController,
                    themeViewModel = themeViewModel,
                    onLanguageChanged = { selectedLanguage ->
                        setAppLocal(context, selectedLanguage)
                        restartApp(context)
                    }
                )
            }
        }
    }
}
