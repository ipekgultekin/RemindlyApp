package com.yazilimxyz.remindly.screens.pages.profile_pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.firebase.auth.FirebaseAuth
import com.yazilimxyz.remindly.getRoleCredentials
import com.yazilimxyz.remindly.screens.pages.profile_pages.role_pages.adminProfilePage
import com.yazilimxyz.remindly.screens.pages.profile_pages.role_pages.asistanProfilePage
import com.yazilimxyz.remindly.screens.pages.profile_pages.role_pages.calisanProfilePage
import com.yazilimxyz.remindly.screens.pages.profile_pages.role_pages.ekipLideriProfilePage
import com.yazilimxyz.remindly.screens.pages.profile_pages.role_pages.yonetimKuruluProfilePage

@Composable
fun ProfilePage() {
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
        adminProfilePage()
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
