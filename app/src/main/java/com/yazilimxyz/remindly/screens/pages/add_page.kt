package com.yazilimxyz.remindly.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
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
import androidx.compose.runtime.*
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
fun Spacer1() {
    Spacer(modifier = Modifier.height(30.dp))
}

@Composable
fun Spacer2() {
    Spacer(modifier = Modifier.height(5.dp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddField(
    icon: Int,
    title: String,
    fieldTitle: String,
    description: String?,
    textState: MutableState<String>,
    onValueChange: (String) -> Unit
) {
    val textColor = MaterialTheme.colorScheme.onBackground
    val fieldTitleColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)

    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(icon),
                contentDescription = description,
                modifier = Modifier
                    .fillMaxWidth(0.1f)
                    .aspectRatio(1f)
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

        Spacer2()
        Spacer2()
        Spacer2()

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
                containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
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
        Spacer1()
    }
}

@Composable
fun AddPage(context: Context) {
    val textColor = MaterialTheme.colorScheme.onBackground

    var selectedAvatarIndex by remember { mutableStateOf(0) }
    var selectedDateTime by remember { mutableStateOf("") }
    var dateTimeText by remember { mutableStateOf(context.getString(R.string.select_date_time)) }

    var selectedPriority by remember { mutableStateOf(3f) }
    var meetingTitleState by remember { mutableStateOf("") }
    var meetingDescriptionState by remember { mutableStateOf("") }

    val avatars = listOf(
        R.drawable.avatar2,
        R.drawable.avatar1,
        R.drawable.avatar3,
        R.drawable.avatar4,
        R.drawable.avatar5
    )

    val avatarLabels = listOf(
        context.getString(R.string.admin),
        context.getString(R.string.assistant),
        context.getString(R.string.teamleader),
        context.getString(R.string.boardmember),
        context.getString(R.string.employee)
    )


    val calendar = Calendar.getInstance()

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(26.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        item {
            Text(
                text = context.getString(R.string.create_new_meeting),
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 40.sp,
                    color = textColor
                )
            )
            Spacer1()
        }

        item {
            AddField(
                icon = R.drawable.title,
                title = context.getString(R.string.meeting_title),
                fieldTitle = context.getString(R.string.enter_meeting_title),
                description = "meeting",
                textState = mutableStateOf(meetingTitleState)
            ) { newValue ->
                meetingTitleState = newValue
                println("Meeting Title: $newValue")
            }
        }

        item {
            AddField(
                icon = R.drawable.description,
                title = context.getString(R.string.description),
                fieldTitle = context.getString(R.string.enter_description),
                description = "description",
                textState = mutableStateOf(meetingDescriptionState)
            ) { newValue ->
                meetingDescriptionState = newValue
                println("Description: $newValue")
            }
        }

        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.date),
                    contentDescription = "Date icon",
                    modifier = Modifier
                        .fillMaxWidth(0.1f)
                        .aspectRatio(1f)
                )
                Spacer(modifier = Modifier.width(15.dp))
                Text(
                    text = context.getString(R.string.date_time),
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = textColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                )
            }

            Spacer2()
            Spacer2()
            Spacer2()

            CustomButton(title = dateTimeText, color = textColor) {
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

                                selectedDateTime = "${dayOfMonth}-${month + 1}-$year $hourOfDay:$minute"
                                dateTimeText = selectedDateTime
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

            Spacer(modifier = Modifier.height(30.dp))
        }

        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.priority),
                    contentDescription = "Priority icon",
                    modifier = Modifier
                        .fillMaxWidth(0.1f)
                        .aspectRatio(1f)
                )
                Spacer(modifier = Modifier.width(15.dp))
                Text(
                    text = context.getString(R.string.priority),
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = textColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                )
            }
            Spacer(modifier = Modifier.height(5.dp))

            PriorityBar(selectedPriority = mutableStateOf(selectedPriority))

            Spacer(modifier = Modifier.height(30.dp))
        }

        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.assigned),
                    contentDescription = "Assigned icon",
                    modifier = Modifier
                        .fillMaxWidth(0.1f)
                        .aspectRatio(1f)
                )
                Spacer(modifier = Modifier.width(15.dp))
                Text(
                    text = context.getString(R.string.assigned_for),
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = Color(0xDD191919),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(5.dp))

            Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                avatars.forEachIndexed { index, avatar ->
                    AvatarImage(
                        avatar = avatar,
                        content = avatarLabels[index],
                        isSelected = selectedAvatarIndex == index,
                        onClick = {
                            selectedAvatarIndex = index
                        }
                    )
                }
            }
            Spacer1()
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
            CustomButton(title = context.getString(R.string.create_meeting), color = Color(0xDD191919)) {
                val meetingModel = MeetingModel(
                    meetingTitle = meetingTitleState,
                    meetingDescription = meetingDescriptionState,
                    meetingDateTime = selectedDateTime,
                    priority = selectedPriority,
                    assignedIndex = selectedAvatarIndex // Use the correct parameter name
                )

                if (meetingModel.meetingTitle.isNotEmpty() &&
                    meetingModel.meetingDescription.isNotEmpty() &&
                    meetingModel.meetingDateTime.isNotEmpty()) {
                    saveMeetingToFirestore(meetingModel, context)
                } else {
                    Toast.makeText(context, "Please fill in all required fields.", Toast.LENGTH_SHORT).show()
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            CustomButton(title = context.getString(R.string.clear_fields), color = Color(0xFFF2B1B1)) {
                selectedAvatarIndex = 0
                selectedDateTime = ""
                dateTimeText = context.getString(R.string.select_date_time)
                selectedPriority = 0f
                meetingTitleState = ""
                meetingDescriptionState = ""
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
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(90.dp)
                .clip(CircleShape)
                .border(
                    width = 6.dp,
                    color = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.8f) else Color.Transparent,
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
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = content,
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = MaterialTheme.colorScheme.onBackground
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
            val iconTintColor = if (isSelected) Color(0xFFF2B1B1) else Color(0x44191919)
            Icon(
                imageVector = Icons.Rounded.Star,
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
fun PriorityBar(selectedPriority: MutableState<Float>) {
    StarRatingBar(maxStars = 5, rating = selectedPriority.value, onRatingChanged = {
        selectedPriority.value = it
    })
}
