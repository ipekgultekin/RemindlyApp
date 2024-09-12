package com.yazilimxyz.remindly.screens.pages.profile_pages.role_pages

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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.sp
import com.yazilimxyz.remindly.AddCredentialsScreen
import com.yazilimxyz.remindly.R

@Composable
fun adminProfilePage() {

    var isShowEditRolesDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (isShowEditRolesDialog) {
            showEditRolesDialog()
        }


        Image(
            painter = painterResource(id = R.drawable.avatar2),
            contentDescription = null,
            modifier = Modifier
                .size(130.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

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
            onClick = {
                isShowEditRolesDialog = true
            }, colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFF2B1B1)
            ), shape = MaterialTheme.shapes.small // Apply custom shape
        ) {
            Text("Edit Roles", style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
            , modifier = Modifier.padding(12.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier.width(300.dp),
            onClick = {

            }, colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFF2B1B1)
            ), shape = MaterialTheme.shapes.small
        ) {
            Text("Schedule Meetings", style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
                , modifier = Modifier.padding(12.dp))        }


    }
}

@Composable
fun showEditRolesDialog() {
    // State to control whether the dialog is shown or not
    var openDialog by remember { mutableStateOf(true) }

    if (openDialog) {
        AlertDialog(
            onDismissRequest = {
                openDialog = false // Close dialog when touched outside or back press
            },
            title = {
                Text(text = "Edit Roles")
            },
            text = {
                Text("Here you can edit roles.") // Main dialog content
            },
            confirmButton = {
                Button(
                    onClick = {
                        // Perform the role editing logic here
                        openDialog = false // Close the dialog after action
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        openDialog = false // Close dialog without taking action
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}
