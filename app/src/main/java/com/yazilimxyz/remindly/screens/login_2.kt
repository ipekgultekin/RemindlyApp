package com.yazilimxyz.remindly.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.yazilimxyz.remindly.R

@Composable
fun LoginPage(navController: NavController) {

    val backgroundImage: Painter = painterResource(id = R.drawable.arkaplan)

    var email by remember { mutableStateOf("") }
    val isEmailValid = remember(email) { email.contains("@") && email.endsWith(".com") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val buttonColor = Color(0xFFFFB8B8)




    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
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
                text = "REMINDLY",
                style = TextStyle(
                    color = Color(0xFFB5AA36),
                    fontSize = 48.sp,
                    fontFamily = FontFamily.Cursive
                ),
                modifier = Modifier.padding(bottom = 32.dp)
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                leadingIcon = {
                    Icon(
                        Icons.Outlined.Email,
                        contentDescription = "Email Icon"
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
                modifier = Modifier.fillMaxWidth(),
                isError = !isEmailValid && email.isNotEmpty()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                leadingIcon = {
                    Icon(
                        Icons.Default.Lock,
                        contentDescription = "Password Icon"
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

                )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {   },
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp)
            ) {

                Text(text = "GİRİŞ YAP",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 20.sp
                    ) )
            }
        }
    }
}

