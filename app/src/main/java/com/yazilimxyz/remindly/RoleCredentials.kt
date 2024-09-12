package com.yazilimxyz.remindly

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.firebase.firestore.FirebaseFirestore

data class RoleCredentials(
    val email: String = "", val password: String = ""
)

fun saveRoleCredentials(role: String, email: String, password: String) {
    val db = FirebaseFirestore.getInstance()
    val roleCredentials = RoleCredentials(email, password)

    db.collection("credentials").document(role).set(roleCredentials).addOnSuccessListener {
            // Handle success
            println("Credentials saved successfully!")
        }.addOnFailureListener { e ->
            // Handle failure
            println("Error saving credentials: $e")
        }
}

fun getRoleCredentials(onSuccess: (String) -> Unit) {
    val db = FirebaseFirestore.getInstance()

    // Fetch the document with the fixed ID "admin_credentials"
    db.collection("credentials").document("admin_credentials").get().addOnSuccessListener { document ->
        if (document != null) {
            // Extract the admin email field from the document
            val adminEmail = document.getString("adminEmail")
            Log.d("mesaj", "Admin Email: $adminEmail")
            if (adminEmail != null) {
                onSuccess(adminEmail) // Return the adminEmail to the caller
            }
        }
    }.addOnFailureListener { e ->
        Log.e("mesaj", "Error getting admin credentials: $e")
    }
}

@Composable
fun AddCredentialsScreen() {
    var role by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column {
        TextField(value = role, onValueChange = { role = it }, label = { Text("Role") })
        TextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
        TextField(value = password, onValueChange = { password = it }, label = { Text("Password") })

        Button(onClick = { saveRoleCredentials(role, email, password) }) {
            Text("Save Credentials")
        }
    }
}

@Composable
fun ShowCredentialsScreen(role: String) {
    var credentials by remember { mutableStateOf<RoleCredentials?>(null) }

//    LaunchedEffect(role) {
//        getRoleCredentials(role) { retrievedCredentials ->
//            credentials = retrievedCredentials
//        }
//    }

    if (credentials != null) {
        Column {
            Text("Email: ${credentials?.email}")
            Text("Password: ${credentials?.password}")
        }
    } else {
        Text("Loading...")
    }
}
