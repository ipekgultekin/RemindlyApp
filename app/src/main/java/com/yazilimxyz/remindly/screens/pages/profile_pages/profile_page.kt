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
import com.yazilimxyz.remindly.RoleCredentialsRepository
import com.yazilimxyz.remindly.getRoleCredentials
import com.yazilimxyz.remindly.screens.pages.profile_pages.role_pages.admin_page.adminProfilePage
import com.yazilimxyz.remindly.screens.pages.profile_pages.role_pages.asistanProfilePage
import com.yazilimxyz.remindly.screens.pages.profile_pages.role_pages.calisanProfilePage
import com.yazilimxyz.remindly.screens.pages.profile_pages.role_pages.ekipLideriProfilePage
import com.yazilimxyz.remindly.screens.pages.profile_pages.role_pages.yonetimKuruluProfilePage


@Composable
fun ProfilePage(navController: NavController) {
    val userEmail = FirebaseAuth.getInstance().currentUser?.email

    LaunchedEffect(Unit) {
        RoleCredentialsRepository.loadRoleEmails()
    }

    when (userEmail) {
        RoleCredentialsRepository.adminEmail -> {
            adminProfilePage(navController)
        }
        RoleCredentialsRepository.asistanEmail -> {
            asistanProfilePage()
        }
        RoleCredentialsRepository.ekipLideriEmail -> {
            ekipLideriProfilePage()
        }
        RoleCredentialsRepository.yonetimKuruluEmail -> {
            yonetimKuruluProfilePage()
        }
        RoleCredentialsRepository.calisanEmail -> {
            calisanProfilePage()
        }
    }
}
