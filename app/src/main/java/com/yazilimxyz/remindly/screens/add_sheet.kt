package com.yazilimxyz.remindly.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yazilimxyz.remindly.R
import com.yazilimxyz.remindly.utilities.CustomButton


@Composable
fun spacer1() {
    Spacer(modifier = Modifier.height(30.dp))
}

@Composable
fun spacer2() {
    Spacer(modifier = Modifier.height(5.dp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun addField(
    icon: ImageVector,
    title: String,
    fieldTitle: String,
    description: String?,
    onValueChange: (String) -> Unit
) {
    Row {
        Icon(imageVector = icon, contentDescription = description)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title, style = MaterialTheme.typography.labelMedium.copy(
                color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 20.sp
            )
        )
    }
    spacer2()

    TextField(
        value = "", onValueChange = onValueChange, label = {
            Text(
                fieldTitle, style = TextStyle(
                    color = Color.Black.copy(alpha = 0.4f), fontSize = 18.sp
                )
            )
        }, colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.LightGray.copy(alpha = 0.3f), // Light gray background
            focusedIndicatorColor = Color.Transparent, // Removes the underline when focused
            unfocusedIndicatorColor = Color.Transparent, // Removes the underline when not focused
            cursorColor = MaterialTheme.colorScheme.primary // Customize cursor color
        ), modifier = Modifier
            .fillMaxWidth()
            .height(60.dp) // Adjust height for better appearance
            .padding(horizontal = 8.dp), shape = MaterialTheme.shapes.medium // Add rounded corners
    )
    spacer1()
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMeetingSheet() {

    var selectedAvatarIndex by remember { mutableStateOf(0) }

    // List of avatars (replace these with actual resource IDs or URLs)
    val avatars = listOf(
        R.drawable.avatar1,
        R.drawable.avatar2,
        R.drawable.avatar3,
        R.drawable.avatar5,
        R.drawable.avatar6
    )

    // Use LazyColumn for scrollable content
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(26.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        item {
            Text(
                text = "Create\nNew Meeting", style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.SemiBold, // Set text bold
                    fontSize = 40.sp // Set custom font size
                )
            )
            spacer1()
        }

        item {

            addField(
                icon = Icons.Default.Person,
                title = "Meeting Title",
                fieldTitle = "Enter a title for your meeting",
                description = "meeting"
            ) {
                // Handle text field value change here
            }

        }

        item {
            addField(
                icon = Icons.Default.Info,
                title = "Description",
                fieldTitle = "Enter a description for your meeting",
                description = "description"
            ) {
                // Handle text field value change here
            }
        }

        item {
            Row {
                Icon(imageVector = Icons.Default.Send, contentDescription = "meeting icon")
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Date & Time", style = MaterialTheme.typography.labelMedium.copy(
                        color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 20.sp
                    )
                )
            }
            Spacer(modifier = Modifier.height(5.dp))

            CustomButton(title = "Select Date & Time", color = Color.Black) {
                // Handle time selection logic here
            }

            Spacer(modifier = Modifier.height(30.dp))
        }

        item {
            Row {
                Icon(imageVector = Icons.Default.Star, contentDescription = "meeting icon")
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Priority", style = MaterialTheme.typography.labelMedium.copy(
                        color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 20.sp
                    )
                )
            }
            Spacer(modifier = Modifier.height(5.dp))



            Spacer(modifier = Modifier.height(30.dp))
        }

        item {
            Row {
                Icon(imageVector = Icons.Default.Face, contentDescription = "meeting icon")
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Assigned For", style = MaterialTheme.typography.labelMedium.copy(
                        color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 20.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(5.dp))

            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState())
            ) {
                AvatarImage(avatar = R.drawable.avatar1, content = "Admin") {}
                AvatarImage(avatar = R.drawable.avatar2, content = "Asistan") {}
                AvatarImage(avatar = R.drawable.avatar3, content = "Yönetim Kurulu") {}
                AvatarImage(avatar = R.drawable.avatar4, content = "Ekip Lideri") {}
                AvatarImage(avatar = R.drawable.avatar5, content = "Çalışan") {}
            }
            Spacer(modifier = Modifier.height(30.dp))
        }

        item {
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 40.dp),
                color = Color.Black.copy(alpha = 0.5f),
                thickness = 2.dp
            )
            Spacer(modifier = Modifier.height(30.dp))
        }

        item {
            CustomButton(title = "Create Meeting", color = Color.Black) {
                // Handle time selection logic here
            }

            Spacer(modifier = Modifier.height(10.dp))

            CustomButton(title = "Clear Fields", color = Color.Black) {
                // Handle time selection logic here
            }
        }
    }
}


@Composable
fun AvatarImage(@DrawableRes avatar: Int, content: String?, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = avatar),
            contentDescription = content,
            modifier = Modifier
                .width(120.dp)
                .height(120.dp)
                .padding(10.dp)
                .clickable(onClick = onClick),
            contentScale = ContentScale.Crop
        )
        Text(
            text = content ?: "",
            style = MaterialTheme.typography.labelLarge,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
    }
}

