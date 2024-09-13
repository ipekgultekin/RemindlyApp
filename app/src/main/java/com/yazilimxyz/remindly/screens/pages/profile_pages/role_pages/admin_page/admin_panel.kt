package com.yazilimxyz.remindly.screens.pages.profile_pages.role_pages.admin_page

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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import com.yazilimxyz.remindly.R
import com.yazilimxyz.remindly.RoleCredentialsRepository
import com.yazilimxyz.remindly.editDialogueLottie
import com.yazilimxyz.remindly.screens.AvatarImage
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun AdminPanel(navController: NavController) {

    var currentEmail by remember { mutableStateOf("") }
    var currentPassword by remember { mutableStateOf("") }

    var selectedAvatarIndex by remember { mutableIntStateOf(0) }
    var showRoleDialog by remember { mutableStateOf(false) }
    var showAddButton by remember { mutableStateOf(false) }
    var showEditButton by remember { mutableStateOf(false) }

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

            LaunchedEffect(selectedAvatarIndex) {
                currentEmail = when (selectedAvatarIndex) {
                    0 -> RoleCredentialsRepository.adminEmail
                    1 -> RoleCredentialsRepository.asistanEmail
                    2 -> RoleCredentialsRepository.ekipLideriEmail
                    3 -> RoleCredentialsRepository.yonetimKuruluEmail
                    4 -> RoleCredentialsRepository.calisanEmail
                    else -> ""
                }

                currentPassword = when (selectedAvatarIndex) {
                    0 -> RoleCredentialsRepository.adminPassword
                    1 -> RoleCredentialsRepository.asistanPassword
                    2 -> RoleCredentialsRepository.ekipLideriPassword
                    3 -> RoleCredentialsRepository.yonetimKuruluPassword
                    4 -> RoleCredentialsRepository.calisanPassword
                    else -> ""
                }
            }

            LaunchedEffect(currentEmail) {
                showAddButton = currentEmail.isEmpty()
                showEditButton = currentEmail.isNotEmpty()
            }


            if (currentEmail.isEmpty()) {

//                showAddButton = true
//                showEditButton = false

                Text(
                    text = "No such role.",
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp),
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                )

            } else {

//                showEditButton = true
//                showAddButton = false

                TextField(value = currentEmail,
                    onValueChange = {
//                        email = it
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

                TextField(value = currentPassword,
                    onValueChange = {
//                        password = it
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

            if (showAddButton) {
                Button(
                    modifier = Modifier
                        .width(400.dp)
                        .padding(horizontal = 10.dp)
                        .align(Alignment.CenterHorizontally), onClick = {
                        showRoleDialog = true
                    }, colors = buttonColors(
                        containerColor = Color(
                            0xFF579c1f
                        )
                    ), shape = MaterialTheme.shapes.large
                ) {
                    Text(
                        "Add Role",
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp),
                        modifier = Modifier.padding(12.dp)
                    )
                }
            } else if (showEditButton) {
                Button(
                    modifier = Modifier
                        .width(400.dp)
                        .padding(horizontal = 10.dp)
                        .align(Alignment.CenterHorizontally),
                    onClick = {
                        showRoleDialog = true
                    },
                    colors = buttonColors(containerColor = Color.Black.copy(alpha = 0.6f)),
                    shape = MaterialTheme.shapes.large
                ) {
                    Text(
                        "Edit Role",
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp),
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }


            Spacer(modifier = Modifier.height(10.dp))

            DeleteRoleButton(selectedAvatarIndex)
        }
    }
    if (showRoleDialog) {
        AlertDialog(onDismissRequest = { showRoleDialog = false }, properties = DialogProperties(
            dismissOnBackPress = true, // Dismiss dialog on back press
            dismissOnClickOutside = true // Dismiss dialog on outside tap
        ), title = {
            Text(
                text = if (showAddButton) "Add a role" else "Edit role", style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                )
            )
        }, text = {
            Column {
                editDialogueLottie()
                Text(
                    text = "Email",
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(bottom = 5.dp)
                )
                OutlinedTextField(
                    value = currentEmail,
                    onValueChange = {},
                    shape = MaterialTheme.shapes.small
                )
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    text = "Password",
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(bottom = 5.dp)
                )
                OutlinedTextField(
                    value = currentPassword,
                    onValueChange = {},
                    shape = MaterialTheme.shapes.small
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }, confirmButton = {
            Button(colors = buttonColors(
                contentColor = Color.White,
                containerColor = Color.Red.copy(alpha = 0.5f),
            ), onClick = {

            }) {
                Text("Confirm")
            }
        }, dismissButton = {
            Button(colors = buttonColors(
                contentColor = Color.White,
                containerColor = Color.Black.copy(alpha = 0.6f),
            ), onClick = { showRoleDialog = false }) {
                Text("Cancel")
            }
        })
    }
}


@Composable
fun DeleteRoleButton(selectedAvatarIndex: Int) {
    val scope = rememberCoroutineScope()
    var showDialog by remember { mutableStateOf(false) }

    Button(
        modifier = Modifier
            .width(400.dp)
            .padding(horizontal = 10.dp), onClick = {
            showDialog = true
        }, colors = buttonColors(
            containerColor = Color.Red.copy(alpha = 0.5f)
        ), shape = MaterialTheme.shapes.large // Apply custom shape
    ) {
        Text(
            "Delete Role",
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp),
            modifier = Modifier.padding(12.dp)
        )
    }
    if (showDialog) {
        AlertDialog(onDismissRequest = { showDialog = false }, title = {
            Text(
                "Confirm Delete", style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                )
            )
        }, text = {
            Text(
                "Are you sure you want to delete this role?", style = TextStyle(
                    fontSize = 16.sp,
                )
            )
        }, confirmButton = {
            Button(colors = buttonColors(
                contentColor = Color.White,
                containerColor = Color.Red.copy(alpha = 0.5f),
            ), onClick = {
                scope.launch {
                    deleteRole(selectedAvatarIndex)
                    showDialog = false
                }
            }) {
                Text("Confirm")
            }
        }, dismissButton = {
            Button(colors = buttonColors(
                contentColor = Color.White,
                containerColor = Color.Black.copy(alpha = 0.6f),
            ), onClick = { showDialog = false }) {
                Text("Cancel")
            }
        })
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
}

suspend fun deleteRoleAndHandleResult(documentCredentials: String) {
    val db = FirebaseFirestore.getInstance()
    try {
        val documentSnapshot =
            db.collection("credentials").document(documentCredentials).get().await()
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

