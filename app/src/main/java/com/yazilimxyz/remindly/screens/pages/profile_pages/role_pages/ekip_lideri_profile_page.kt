
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
fun EkipLideriProfilePage(navController: NavController) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
//                    Log.d("mesaj", userEmail.toString())
//                    Log.d("mesaj", adminEmail)
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.avatar4),
                contentDescription = null,
                modifier = Modifier
                    .size(128.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(id = R.string.welcome_teamleader),
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize
                )
            )


            Spacer(modifier = Modifier.height(100.dp))


            Button(
                modifier = Modifier.width(300.dp),
                elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 8.dp),
                onClick = {
                    navController.navigate("ekipuyeleriPage")
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF2B1B1)
                ),
                shape = MaterialTheme.shapes.small
            ) {
                Text(
                    text = stringResource(id = R.string.team_members),
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp),
                    modifier = Modifier.padding(12.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))


            Button(
                modifier = Modifier.width(300.dp),
                elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 8.dp),
                onClick = {
                    navController.navigate("asistanPage")
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF2B1B1)
                ),
                shape = MaterialTheme.shapes.small
            ) {
                Text(
                    text = stringResource(id = R.string.assistant),
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp),
                    modifier = Modifier.padding(12.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                modifier = Modifier.width(300.dp),
                elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 8.dp),
                onClick = {
                    navController.navigate("toplantilarPage")
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF2B1B1)
                ),
                shape = MaterialTheme.shapes.small
            ) {
                Text(
                    text = stringResource(id = R.string.meetings),
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp),
                    modifier = Modifier.padding(12.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

        }
    }
}