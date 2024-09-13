package com.yazilimxyz.remindly.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    background = Color(0xFF121212), // Koyu arka plan rengi
    surface = Color(0xFF1E1E1E),   // Koyu yüzey rengi
    onPrimary = Color.White,        // Ana rengi üzerine yazı rengi
    onSecondary = Color.White,      // İkincil rengi üzerine yazı rengi
    onTertiary = Color.White,       // Üçüncül rengi üzerine yazı rengi
    onBackground = Color.White,     // Arka plan rengi üzerine yazı rengi
    onSurface = Color.White         // Yüzey rengi üzerine yazı rengi
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    background = Color(0xFFFFFBFE), // Açık arka plan rengi
    surface = Color(0xFFFFFFFF),     // Açık yüzey rengi
    onPrimary = Color.Black,         // Ana rengi üzerine yazı rengi
    onSecondary = Color.Black,       // İkincil rengi üzerine yazı rengi
    onTertiary = Color.Black,        // Üçüncül rengi üzerine yazı rengi
    onBackground = Color.Black,      // Arka plan rengi üzerine yazı rengi
    onSurface = Color.Black          // Yüzey rengi üzerine yazı rengi
)


@Composable
fun RemindlyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

