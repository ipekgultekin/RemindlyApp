package com.yazilimxyz.remindly.screens.pages.profile_pages

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.yazilimxyz.remindly.RoleCredentialsRepository
import com.yazilimxyz.remindly.screens.pages.profile_pages.role_pages.AsistanProfilePage
import com.yazilimxyz.remindly.screens.pages.profile_pages.role_pages.CalisanProfilePage
import com.yazilimxyz.remindly.screens.pages.profile_pages.role_pages.EkipLideriProfilePage
import com.yazilimxyz.remindly.screens.pages.profile_pages.role_pages.YonetimKuruluProfilePage
import com.yazilimxyz.remindly.screens.pages.profile_pages.role_pages.admin_page.AdminProfilePage

@Composable
fun ProfilePage(navController: NavController) {
    val currentUser = RoleCredentialsRepository.currentUser

    when (currentUser) {
        "admin" -> {
            AdminProfilePage(navController)
        }

        "asistan" -> {
            AsistanProfilePage(navController)
        }

        "ekipLideri" -> {
            EkipLideriProfilePage(navController)
        }

        "yonetimKurulu" -> {
            YonetimKuruluProfilePage(navController)
        }

        "calisan" -> {
            CalisanProfilePage(navController)
        }
    }
}
