package com.yazilimxyz.remindly.screens.pages

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.yazilimxyz.remindly.R
import com.yazilimxyz.remindly.getRoleCredentials

@Composable
fun ProfilePage() {
    val user = FirebaseAuth.getInstance().currentUser?.email
    var adminEmail by remember { mutableStateOf("Loading...") } // Use state-backed variable


    LaunchedEffect(user) {
        getRoleCredentials { fetchedAdminEmail ->
            adminEmail = fetchedAdminEmail // Update the state with the fetched admin email
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    Log.d("mesaj", user.toString())
                    Log.d("mesaj", adminEmail)
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.avatar3),
                contentDescription = null,
                modifier = Modifier
                    .size(128.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = adminEmail, // Displays the fetched email or "Loading..." initially
                style = TextStyle(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize
                )
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
