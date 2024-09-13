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

    fun loadRoles() {

    }

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
