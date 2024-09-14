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
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import java.util.Locale

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val languageCode = sharedPreferences.getString("language_code", "en") ?: "en"
        setAppLocal(this, languageCode)

        FirebaseApp.initializeApp(this)

        enableEdgeToEdge()
        setContent {
            val themeViewModel: ThemeViewModel = viewModel()
            RemindlyTheme(darkTheme = themeViewModel.isDarkTheme) {
                RemindlyApp(themeViewModel)
            }
        }
    }

    private fun setAppLocal(context: Context, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration(context.resources.configuration).apply {
            setLocale(locale)
        }
        context.createConfigurationContext(config)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }

    private fun restartApp(context: Context) {
        val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
        intent?.let {
            it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(it)
            Runtime.getRuntime().exit(0)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun RemindlyApp(themeViewModel: ThemeViewModel) {
        val navController = rememberNavController()
        val user by rememberUpdatedState(FirebaseAuth.getInstance().currentUser)
        val languageCode by rememberUpdatedState(
            getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                .getString("language_code", "en") ?: "en"
        )

        NavHost(navController, startDestination = if (user == null) "login" else "main") {
            composable("login") { LoginScreen(navController, themeViewModel) }
            composable("main") { MainScreen(themeViewModel) }
            composable("settings") {
                SettingsPage(
                    navController = navController,
                    themeViewModel = themeViewModel,
                    onLanguageChanged = { newLanguageCode ->
                        setAppLocal(this@MainActivity, newLanguageCode)
                        getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                            .edit()
                            .putString("language_code", newLanguageCode)
                            .apply()
                        restartApp(this@MainActivity)
                    }
                )
            }
        }
    }
}
