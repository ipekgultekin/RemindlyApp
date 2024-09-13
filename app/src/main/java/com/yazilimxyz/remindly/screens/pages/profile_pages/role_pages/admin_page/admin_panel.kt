package com.yazilimxyz.remindly.screens.pages.profile_pages.role_pages.admin_page

import android.widget.Toast
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import com.yazilimxyz.remindly.R
import com.yazilimxyz.remindly.RoleCredentialsRepository
import com.yazilimxyz.remindly.getRoleCredentials
import com.yazilimxyz.remindly.screens.AvatarImage
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun AdminPanel(navController: NavController) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var selectedAvatarIndex by remember { mutableIntStateOf(0) }
    var showRoleDialog by remember { mutableStateOf(false) }



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
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(38.dp))

            Text(
                text = "Roles",
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp),
                modifier = Modifier
                    .padding(top = 8.dp)
                    .align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(start = 16.dp)
            ) {
                AvatarImage(
                    avatar = if (!RoleCredentialsRepository.adminEmail.isNullOrEmpty()) {
                        R.drawable.avatar2
                    } else {
                        R.drawable.avatar22
                    },
                    "Admin",
                    isSelected = selectedAvatarIndex == 0,
                ) {
                    selectedAvatarIndex = 0
                }
                AvatarImage(
                    avatar = if (!RoleCredentialsRepository.asistanEmail.isNullOrEmpty()) {
                        R.drawable.avatar1
                    } else {
                        R.drawable.avatar11
                    },
                    "Asistan",
                    isSelected = selectedAvatarIndex == 1,
                ) {
                    selectedAvatarIndex = 1
                }
                AvatarImage(
                    avatar = if (!RoleCredentialsRepository.ekipLideriEmail.isNullOrEmpty()) {
                        R.drawable.avatar3
                    } else {
                        R.drawable.avatar33
                    },
                    "Ekip Lideri",
                    isSelected = selectedAvatarIndex == 2,
                ) {
                    selectedAvatarIndex = 2
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .padding(start = 16.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                AvatarImage(
                    avatar = if (!RoleCredentialsRepository.yonetimKuruluEmail.isNullOrEmpty()) {
                        R.drawable.avatar4
                    } else {
                        R.drawable.avatar44
                    },
                    "Yönetim Kurulu",
                    isSelected = selectedAvatarIndex == 3,
                ) {
                    selectedAvatarIndex = 3
                }
                AvatarImage(
                    avatar = if (!RoleCredentialsRepository.calisanEmail.isNullOrEmpty()) {
                        R.drawable.avatar5
                    } else {
                        R.drawable.avatar55
                    },
                    "Çalışan",
                    isSelected = selectedAvatarIndex == 4,
                ) {
                    selectedAvatarIndex = 4
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Get email and password for the selected avatar
            val currentEmail = when (selectedAvatarIndex) {
                0 -> RoleCredentialsRepository.adminEmail
                1 -> RoleCredentialsRepository.asistanEmail
                2 -> RoleCredentialsRepository.ekipLideriEmail
                3 -> RoleCredentialsRepository.yonetimKuruluEmail
                4 -> RoleCredentialsRepository.calisanEmail
                else -> ""
            }

            val currentPassword = when (selectedAvatarIndex) {
                0 -> RoleCredentialsRepository.adminPassword
                1 -> RoleCredentialsRepository.asistanPassword
                2 -> RoleCredentialsRepository.ekipLideriPassword
                3 -> RoleCredentialsRepository.yonetimKuruluPassword
                4 -> RoleCredentialsRepository.calisanPassword
                else -> ""
            }

            if (currentEmail.isNullOrEmpty()) {
                Text(
                    text = "No role assigned for this user",
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp),
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                )
            } else {

                TextField(
                    value = currentEmail,
                    onValueChange = {
                        email = it
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
                    value = currentPassword,
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
            }

            Spacer(modifier = Modifier.height(30.dp))

            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 56.dp),
                thickness = 2.dp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                modifier = Modifier
                    .width(400.dp)
                    .padding(horizontal = 10.dp)
                    .align(Alignment.CenterHorizontally),
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black.copy(alpha = 0.5f)
                ),
                shape = MaterialTheme.shapes.large // Apply custom shape
            ) {
                Text(
                    "Add / Edit Role",
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp),
                    modifier = Modifier.padding(12.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            DeleteRoleButton(selectedAvatarIndex)
        }
    }
}


@Composable
fun DeleteRoleButton(selectedAvatarIndex: Int) {
    val scope = rememberCoroutineScope()

    Button(
        modifier = Modifier
            .width(400.dp)
            .padding(horizontal = 10.dp),
        onClick = {
            scope.launch {
                deleteRole(selectedAvatarIndex)
            }
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Red.copy(alpha = 0.5f)
        ),
        shape = MaterialTheme.shapes.large // Apply custom shape
    ) {
        Text(
            "Delete Role",
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp),
            modifier = Modifier.padding(12.dp)
        )
    }
}

suspend fun deleteRole(avatarIndex: Int) {
    when (avatarIndex) {
        0 -> {
            println("You cannot delete admin.")
        }
        1 -> deleteRoleAndHandleResult("asistan_credentials")
        2 -> deleteRoleAndHandleResult("ekip_lideri_credentials")
        3 -> deleteRoleAndHandleResult("yonetim_kurulu_credentials")
        4 -> deleteRoleAndHandleResult("calisan_credentials")
    }
//    RoleCredentialsRepository.loadRoleEmails()
}

suspend fun deleteRoleAndHandleResult(documentCredentials: String) {
    val db = FirebaseFirestore.getInstance()
    try {
        val documentSnapshot = db.collection("credentials").document(documentCredentials).get().await()
        val email = documentSnapshot.getString("email")
        if (email.isNullOrEmpty()) {
            println("There is no such role.")
        } else {
            db.collection("credentials").document(documentCredentials).delete().await()
            println("Role deleted successfully.")
        }
    } catch (e: Exception) {
        println("Error deleting role: ${e.message}")
    }
}

