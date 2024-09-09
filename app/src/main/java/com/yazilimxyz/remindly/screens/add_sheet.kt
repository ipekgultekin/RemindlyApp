package com.yazilimxyz.remindly.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yazilimxyz.remindly.MeetingModel
import com.yazilimxyz.remindly.R
import com.yazilimxyz.remindly.saveMeetingToFirestore
import com.yazilimxyz.remindly.utilities.CustomButton
import java.util.Calendar

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
    icon: Int,
    title: String,
    fieldTitle: String,
    description: String?,
    textState: MutableState<String>, // Added textState parameter to control TextField
    onValueChange: (String) -> Unit
) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(icon),
                contentDescription = description,
                modifier = Modifier
                    .fillMaxWidth(0.1f) // Image takes 10% of the parent's width
                    .aspectRatio(1f) // Ensures the image remains square
            )
            Spacer(modifier = Modifier.width(15.dp))
            Text(
                text = title, style = MaterialTheme.typography.labelMedium.copy(
                    color = Color(0xDD191919), fontWeight = FontWeight.Bold, fontSize = 20.sp
                )
            )
        }

        spacer2()
        spacer2()
        spacer2()

        TextField(value = textState.value, // Controlled by textState
            onValueChange = {
                textState.value = it // Update the state
                onValueChange(it) // Call the onValueChange lambda
            },
            label = {
                Text(
                    fieldTitle, style = TextStyle(
                        color = Color(0xDD191919).copy(alpha = 0.4f), fontSize = 15.sp
                    )
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.LightGray.copy(alpha = 0.3f), // Light gray background
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
        spacer1()
    }
}

@Composable
fun AddMeetingSheet(context: Context) {

    var selectedAvatarIndex by remember { mutableIntStateOf(0) }
    var selectedDateTime by remember { mutableStateOf("") }
    var dateTimeText by remember { mutableStateOf("Select Date & Time") }
    var selectedPriority = remember { mutableStateOf(3f) } // Default rating value

    var meetingTitleState = remember { mutableStateOf("") }
    var meetingDescriptionState = remember { mutableStateOf("") }

    val avatars = listOf(
        R.drawable.avatar1,
        R.drawable.avatar2,
        R.drawable.avatar3,
        R.drawable.avatar4,
        R.drawable.avatar5
    )

    val avatarLabels = listOf("Admin", "Asistan", "Ekip Lideri", "Yönetim Kurulu", "Çalışan")

    val calendar = Calendar.getInstance()

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(26.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        item {
            Text(
                text = "Create\nNew Meeting", style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.SemiBold, fontSize = 40.sp
                )
            )
            spacer1()
        }

        item {
            addField(
                icon = R.drawable.title,
                title = "Meeting Title",
                fieldTitle = "Enter a title for your meeting",
                description = "meeting",
                textState = meetingTitleState
            ) { newValue ->
                println("it is: $newValue")

            }
        }

        item {
            addField(
                icon = R.drawable.description,
                title = "Description",
                fieldTitle = "Enter a description for your meeting",
                description = "description",
                textState = meetingDescriptionState
            ) { newValue ->
                println("it is: $newValue")

            }
        }

        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.date),
                    contentDescription = "date icon",
                    modifier = Modifier
                        .fillMaxWidth(0.1f) // Image takes 10% of the parent's width
                        .aspectRatio(1f) // Ensures the image remains square
                )
                Spacer(modifier = Modifier.width(15.dp))
                Text(
                    text = "Date & Time", style = MaterialTheme.typography.labelMedium.copy(
                        color = Color(0xDD191919), fontWeight = FontWeight.Bold, fontSize = 20.sp
                    )
                )
            }

            spacer2()
            spacer2()
            spacer2()

            CustomButton(title = dateTimeText, color = Color(0xDD191919)) {
                DatePickerDialog(
                    context,
                    { _, year, month, dayOfMonth ->
                        calendar.set(Calendar.YEAR, year)
                        calendar.set(Calendar.MONTH, month)
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                        TimePickerDialog(
                            context,
                            { _, hourOfDay, minute ->
                                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                                calendar.set(Calendar.MINUTE, minute)

                                selectedDateTime =
                                    "${dayOfMonth}-${month + 1}-$year $hourOfDay:$minute"
                                Toast.makeText(
                                    context, "Selected: $selectedDateTime", Toast.LENGTH_SHORT
                                ).show()

                            },
                            calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE),
                            true
                        ).show()
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                ).apply {
                    // Disable previous dates by setting the minimum date
                    datePicker.minDate =
                        System.currentTimeMillis() // Sets the minimum date to today
                }.show()
            }

            if (selectedDateTime.isNotEmpty()) {
                dateTimeText = selectedDateTime
            }

            Spacer(modifier = Modifier.height(30.dp))
        }

        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.priority),
                    contentDescription = "priority icon",
                    modifier = Modifier
                        .fillMaxWidth(0.1f) // Image takes 10% of the parent's width
                        .aspectRatio(1f) // Ensures the image remains square
                )
                Spacer(modifier = Modifier.width(15.dp))
                Text(
                    text = "Priority", style = MaterialTheme.typography.labelMedium.copy(
                        color = Color(0xDD191919), fontWeight = FontWeight.Bold, fontSize = 20.sp
                    )
                )
            }
            Spacer(modifier = Modifier.height(5.dp))

            PriorityBar(LocalContext.current, selectedPriority)

            Spacer(modifier = Modifier.height(30.dp))
        }

        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.assigned),
                    contentDescription = "assigned icon",
                    modifier = Modifier
                        .fillMaxWidth(0.1f) // Image takes 10% of the parent's width
                        .aspectRatio(1f) // Ensures the image remains square
                )
                Spacer(modifier = Modifier.width(15.dp))
                Text(
                    text = "Assigned For", style = MaterialTheme.typography.labelMedium.copy(
                        color = Color(0xDD191919), fontWeight = FontWeight.Bold, fontSize = 20.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(5.dp))

            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState())
            ) {
                avatars.forEachIndexed { index, avatar ->
                    AvatarImage(avatar = avatar,
                        content = avatarLabels[index],
                        isSelected = selectedAvatarIndex == index,
                        onClick = {
                            selectedAvatarIndex = index // Update the selected avatar
                            Toast.makeText(
                                context,
                                "Selected: ${avatarLabels[index]} with $selectedAvatarIndex",
                                Toast.LENGTH_SHORT
                            ).show()
                        })
                }
            }
            spacer1()
        }

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
                val meetingModel = MeetingModel(
                    meetingTitle = meetingTitleState.value,
                    meetingDescription = meetingDescriptionState.value,
                    meetingDateTime = selectedDateTime,
                    priority = selectedPriority.value,
                    assignedIndex = selectedAvatarIndex
                )

                if (meetingModel.meetingTitle.isNotEmpty() && meetingModel.meetingDateTime.isNotEmpty()) {
                    Log.d("mesaj", "girdi")
                    saveMeetingToFirestore(meetingModel, context)
                } else {
                    Toast.makeText(
                        context, "Please fill in all required fields..", Toast.LENGTH_SHORT
                    ).show()
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            CustomButton(title = "Clear Fields", color = Color(0xFFF2B1B1)) {
                selectedAvatarIndex = 0
                selectedDateTime = ""
                dateTimeText = "Select Date & Time"
                selectedPriority.value = 0f
            }
        }
    }
}

