package com.yazilimxyz.remindly.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.Star
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yazilimxyz.remindly.R
import com.yazilimxyz.remindly.utilities.CustomButton
import com.composables.icons.lucide.HeartPulse
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Star

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
                color = Color(0xDD191919), fontWeight = FontWeight.Bold, fontSize = 20.sp
            )
        )
    }
    spacer2()

    TextField(
        value = "", onValueChange = onValueChange, label = {
            Text(
                fieldTitle, style = TextStyle(
                    color = Color(0xDD191919).copy(alpha = 0.4f), fontSize = 18.sp
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

@Composable
fun AddMeetingSheet() {

    var selectedAvatarIndex by remember { mutableStateOf(0) }

    // List of avatars (replace these with actual resource IDs or URLs)
    val avatars = listOf(
        R.drawable.avatar1,
        R.drawable.avatar2,
        R.drawable.avatar3,
        R.drawable.avatar4,
        R.drawable.avatar5
    )

    val avatarLabels = listOf("Admin", "Asistan", "Ekip Lideri", "Yönetim Kurulu", "Çalışan")

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(26.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        item {
            Text(
                text = "Create\nNew Meeting",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 40.sp
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
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
            // Date & Time selection logic
            Row {
                Icon(imageVector = Icons.Default.Send, contentDescription = "meeting icon")
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Date & Time",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = Color(0xDD191919), fontWeight = FontWeight.Bold, fontSize = 20.sp
                    )
                )
            }
            Spacer(modifier = Modifier.height(5.dp))

            CustomButton(title = "Select Date & Time", color = Color(0xDD191919)) {
                // Handle time selection logic here
            }

            Spacer(modifier = Modifier.height(30.dp))
        }

        item {
            // Priority section
            Row {
                Image(Lucide.Star, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Priority",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = Color(0xDD191919), fontWeight = FontWeight.Bold, fontSize = 20.sp
                    )
                )
            }
            Spacer(modifier = Modifier.height(5.dp))

            StarRatingSample()

            Spacer(modifier = Modifier.height(30.dp))
        }

        item {
            // Assigned For section with selectable avatars
            Row {
                Icon(imageVector = Icons.Default.Face, contentDescription = "meeting icon")
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Assigned For",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = Color(0xDD191919), fontWeight = FontWeight.Bold, fontSize = 20.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(5.dp))

            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState())
            ) {
                avatars.forEachIndexed { index, avatar ->
                    AvatarImage(
                        avatar = avatar,
                        content = avatarLabels[index], // Assign the respective label
                                                isSelected = selectedAvatarIndex == index,
                        onClick = {
                            selectedAvatarIndex = index // Update the selected avatar
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
        }

        // Other items such as the divider and buttons
        item {
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 40.dp),
                color = Color(0xDD191919).copy(alpha = 0.5f),
                thickness = 2.dp
            )
            Spacer(modifier = Modifier.height(30.dp))
        }

        item {
            CustomButton(title = "Create Meeting", color = Color(0xDD191919)) {
                // Handle meeting creation
            }
            Spacer(modifier = Modifier.height(10.dp))
            CustomButton(title = "Clear Fields", color = Color(0xDD191919)) {
                // Handle clearing fields
            }
        }
    }
}
@Composable
fun AvatarImage(
    avatar: Int,
    content: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        // Box containing the image with border
        Box(
            modifier = Modifier
                .size(90.dp)
                .clip(CircleShape)
                .border(
                    width = 4.dp,
                    color = if (isSelected) Color.Green else Color.Transparent, // Green border if selected
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = avatar),
                contentDescription = content,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.height(4.dp)) // Space between image and text
        Text(
            text = content,
            style = MaterialTheme.typography.labelLarge,
            color = Color(0xFF191919),
            fontWeight = FontWeight.Bold
        )
    }
}




@Composable
fun StarRatingBar(
    maxStars: Int = 5, rating: Float, onRatingChanged: (Float) -> Unit
) {
    val density = LocalDensity.current.density
    val starSize = (40f * density).dp
    val starSpacing = (0.5f * density).dp

    Row(
        modifier = Modifier.selectableGroup(), verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 1..maxStars) {
            val isSelected = i <= rating
            val icon = if (isSelected) Icons.Rounded.Star else Icons.Rounded.Star
            val iconTintColor = if (isSelected) Color(0xFFF2B1B1) else Color(0xF191919)
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTintColor,
                modifier = Modifier
                    .selectable(selected = isSelected, onClick = {
                        onRatingChanged(i.toFloat())
                    })
                    .width(starSize)
                    .height(starSize)
            )

            if (i < maxStars) {
                Spacer(modifier = Modifier.width(starSpacing))
            }
        }
    }
}

@Composable
fun StarRatingSample() {
    var rating by remember { mutableStateOf(1f) } //default rating will be 1

    StarRatingBar(maxStars = 5, rating = rating, onRatingChanged = {
        rating = it
    })
}