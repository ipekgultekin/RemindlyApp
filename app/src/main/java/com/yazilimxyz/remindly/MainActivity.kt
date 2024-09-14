package com.yazilimxyz.remindly

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.yazilimxyz.remindly.RoleCredentialsRepository.currentUser
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

        // Define a state to handle loading
        var isLoading by remember { mutableStateOf(true) }
        var currentUser by remember { mutableStateOf<String?>(null) }

        // Fetch currentUser asynchronously
        LaunchedEffect(Unit) {
            currentUser = RoleCredentialsRepository.currentUser
            isLoading = false
        }

        if (isLoading) {
            LoadingScreen()
        } else {
            val startDestination = if (currentUser.isNullOrEmpty() || currentUser == "empty") {
                "login"
            } else {
                "main"
            }

            NavHost(
                navController = navController,
                startDestination = startDestination
            ) {
                composable("login") { LoginScreen(navController, themeViewModel) }
                composable("main") { MainScreen(themeViewModel) }
            }
        }
    }


    @Composable
    fun LoadingScreen() {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

}