@Composable
fun AvatarImage(
    avatar: Int, content: String, isSelected: Boolean, onClick: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick() }) {
        // Box containing the image with border
        Box(
            modifier = Modifier
                .size(90.dp)
                .clip(CircleShape)
                .border(
                    width = 6.dp,
                    color = if (isSelected) Color(0xDD191919).copy(alpha = 0.8f) else Color.Transparent,
                    shape = CircleShape
                ), contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = avatar),
                contentDescription = content,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        spacer2()
        spacer2()
        spacer2()
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
    val starSize = (30f * density).dp
    val starSpacing = (0.1f * density).dp

    Row(
        modifier = Modifier.selectableGroup(), verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 1..maxStars) {
            val isSelected = i <= rating
            val icon = if (isSelected) Icons.Rounded.Star else Icons.Rounded.Star
            val iconTintColor = if (isSelected) Color(0xFFF2B1B1) else Color(0x44191919)
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
fun PriorityBar(context: Context, selectedPriority: MutableState<Float>) {
    StarRatingBar(maxStars = 5, rating = selectedPriority.value, onRatingChanged = {
        selectedPriority.value = it
        Toast.makeText(context, "Selected Priority: ${selectedPriority.value}", Toast.LENGTH_SHORT)
            .show()
    })
}