package com.yazilimxyz.remindly

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.yazilimxyz.remindly.screens.LoginScreen
import com.yazilimxyz.remindly.screens.screens.MainScreen
import com.yazilimxyz.remindly.ui.theme.RemindlyTheme
import com.yazilimxyz.remindly.ui.theme.ThemeViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yazilimxyz.remindly.screens.SettingsPage
import com.yazilimxyz.remindly.screens.pages.HomePage
import com.yazilimxyz.remindly.screens.pages.profile_pages.ProfilePage

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)

        enableEdgeToEdge()
        setContent {
            val themeViewModel: ThemeViewModel = viewModel()
            RemindlyTheme(darkTheme = themeViewModel.isDarkTheme) {
                RemindlyApp(themeViewModel)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun RemindlyApp(themeViewModel: ThemeViewModel) {
        val navController = rememberNavController()
        val user by rememberUpdatedState(FirebaseAuth.getInstance().currentUser)

        NavHost(navController, startDestination = if (user == null) "login" else "main") {
            composable("login") { LoginScreen(navController, themeViewModel) }
            composable("main") { MainScreen(themeViewModel) }
            composable("SettingsPage") { SettingsPage(navController, themeViewModel) }
        }
    }
}