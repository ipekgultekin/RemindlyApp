package com.yazilimxyz.remindly

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.tasks.await

fun saveMeetingToFirestore(meetingModel: MeetingModel, context: Context) {

    Log.d("mesaj", "tikladi")
//    FirebaseApp.initializeApp(context)
    val firestore = FirebaseFirestore.getInstance()
    Log.d("mesaj", "tikladi2")
    firestore.collection("meetings")
        .add(meetingModel)
        .addOnSuccessListener {
            Log.d("mesaj", "Meeting created successfully!")
            Toast.makeText(context, "Meeting created successfully!", Toast.LENGTH_SHORT).show()
        }
        .addOnFailureListener { e ->
            Log.d("mesaj", "Meeting hata: ${e.message}")
            Toast.makeText(context, "Error creating meeting: ${e.message}", Toast.LENGTH_SHORT).show()
        }
}
