package com.yazilimxyz.remindly

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.tasks.await

class MeetingRepository {

    private val firestore = FirebaseFirestore.getInstance()

    suspend fun saveMeeting(meeting: MeetingModel): Boolean {
        return try {
            firestore.collection("meetings")
                .add(meeting)
                .await()
            true
        } catch (e: FirebaseFirestoreException) {
            false
        }
    }
}