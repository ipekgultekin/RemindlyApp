package com.yazilimxyz.remindly

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.firebase.firestore.FirebaseFirestore

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

    var currentUser:String by mutableStateOf("")

    init {
        listenToRoleChanges()
    }

    private fun listenToRoleChanges() {
        val db = FirebaseFirestore.getInstance()

        db.collection("credentials").document("current_user")
            .addSnapshotListener { snapshot, _ ->
                snapshot?.let {
                    currentUser = it.getString("current_user") ?: ""
                }
            }

        db.collection("credentials").document("admin_credentials")
            .addSnapshotListener { snapshot, _ ->
                snapshot?.let {
                    adminEmail = it.getString("email") ?: "yok"
                    adminPassword = it.getString("password") ?: "yok"
                }
            }

        db.collection("credentials").document("yonetim_kurulu_credentials")
            .addSnapshotListener { snapshot, _ ->
                snapshot?.let {
                    yonetimKuruluEmail = it.getString("email") ?: "yok"
                    yonetimKuruluPassword = it.getString("password") ?: "yok"
                }
            }

        db.collection("credentials").document("ekip_lideri_credentials")
            .addSnapshotListener { snapshot, _ ->
                snapshot?.let {
                    ekipLideriEmail = it.getString("email") ?: "yok"
                    ekipLideriPassword = it.getString("password") ?: "yok"
                }
            }

        db.collection("credentials").document("asistan_credentials")
            .addSnapshotListener { snapshot, _ ->
                snapshot?.let {
                    asistanEmail = it.getString("email") ?: "yok"
                    asistanPassword = it.getString("password") ?: "yok"
                }
            }

        db.collection("credentials").document("calisan_credentials")
            .addSnapshotListener { snapshot, _ ->
                snapshot?.let {
                    calisanEmail = it.getString("email") ?: "yok"
                    calisanPassword = it.getString("password") ?: "yok"
                }
            }
    }

    fun setUser(user: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("credentials").document("current_user")
            .set(mapOf("current_user" to user))
            .addOnSuccessListener {
                currentUser = user
            }
    }
}
