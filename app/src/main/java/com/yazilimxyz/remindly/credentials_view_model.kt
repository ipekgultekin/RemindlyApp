package com.yazilimxyz.remindly

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.yazilimxyz.remindly.screens.signinFirebase
import kotlinx.coroutines.tasks.await


class AuthViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    suspend fun signUp(email: String, password: String) {
        try {
            auth.createUserWithEmailAndPassword(email, password).await()

            Log.d("mesaj", "autha kaydedildi")
        } catch (e: Exception) {
            Log.d("mesaj", e.message.toString())
            throw e
        }
    }
}


object RoleCredentialsRepository {
    var adminEmail: String by mutableStateOf("")
    var adminPassword: String by mutableStateOf("")

    var yonetimKuruluEmail: String by mutableStateOf("")
    var yonetimKuruluPassword: String by mutableStateOf("")

    var ekipLideriEmail: String by mutableStateOf("")
    var ekipLideriPassword: String by mutableStateOf("")

    var asistanEmail: String by mutableStateOf("")
    var asistanPassword: String by mutableStateOf("")

    var calisanEmail: String by mutableStateOf("")
    var calisanPassword: String by mutableStateOf("")

    init {
        listenToRoleChanges()
    }

    private fun listenToRoleChanges() {
        val db = FirebaseFirestore.getInstance()

        db.collection("credentials").document("admin_credentials")
            .addSnapshotListener { snapshot, _ ->
                snapshot?.let {
                    adminEmail = it.getString("email") ?: ""
                    adminPassword = it.getString("password") ?: ""
                }
            }

        db.collection("credentials").document("yonetim_kurulu_credentials")
            .addSnapshotListener { snapshot, _ ->
                snapshot?.let {
                    yonetimKuruluEmail = it.getString("email") ?: ""
                    yonetimKuruluPassword = it.getString("password") ?: ""
                }
            }

        db.collection("credentials").document("ekip_lideri_credentials")
            .addSnapshotListener { snapshot, _ ->
                snapshot?.let {
                    ekipLideriEmail = it.getString("email") ?: ""
                    ekipLideriPassword = it.getString("password") ?: ""
                }
            }

        db.collection("credentials").document("asistan_credentials")
            .addSnapshotListener { snapshot, _ ->
                snapshot?.let {
                    asistanEmail = it.getString("email") ?: ""
                    asistanPassword = it.getString("password") ?: ""
                }
            }

        db.collection("credentials").document("calisan_credentials")
            .addSnapshotListener { snapshot, _ ->
                snapshot?.let {
                    calisanEmail = it.getString("email") ?: ""
                    calisanPassword = it.getString("password") ?: ""
                }
            }
    }
}
