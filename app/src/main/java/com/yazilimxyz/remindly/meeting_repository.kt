package com.yazilimxyz.remindly

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.tasks.await

fun saveMeetingToFirestore(meetingModel: MeetingModel, context: Context) {

    val firestore = FirebaseFirestore.getInstance()
    firestore.collection("meetings")
        .add(meetingModel)
        .addOnSuccessListener {
            Toast.makeText(context, "Meeting created successfully!", Toast.LENGTH_SHORT).show()
        }
        .addOnFailureListener { e ->
            Toast.makeText(context, "Error creating meeting: ${e.message}", Toast.LENGTH_SHORT).show()
        }
}
