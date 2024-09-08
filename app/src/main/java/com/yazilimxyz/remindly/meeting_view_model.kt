package com.yazilimxyz.remindly

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MeetingViewModel(private val repository: MeetingRepository) : ViewModel() {

    private val _isMeetingSaved = MutableStateFlow<Boolean>(false)
    val isMeetingSaved = _isMeetingSaved.asStateFlow()

    fun createMeeting(
        title: String,
        description: String,
        dateTime: String,
        priority: Float,
        assignedIndex: Int,
    ) {
        val newMeeting = MeetingModel(
            meetingTitle = title,
            meetingDescription = description,
            meetingDateTime = dateTime,
            priority = priority,
            assignedIndex = assignedIndex,
        )

        viewModelScope.launch {
            val isSaved = repository.saveMeeting(newMeeting)
            _isMeetingSaved.value = isSaved
        }
    }
}
