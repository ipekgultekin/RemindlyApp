package com.yazilimxyz.remindly.screens.pages.profile_pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.yazilimxyz.remindly.RoleCredentialsRepository
import com.yazilimxyz.remindly.screens.pages.profile_pages.role_pages.admin_page.AdminProfilePage
import com.yazilimxyz.remindly.screens.pages.profile_pages.role_pages.AsistanProfilePage
import com.yazilimxyz.remindly.screens.pages.profile_pages.role_pages.CalisanProfilePage
import com.yazilimxyz.remindly.screens.pages.profile_pages.role_pages.EkipLideriProfilePage
import com.yazilimxyz.remindly.screens.pages.profile_pages.role_pages.YonetimKuruluProfilePage

@Composable
fun ProfilePage(navController: NavController) {
    val userEmail = FirebaseAuth.getInstance().currentUser?.email

    LaunchedEffect(Unit) {
        RoleCredentialsRepository.loadRoleEmails()
    }

    when (userEmail) {
        RoleCredentialsRepository.adminEmail -> {
            AdminProfilePage(navController)
        }
        RoleCredentialsRepository.asistanEmail -> {
            AsistanProfilePage()
        }
        RoleCredentialsRepository.ekipLideriEmail -> {
            EkipLideriProfilePage()
        }
        RoleCredentialsRepository.yonetimKuruluEmail -> {
            YonetimKuruluProfilePage()
        }
        RoleCredentialsRepository.calisanEmail -> {
            CalisanProfilePage()
        }
    }
}
