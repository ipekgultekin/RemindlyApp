package com.yazilimxyz.remindly

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

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

fun getRoleCredentials(
    documentCredentials: String, roleEmail: String, onSuccess: (String) -> Unit
) {
    val db = FirebaseFirestore.getInstance()

    db.collection("credentials").document(documentCredentials).get()
        .addOnSuccessListener { document ->
            if (document != null) {
                val fetchedEmail = document.getString(roleEmail)
                if (fetchedEmail != null) {
                    onSuccess(fetchedEmail)
                }
            }
        }.addOnFailureListener { e ->
            Log.e("mesaj", "Error getting credentials: $e")
        }
}

/*
suspend fun deleteFirestoreDocument(collection: String, documentId: String): Boolean {
    return try {
        FirebaseFirestore.getInstance().collection(collection).document(documentId).delete().await()
        true
    } catch (e: Exception) {
        false
    }
}
*/

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
