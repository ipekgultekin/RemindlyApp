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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavController
import com.yazilimxyz.remindly.R
import com.yazilimxyz.remindly.RoleCredentialsRepository
import com.yazilimxyz.remindly.ui.theme.ThemeViewModel
import com.yazilimxyz.remindly.ui.theme.setAppLocal
import com.yazilimxyz.remindly.screens.pages.sendNotification
import com.yazilimxyz.remindly.screens.pages.checkNotificationPermission


@Composable
fun SettingsPage(
    navController: NavController,
    themeViewModel: ThemeViewModel,
    onLanguageChanged: (String) -> Unit
) {


    val themeColor = Color(0xFFF2B1B1)
    val expandedBackgroundColor = Color(0xFFB0BEC5)
    val textColor = Color(0xFFFFFFFF)

    val scrollState = rememberScrollState()

    var isThemeExpanded by remember { mutableStateOf(false) }
    var isLanguageExpanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var switchState by remember { mutableStateOf(false) }

    // Store string resources in variables
    val turkish = stringResource(id = R.string.turkish)
    val english = stringResource(id = R.string.english)
    val darkTheme = stringResource(id = R.string.dark)
    val lightTheme = stringResource(id = R.string.light)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(100.dp))

        Image(
            painter = painterResource(id = R.drawable.settings),
            contentDescription = stringResource(id = R.string.settings),
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
            SettingsBar(
                title = stringResource(id = R.string.theme),
                color = themeColor,
                options = listOf(darkTheme, lightTheme),
                isExpanded = isThemeExpanded,
                onClick = { isThemeExpanded = !isThemeExpanded },
                expandedBackgroundColor = expandedBackgroundColor,
                textColor = textColor,
                onOptionSelected = { selectedOption ->
                    themeViewModel.isDarkTheme = selectedOption == darkTheme
                }
            )

            SettingsBar(
                title = stringResource(id = R.string.language),
                color = themeColor,
                options = listOf(turkish, english),
                isExpanded = isLanguageExpanded,
                onClick = { isLanguageExpanded = !isLanguageExpanded },
                expandedBackgroundColor = expandedBackgroundColor,
                textColor = textColor,
                onOptionSelected = { selectedOption ->
                    val languageCode = if (selectedOption == turkish) "tr" else "en"
                    onLanguageChanged(languageCode)
                }
            )

            NotificationSettingsBar(
                title = stringResource(id = R.string.notification),
                color = themeColor, // Tema rengine uygun renk
                textColor = Color.White,
                isChecked = switchState, // Switch durumunu geçir
                onCheckedChange = { isChecked ->
                    // Switch durumunu değiştir
                    switchState = isChecked

                    // Bildirim iznini kontrol et ve izni yoksa talep et
                    if (isChecked) {
                        if (checkNotificationPermission(context)) {
                            // Bildirim izni varsa, bildirim gönder
                            sendNotification(context, "Toplantı ve planlarını kaçırma!", "Gelecek toplantıların seni bekliyor.")
                        } else {
                            // İzin verilmemişse, kullanıcıyı bilgilendirin veya izni talep edin
                            println("Bildirim izni verilmedi.")
                        }
                    }
                },
                onClick = {
                    // Tıklama olayını işlemek için kod
                    println("Notification settings bar clicked")
                }
            )
        }

        Spacer(modifier = Modifier.weight(1f))

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
                text = stringResource(id = R.string.sign_out), style = TextStyle(
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
fun NotificationSettingsBar(
    title: String,
    color: Color,
    textColor: Color,
    isChecked: Boolean, // Switch'in durumunu göstermek için bir parametre
    onCheckedChange: (Boolean) -> Unit, // Switch durumunu değiştiren bir lambda fonksiyonu
    onClick: () -> Unit // Row'un tıklama olayını işlemek için bir lambda fonksiyonu
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = color, shape = MaterialTheme.shapes.medium)
            .padding(vertical = 12.dp, horizontal = 16.dp)
            .clickable(onClick = onClick) // Row'un tıklanabilirliğini sağlar
        ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
        )
        Switch(
            checked = isChecked, // Switch'in durumunu gösterir
            onCheckedChange = onCheckedChange // Switch'in durumunu değiştiren olay işleyici
        )
    }

}