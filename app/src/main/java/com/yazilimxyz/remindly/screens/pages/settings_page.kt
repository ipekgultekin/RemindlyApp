package com.yazilimxyz.remindly.screens

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.yazilimxyz.remindly.R
import com.yazilimxyz.remindly.RoleCredentialsRepository
import com.yazilimxyz.remindly.ui.theme.ThemeViewModel
import com.yazilimxyz.remindly.ui.theme.setAppLocal

@Composable
fun SettingsPage(
    navController: NavController,
    themeViewModel: ThemeViewModel,
    onLanguageChanged: (String) -> Unit // Dil değiştirme fonksiyonu
) {
    val themeColor = Color(0xFFF2B1B1)
    val expandedBackgroundColor = Color(0xFFB0BEC5)
    val textColor = Color(0xFFFFFFFF)

    val scrollState = rememberScrollState()

    var isThemeExpanded by remember { mutableStateOf(false) }
    var isLanguageExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(100.dp))

        // Profil görüntüsü
        Image(
            painter = painterResource(id = R.drawable.settings),
            contentDescription = "Ayarlar Görüntüsü",
            modifier = Modifier
                .size(200.dp)
                .padding(bottom = 32.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(48.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Tema ayarları
            SettingsBar(
                title = "Tema",
                color = themeColor,
                options = listOf("Koyu Tema", "Açık Tema"),
                isExpanded = isThemeExpanded,
                onClick = { isThemeExpanded = !isThemeExpanded },
                expandedBackgroundColor = expandedBackgroundColor,
                textColor = textColor,
                onOptionSelected = { selectedOption ->
                    themeViewModel.isDarkTheme = selectedOption == "Koyu Tema"
                }
            )

            // Dil ayarları
            SettingsBar(
                title = "Dil",
                color = themeColor,
                options = listOf("Türkçe", "English"),
                isExpanded = isLanguageExpanded,
                onClick = { isLanguageExpanded = !isLanguageExpanded },
                expandedBackgroundColor = expandedBackgroundColor,
                textColor = textColor,
                onOptionSelected = { selectedOption ->
                    // Dil seçildiğinde, onLanguageChanged fonksiyonu çağrılır
                    val languageCode = if (selectedOption == "Türkçe") "tr" else "en"
                    onLanguageChanged(languageCode) // Dil kodunu gönderiyoruz
                }
            )

            // Bildirim ayarları (eklemek isterseniz)
            NotificationSettingsBar(
                title = "Bildirim Ayarları", color = themeColor, textColor = textColor
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Çıkış butonu
        Button(
            onClick = {
                RoleCredentialsRepository.setUser("") // Kullanıcı oturumu kapatılır
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black.copy(alpha = 0.6f)),
            shape = MaterialTheme.shapes.medium,
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                text = "Oturumu Kapat", style = TextStyle(
                    color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp
                ), modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun SettingsBar(
    title: String,
    color: Color,
    options: List<String> = emptyList(),
    isExpanded: Boolean = false,
    onClick: () -> Unit = {},
    expandedBackgroundColor: Color,
    textColor: Color,
    onOptionSelected: (String) -> Unit = {}
) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = color, shape = MaterialTheme.shapes.medium)
                .padding(vertical = 12.dp, horizontal = 16.dp)
                .clickable { onClick() },
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = title, style = TextStyle(
                    fontSize = 18.sp, fontWeight = FontWeight.Bold, color = textColor
                )
            )
        }

        if (isExpanded && options.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(expandedBackgroundColor)
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                options.forEach { option ->
                    Text(text = option, style = TextStyle(
                        fontSize = 16.sp, color = textColor
                    ), modifier = Modifier.clickable {
                        onOptionSelected(option)
                    })
                }
            }
        }
    }
}

fun restartApp(context: Context) {
    val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
    intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    context.startActivity(intent)
}

@Composable
fun NotificationSettingsBar(title: String, color: Color, textColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = color, shape = MaterialTheme.shapes.medium)
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title, style = TextStyle(
                fontSize = 18.sp, fontWeight = FontWeight.Bold, color = textColor
            )
        )
        Switch(checked = false, onCheckedChange = { /* Handle switch state change */ })
    }
}
