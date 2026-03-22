package ru.underfish.app.dto.request

import java.time.LocalDateTime

data class EventRequest(
    val title: String,
    val description: String,
    val startDatetime: LocalDateTime,
    val endDatetime: LocalDateTime,
    val locationId: String,
    val price: Double? = null,
    val currency: String? = null,
    val posterUrl: String? = null,
    val status: String,
    val maxParticipants: Int? = null,
    val isOnline: Boolean? = null,
)
