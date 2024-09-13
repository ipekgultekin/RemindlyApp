package com.yazilimxyz.remindly

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

data class RoleCredentials(
    val email: String = "", val password: String = ""
)

fun saveRoleCredentials(role: String, email: String, password: String) {
    if (!isValidEmail(email)) {
        println("mesaj: Invalid email format.")
        return
    }

    if (!isValidPassword(password)) {
        println("mesaj: Password does not meet the requirements.")
        return
    }

    val db = FirebaseFirestore.getInstance()
    val roleCredentials = RoleCredentials(email, password)

    db.collection("credentials").document(role).set(roleCredentials).addOnSuccessListener {
            // Handle success
            println("mesaj: Credentials saved successfully!")
        }.addOnFailureListener { e ->
            // Handle failure
            println("mesaj: Error saving credentials: $e")
        }
}

fun isValidEmail(email: String): Boolean {
    // Regex pattern for valid email format
    val emailRegex = "^[A-Za-z0-9+_.-]+@(.+)\$".toRegex()
    return emailRegex.matches(email)
}

fun isValidPassword(password: String): Boolean {
    return password.length >= 6
}



class AdminViewModel : ViewModel() {

    // Observable state variables
    var email = mutableStateOf<String?>(null)
        public set
    var password = mutableStateOf<String?>(null)
        public set

    // Function to fetch admin credentials
    fun loadAdminCredentials() {
        viewModelScope.launch {
            val db = FirebaseFirestore.getInstance()
            val docRef = db.collection("credentials").document("admin_credentials")

            try {
                val document = docRef.get().await()
                if (document != null && document.exists()) {
                    email.value = document.getString("email")
                    password.value = document.getString("password")
                } else {
                    // Handle case where document does not exist
                }
            } catch (e: Exception) {
                // Handle potential errors
            }
        }
    }
}


