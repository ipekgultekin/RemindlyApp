package com.yazilimxyz.remindly.models


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.yazilimxyz.remindly.MeetingModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import androidx.compose.ui.graphics.Color
import com.yazilimxyz.remindly.screens.pages.TaskItem

class HomeViewModel : ViewModel() {

    // MutableStateFlow kullanarak veri akışını tanımla
    private val _meetings = MutableStateFlow<List<TaskItem>>(emptyList())
    val meetings: StateFlow<List<TaskItem>> get() = _meetings

    init {
        // ViewModel ilk başlatıldığında toplantıları yükle
        loadMeetings()
    }

    // Firestore'dan toplantıları yükleyen fonksiyon
    private fun loadMeetings() {
        viewModelScope.launch {
            val meetingList = getMeetingsFromFirestore().map { meeting ->
                TaskItem(
                    title = meeting.meetingTitle,
                    timeLeft = meeting.meetingDateTime, // Zamanı buradan çekiyoruz bunu daha estetik yaz
                    color = getPriorityColor(meeting.priority), // Sabit renk belirleme
                    colorName = getPriorityColorName(meeting.priority) // Sabit renk adı
                )
            }
            _meetings.value = meetingList
        }
    }

    // Firestore'dan toplantıları çeken suspend fonksiyon
    private suspend fun getMeetingsFromFirestore(): List<MeetingModel> {
        val firestore = FirebaseFirestore.getInstance()
        val meetingList = mutableListOf<MeetingModel>()

        try {
            val snapshot = firestore.collection("meetings").get().await()
            for (document in snapshot.documents) {
                val meeting = document.toObject(MeetingModel::class.java)
                meeting?.let { meetingList.add(it) }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return meetingList
    }

    // Önceliğe göre sabit renkler belirleyen yardımcı fonksiyonlar
    private fun getPriorityColor(priority: Float): Color {
        return when (priority) {
            0f -> Color.Red
            1f -> Color.Yellow
            2f -> Color.Green
            else -> Color.Gray
        }
    }

    private fun getPriorityColorName(priority: Float): String {
        return when (priority) {
            0f -> "Red"
            1f -> "Yellow"
            2f -> "Green"
            else -> "Gray"
        }
    }
}
