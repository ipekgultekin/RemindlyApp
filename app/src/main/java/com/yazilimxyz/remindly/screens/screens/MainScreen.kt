package com.yazilimxyz.remindly.screens.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.yazilimxyz.remindly.screens.AddPage
import com.yazilimxyz.remindly.screens.CalendarPage
import com.yazilimxyz.remindly.screens.SettingsPage
import com.yazilimxyz.remindly.screens.pages.HomePage
import com.yazilimxyz.remindly.screens.pages.ProfilePage

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route ?: "home"

    Scaffold(bottomBar = {
        NavigationBar {
            NavigationBarItem(icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                label = { Text("Home") },
                selected = currentRoute == "home",
                onClick = {
                    navController.navigate("home") {
                        // Pop up to the start destination to avoid multiple back stack entries
                        popUpTo("home") { saveState = true }
                    }
                })
            NavigationBarItem(icon = {
                Icon(
                    Icons.Filled.DateRange, contentDescription = "Calendar"
                )
            }, label = { Text("Calendar") }, selected = currentRoute == "calendar", onClick = {
                navController.navigate("calendar") {
                    popUpTo("calendar") { saveState = true }
                }
            })
            NavigationBarItem(icon = {
                Icon(
                    Icons.Filled.AddCircle, contentDescription = "Add"
                )
            }, label = { Text("Add") }, selected = currentRoute == "add", onClick = {
                navController.navigate("add") {
                    // Pop up to the start destination to avoid multiple back stack entries
                    popUpTo("add") { saveState = true }
                }
            })

            NavigationBarItem(icon = {
                Icon(
                    Icons.Filled.Person, contentDescription = "Profile"
                )
            }, label = { Text("Profile") }, selected = currentRoute == "profile", onClick = {
                navController.navigate("profile") {
                    popUpTo("profile") { saveState = true }
                }
            })
            NavigationBarItem(icon = {
                Icon(
                    Icons.Filled.Search, contentDescription = "Settings"
                )
            }, label = { Text("Settings") }, selected = currentRoute == "settings", onClick = {
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
            composable("add") {
                AddPage(
                    LocalContext.current
                )
            }
            composable("profile") { ProfilePage() }
            composable("settings") {
                SettingsPage()
            }
        }
    }
}
