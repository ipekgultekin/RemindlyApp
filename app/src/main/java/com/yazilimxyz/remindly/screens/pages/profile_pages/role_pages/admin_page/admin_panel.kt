package com.yazilimxyz.remindly.screens.pages.profile_pages.role_pages.admin_page

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import com.yazilimxyz.remindly.AdminViewModel
import com.yazilimxyz.remindly.R
import com.yazilimxyz.remindly.RoleCredentialsRepository
import com.yazilimxyz.remindly.noRoleLottie
import com.yazilimxyz.remindly.saveRoleCredentials
import com.yazilimxyz.remindly.screens.AvatarImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun AdminPanel(navController: NavController, snackbarHostState: SnackbarHostState) {

    var currentEmail by remember { mutableStateOf("") }
    var currentPassword by remember { mutableStateOf("") }

    var selectedAvatarIndex by remember { mutableIntStateOf(0) }
    var showRoleDialog by remember { mutableStateOf(false) }
    var showAddButton by remember { mutableStateOf(false) }
    var showEditButton by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    val adminViewModel: AdminViewModel = viewModel()

    LaunchedEffect(Unit) {
        adminViewModel.loadAdminCredentials()
    }

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
                    avatar = if (RoleCredentialsRepository.adminEmail != "yok") {
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
                    avatar = if (RoleCredentialsRepository.asistanEmail != "yok") {
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
                    avatar = if (RoleCredentialsRepository.ekipLideriEmail != "yok") {
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
                    avatar = if (RoleCredentialsRepository.yonetimKuruluEmail != "yok") {
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
                    avatar = if (RoleCredentialsRepository.calisanEmail != "yok") {
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

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "No such role.",
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp),
                        modifier = Modifier
                            .padding(start = 60.dp)
                            .align(Alignment.CenterVertically)
                    )
                    noRoleLottie()
                }

            } else {

                CredentialsDisplay(
                    currentEmail = currentEmail, currentPassword = currentPassword
                )
            }


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

            DeleteRoleButton(selectedAvatarIndex, snackbarHostState)
        }
    }
    if (showRoleDialog) {

        AlertDialog(onDismissRequest = { showRoleDialog = false }, properties = DialogProperties(
            dismissOnBackPress = false, // Dismiss dialog on back press
            dismissOnClickOutside = false // Dismiss dialog on outside tap
        ), title = {
            Text(
                text = if (showAddButton) "Add a role" else "Edit role", style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                )
            )
        }, text = {
            Column {
                when (selectedAvatarIndex) {
                    0 -> {
                        Image(
                            painter = painterResource(id = R.drawable.avatar2),
                            contentDescription = "Admin",
                            modifier = Modifier
                                .width(120.dp)
                                .height(120.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                    }

                    1 -> {
                        Image(
                            painter = painterResource(id = R.drawable.avatar1),
                            contentDescription = "Asistan",
                            modifier = Modifier
                                .width(120.dp)
                                .height(120.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                    }

                    2 -> {
                        Image(
                            painter = painterResource(id = R.drawable.avatar3),
                            contentDescription = "Ekip Lideri",
                            modifier = Modifier
                                .width(120.dp)
                                .height(120.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                    }

                    3 -> {
                        Image(
                            painter = painterResource(id = R.drawable.avatar4),
                            contentDescription = "Yönetim Kurulu",
                            modifier = Modifier
                                .width(120.dp)
                                .height(120.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                    }

                    4 -> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.avatar5),
                                contentDescription = "Çalışan",
                                modifier = Modifier
                                    .width(120.dp)
                                    .height(120.dp)
                                    .align(Alignment.CenterHorizontally)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                "Çalışan", style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                )
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Email",
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(bottom = 5.dp)
                )
                OutlinedTextField(
                    value = currentEmail,
                    onValueChange = { newValue -> currentEmail = newValue },
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
                    onValueChange = { newValue -> currentPassword = newValue },
                    shape = MaterialTheme.shapes.small
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }, confirmButton = {
            Button(colors = buttonColors(
                contentColor = Color.White,
                containerColor = Color.Red.copy(alpha = 0.5f),
            ), onClick = {

                scope.launch {
                    try {

                    } catch (e: Exception) {
                        Log.d("mesaj", "hata: ${e.message}")
                    }
                }

                when (selectedAvatarIndex) {
                    0 -> {
                        saveRoleCredentials(
                            "admin_credentials", currentEmail, currentPassword

                        )
                    }

                    1 -> {

                        saveRoleCredentials(
                            "asistan_credentials", currentEmail, currentPassword
                        )
                    }

                    2 -> {
                        saveRoleCredentials(
                            "ekip_lideri_credentials", currentEmail, currentPassword
                        )
                    }

                    3 -> {
                        saveRoleCredentials(
                            "yonetim_kurulu_credentials", currentEmail, currentPassword
                        )
                    }

                    4 -> {
                        saveRoleCredentials(
                            "calisan_credentials", currentEmail, currentPassword
                        )
                    }
                }
                showRoleDialog = false

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
fun CredentialsDisplay(
    currentEmail: String, currentPassword: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Email", style = TextStyle(
                fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.Black
            ), modifier = Modifier.padding(bottom = 8.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray)
                    .padding(horizontal = 12.dp), contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = currentEmail, style = TextStyle(
                        fontSize = 15.sp, color = Color.Black
                    )
                )
            }
        }

        // Password Section
        Text(
            text = "Password", style = TextStyle(
                fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.Black
            ), modifier = Modifier.padding(bottom = 8.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp), shape = RoundedCornerShape(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray)
                    .padding(horizontal = 12.dp), contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = currentPassword, style = TextStyle(
                        fontSize = 15.sp, color = Color.Black
                    )
                )
            }
        }
    }
}

@Composable
fun DeleteRoleButton(selectedAvatarIndex: Int, snackbarHostState: SnackbarHostState) {
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
                    deleteRole(selectedAvatarIndex, snackbarHostState, scope)
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

suspend fun deleteRole(
    avatarIndex: Int,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope
) {
    when (avatarIndex) {
        0 -> {
            println("You cannot delete admin..")
            scope.launch {
                snackbarHostState.showSnackbar("You cannot delete admin.")
            }
        }

        1 -> {
            deleteRoleAndHandleResult("asistan_credentials")
            RoleCredentialsRepository.asistanEmail = "yok"
            RoleCredentialsRepository.asistanPassword = "yok"
            scope.launch {
                snackbarHostState.showSnackbar("You deleted asistant.")
            }
        }

        2 -> {
            deleteRoleAndHandleResult("ekip_lideri_credentials")
            RoleCredentialsRepository.ekipLideriEmail = "yok"
            RoleCredentialsRepository.ekipLideriPassword = "yok"
            scope.launch {
                snackbarHostState.showSnackbar("You deleted ekip lideri.")
            }
        }

        3 -> {
            deleteRoleAndHandleResult("yonetim_kurulu_credentials")
            RoleCredentialsRepository.yonetimKuruluEmail = "yok"
            RoleCredentialsRepository.yonetimKuruluPassword = "yok"
            scope.launch {
                snackbarHostState.showSnackbar("You deleted yönetim kurulu.")
            }
        }

        4 -> {
            deleteRoleAndHandleResult("calisan_credentials")
            RoleCredentialsRepository.calisanEmail = "yok"
            RoleCredentialsRepository.calisanPassword = "yok"
            scope.launch {
                snackbarHostState.showSnackbar("You deleted çalışan.")
            }
        }
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
