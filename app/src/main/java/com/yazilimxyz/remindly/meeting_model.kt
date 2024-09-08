package com.yazilimxyz.remindly

data class MeetingModel(
    val meetingTitle: String = "",
    val meetingDescription: String = "",
    val meetingDateTime: String = "",
    val priority: Float = 0f,
    val assignedIndex: Int = 0
)