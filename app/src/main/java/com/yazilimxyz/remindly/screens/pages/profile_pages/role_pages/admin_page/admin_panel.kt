package com.yazilimxyz.remindly.screens.pages.profile_pages.role_pages.admin_page

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.yazilimxyz.remindly.R
import com.yazilimxyz.remindly.RoleCredentialsRepository
import com.yazilimxyz.remindly.screens.AvatarImage

@Composable
fun AdminPanel(navController: NavController) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var selectedAvatarIndex by remember { mutableIntStateOf(0) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopStart)
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }

        Text(
            text = "Admin Panel",
            modifier = Modifier
                .padding(top = 25.dp)
                .align(Alignment.TopCenter),
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp)

        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .verticalScroll(rememberScrollState()) // Enable vertical scrolling
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = "Roles",
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp),
                modifier = Modifier
                    .padding(top = 8.dp)
                    .align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(26.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(start = 16.dp)
            ) {
                AvatarImage(
                    R.drawable.avatar2,
                    "Admin",
                    true,
                ) {
                    selectedAvatarIndex = 0
                    Log.d("mesaj", "admin seçildi")
                }
                AvatarImage(
                    R.drawable.avatar1,
                    "Asistan",
                    false,
                ) {
                    selectedAvatarIndex = 1
                    Log.d("mesaj", "asistan seçildi")
                }
                AvatarImage(
                    R.drawable.avatar3,
                    "Ekip Lideri",
                    false,
                ) {
                    selectedAvatarIndex = 2
                    Log.d("mesaj", "ekip lideriseçildi")
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .padding(start = 16.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                AvatarImage(
                    R.drawable.avatar4,
                    "Yönetim Kurulu",
                    false,
                ) {
                    selectedAvatarIndex = 3
                    Log.d("mesaj", "yönetim kurulu seçildi")
                }
                AvatarImage(
                    R.drawable.avatar5,
                    "Çalışan",
                    false,
                ) {
                    selectedAvatarIndex = 4
                    Log.d("mesaj", "çalışan seçildi")
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            TextField(
                value = when (selectedAvatarIndex) {
                    0 -> RoleCredentialsRepository.adminEmail
                    1 -> RoleCredentialsRepository.asistanEmail
                    2 -> RoleCredentialsRepository.ekipLideriEmail
                    3 -> RoleCredentialsRepository.yonetimKuruluEmail
                    4 -> RoleCredentialsRepository.calisanEmail
                    else -> ""
                },
                onValueChange = {
                    email = it // Update the state
                },
                label = {
                    Text(
                        "Email", style = TextStyle(
                            color = Color(0xDD191919).copy(alpha = 0.4f), fontSize = 15.sp
                        )
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.LightGray.copy(alpha = 0.3f), // Light gray background
                    focusedIndicatorColor = Color.Transparent, // Removes the underline when focused
                    unfocusedIndicatorColor = Color.Transparent, // Removes the underline when not focused
                    cursorColor = MaterialTheme.colorScheme.primary // Customize cursor color
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp) // Adjust height for better appearance
                    .padding(horizontal = 8.dp),
                shape = MaterialTheme.shapes.medium // Add rounded corners
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = when (selectedAvatarIndex) {
                    0 -> RoleCredentialsRepository.adminPassword
                    1 -> RoleCredentialsRepository.asistanPassword
                    2 -> RoleCredentialsRepository.ekipLideriPassword
                    3 -> RoleCredentialsRepository.yonetimKuruluPassword
                    4 -> RoleCredentialsRepository.calisanPassword
                    else -> ""
                },
                onValueChange = {
                    password = it
                },
                label = {
                    Text(
                        "Password", style = TextStyle(
                            color = Color(0xDD191919).copy(alpha = 0.4f), fontSize = 15.sp
                        )
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.LightGray.copy(alpha = 0.3f), // Light gray background
                    focusedIndicatorColor = Color.Transparent, // Removes the underline when focused
                    unfocusedIndicatorColor = Color.Transparent, // Removes the underline when not focused
                    cursorColor = MaterialTheme.colorScheme.primary // Customize cursor color
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp) // Adjust height for better appearance
                    .padding(horizontal = 8.dp),
                shape = MaterialTheme.shapes.medium // Add rounded corners
            )

            Spacer(modifier = Modifier.height(70.dp))

            Button(
                modifier = Modifier
                    .width(400.dp)
                    .padding(horizontal = 10.dp)
                    .align(Alignment.CenterHorizontally), onClick = {
                    navController.navigate("adminPanel")
                }, colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red.copy(alpha = 0.5f)
                ), shape = MaterialTheme.shapes.large // Apply custom shape
            ) {
                Text(
                    "Delete Role",
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp),
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
    }
}
