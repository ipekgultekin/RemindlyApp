package com.yazilimxyz.remindly.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.yazilimxyz.remindly.R
import com.yazilimxyz.remindly.ui.theme.ThemeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

@Composable
fun LoginScreen(navController: NavController, themeViewModel: ThemeViewModel) {

    val backgroundImage: Painter = painterResource(id = R.drawable.arkaplan)

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val isEmailValid = remember(email) { email.contains("@") && email.endsWith(".com") }
    var passwordVisible by remember { mutableStateOf(false) }
    val buttonColor = Color(0xFFFFB8B8)

    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Image(
            painter = backgroundImage,
            contentDescription = "Arka Plan Resmi",
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "REMINDLY", style = TextStyle(
                    color = Color(0xFFB5AA36), fontSize = 48.sp, fontFamily = FontFamily.Cursive
                ), modifier = Modifier.padding(bottom = 32.dp)
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                shape = MaterialTheme.shapes.large,
                leadingIcon = {
                    Icon(
                        Icons.Outlined.Email, contentDescription = "Email Icon"
                    )
                },
                trailingIcon = {
                    if (!isEmailValid && email.isNotEmpty()) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_warning_amber_24),
                            contentDescription = "Invalid Email Icon",
                            tint = Color.Red
                        )
                    }
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                isError = !isEmailValid && email.isNotEmpty()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                shape = MaterialTheme.shapes.large,
                leadingIcon = {
                    Icon(
                        Icons.Default.Lock, contentDescription = "Password Icon"
                    )
                },
                trailingIcon = {
                    val image = if (passwordVisible) {
                        painterResource(id = R.drawable.baseline_visibility_24)
                    } else {
                        painterResource(id = R.drawable.baseline_visibility_off_24)
                    }
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            painter = image,
                            contentDescription = if (passwordVisible) "Hide password" else "Show password"
                        )
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                singleLine = true, // Şifreyi tek satırda tut
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),

                )

            Spacer(modifier = Modifier.height(50.dp))

            Button(
                onClick = {

                },
                shape = MaterialTheme.shapes.large,
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(horizontal = 50.dp)
                    .height(50.dp)
            ) {

                Text(
                    text = "Log In", style = TextStyle(
                        color = Color.Black, fontSize = 20.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            HorizontalDivider(
                color = Color.Gray,
                thickness = 2.dp,
                modifier = Modifier.padding(horizontal = 50.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            Text(text = "Remindly plans your week!")
        }
    }
}

