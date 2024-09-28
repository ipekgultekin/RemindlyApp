package com.yazilimxyz.remindly.screens.pages.profile_pages.role_pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.yazilimxyz.remindly.R


@Composable
fun AsistanProfilePage(navController: NavController) {

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Surface(
            modifier = Modifier.size(130.dp),
            shape = CircleShape
        ) {
            Image(
                painter = painterResource(id = R.drawable.avatar1),
                contentDescription = null,
                modifier = Modifier.clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(30.dp))


        Text(
            text = stringResource(id = R.string.welcome_assistant),
            style = TextStyle(
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.titleLarge.fontSize
            )
        )

        Spacer(modifier = Modifier.height(100.dp))

        // Yetkilendirilen Kullanıcılar Butonu
        Button(
            modifier = Modifier.width(300.dp),
            elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 8.dp),
            onClick = {
                navController.navigate("yetkilendirilenKullanicilarPage")
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFF2B1B1)
            ),
            shape = MaterialTheme.shapes.small
        ) {
            Text(
                text = stringResource(id = R.string.authorized_users),
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp),
                modifier = Modifier.padding(12.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Toplantılar Butonu
        Button(
            modifier = Modifier.width(300.dp),
            elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 8.dp),
            onClick = {
                navController.navigate("toplantilarPage")
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFB1E1F2)
            ),
            shape = MaterialTheme.shapes.small
        ) {
            Text(
                text = stringResource(id = R.string.meetings),
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp),
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}