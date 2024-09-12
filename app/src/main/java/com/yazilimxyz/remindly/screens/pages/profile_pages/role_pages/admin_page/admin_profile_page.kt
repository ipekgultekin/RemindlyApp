package com.yazilimxyz.remindly.screens.pages.profile_pages.role_pages.admin_page

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.yazilimxyz.remindly.R

@Composable
fun adminProfilePage(navController: NavController) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Surface(
            modifier = Modifier.size(130.dp),
            shape = CircleShape,              // Apply CircleShape to the Surface
            elevation = 8.dp                  // Set elevation for shadow effect
        ) {
            Image(
                painter = painterResource(id = R.drawable.avatar2),
                contentDescription = null,
                modifier = Modifier.clip(CircleShape), // Apply clipping inside the surface
                contentScale = ContentScale.Crop
            )
        }


        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Welcome, Admin!", // Displays the fetched email or "Loading..." initially
            style = TextStyle(
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.titleLarge.fontSize
            )
        )

        Spacer(modifier = Modifier.height(100.dp))

        Button(
            modifier = Modifier.width(300.dp),
            elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 8.dp),
            onClick = {
                navController.navigate("adminPanel")
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFF2B1B1)
            ),
            shape = MaterialTheme.shapes.small // Apply custom shape
        ) {
            Text(
                "Admin Panel",
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp),
                modifier = Modifier.padding(12.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))


    }
}
