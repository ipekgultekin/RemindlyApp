package com.yazilimxyz.remindly.screens.pages.profile_pages

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.yazilimxyz.remindly.getRoleCredentials
import com.yazilimxyz.remindly.screens.pages.profile_pages.role_pages.adminProfilePage
import com.yazilimxyz.remindly.screens.pages.profile_pages.role_pages.asistanProfilePage
import com.yazilimxyz.remindly.screens.pages.profile_pages.role_pages.calisanProfilePage
import com.yazilimxyz.remindly.screens.pages.profile_pages.role_pages.ekipLideriProfilePage
import com.yazilimxyz.remindly.screens.pages.profile_pages.role_pages.yonetimKuruluProfilePage


@Composable
fun adminPanel(navController: NavController) {
    // Define the UI for the Admin Panel here
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Admin Panel page",
            style = TextStyle(
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        )
        Button(onClick = { navController.navigate("admin") }) {
            Text(text="go back")
        }
        // Add more UI components for AdminPanel here
    }
}

@Composable
fun ProfilePage(navController: NavController) {
    val userEmail = FirebaseAuth.getInstance().currentUser?.email

    var adminEmail by remember { mutableStateOf("Loading...") }
    var asistanEmail by remember { mutableStateOf("Loading...") }
    var ekipLideriEmail by remember { mutableStateOf("Loading...") }
    var yonetimKuruluEmail by remember { mutableStateOf("Loading...") }
    var calisanEmail by remember { mutableStateOf("Loading...") }

    LaunchedEffect(userEmail) {
        getRoleCredentials("admin_credentials", "adminEmail") { fetchedEmail ->
            adminEmail = fetchedEmail
        }
        getRoleCredentials("asistan_credentials", "asistanEmail") { fetchedEmail ->
            asistanEmail = fetchedEmail
        }
        getRoleCredentials("ekip_lideri_credentials", "ekipLideriEmail") { fetchedEmail ->
            ekipLideriEmail = fetchedEmail
        }
        getRoleCredentials("yonetim_kurulu_credentials", "yonetimKuruluEmail") { fetchedEmail ->
            yonetimKuruluEmail = fetchedEmail
        }
        getRoleCredentials("calisan_credentials", "calisanEmail") { fetchedEmail ->
            calisanEmail = fetchedEmail
        }
    }

    val isAdmin = userEmail == adminEmail
    val isAsistan = userEmail == asistanEmail
    val isEkipLideri = userEmail == ekipLideriEmail
    val isYonetimKurulu = userEmail == yonetimKuruluEmail
    val isCalisan = userEmail == calisanEmail

    if (isAdmin) {
        adminProfilePage(navController)
    } else if (isAsistan) {
        asistanProfilePage()
    } else if (isEkipLideri) {
        ekipLideriProfilePage()
    } else if (isYonetimKurulu) {
        yonetimKuruluProfilePage()
    } else if (isCalisan) {
        calisanProfilePage()
    }
}
