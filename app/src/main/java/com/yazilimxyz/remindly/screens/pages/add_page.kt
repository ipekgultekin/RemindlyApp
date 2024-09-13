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
import androidx.compose.runtime.mutableFloatStateOf
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
import com.yazilimxyz.remindly.CustomButton
import com.yazilimxyz.remindly.MeetingModel
import com.yazilimxyz.remindly.R
import com.yazilimxyz.remindly.saveMeetingToFirestore
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
    textState: MutableState<String>,
    onValueChange: (String) -> Unit
) {
    val textColor = MaterialTheme.colorScheme.onBackground // Yazı rengi
    val fieldTitleColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f) // Label rengi

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
                text = title,
                style = MaterialTheme.typography.labelMedium.copy(
                    color = textColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            )
        }

        spacer2()
        spacer2()
        spacer2()

        TextField(
            value = textState.value,
            onValueChange = {
                textState.value = it
                onValueChange(it)
            },
            label = {
                Text(
                    fieldTitle,
                    style = TextStyle(
                        color = fieldTitleColor,
                        fontSize = 15.sp
                    )
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f), // Background color
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 8.dp),
            shape = MaterialTheme.shapes.medium
        )
        spacer1()
    }
}


@Composable
fun AddPage(context: Context) {
    val textColor = MaterialTheme.colorScheme.onBackground

    var selectedAvatarIndex by remember { mutableIntStateOf(0) }
    var selectedDateTime by remember { mutableStateOf("") }
    var dateTimeText by remember { mutableStateOf("Select Date & Time") }

    var selectedPriority = remember { mutableFloatStateOf(3f) } // Default rating value
    var meetingTitleState = remember { mutableStateOf("") }
    var meetingDescriptionState = remember { mutableStateOf("") }

    val avatars = listOf(
        R.drawable.avatar2,
        R.drawable.avatar1,
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
                text = "Create\nNew Meeting",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 40.sp,
                    color = textColor // Yazı rengi
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
                        .fillMaxWidth(0.1f)
                        .aspectRatio(1f)
                )
                Spacer(modifier = Modifier.width(15.dp))
                Text(
                    text = "Date & Time",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = textColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                )
            }

            spacer2()
            spacer2()
            spacer2()

            CustomButton(title = dateTimeText, color = textColor) { // Buton rengi
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
                    datePicker.minDate = System.currentTimeMillis()
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
                        .fillMaxWidth(0.1f)
                        .aspectRatio(1f)
                )
                Spacer(modifier = Modifier.width(15.dp))
                Text(
                    text = "Priority",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = textColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                )
            }
            Spacer(modifier = Modifier.height(5.dp))

            PriorityBar(context, selectedPriority)

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
                    color = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.8f) else Color.Transparent,
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
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = content,
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = MaterialTheme.colorScheme.onBackground // Use theme color for text
            )
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

    })
}