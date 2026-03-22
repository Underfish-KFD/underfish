package ru.underfish.app.dto.response

import ru.underfish.app.database.entities.Event
import java.time.LocalDateTime

data class EventResponse(
    val eventId: String,
    val title: String,
    val description: String,
    val startDatetime: LocalDateTime,
    val endDatetime: LocalDateTime?,
    val locationId: String,
    val price: Double,
    val currency: String,
    val posterUrl: String?,
    val status: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val maxParticipants: Int,
    val isOnline: Boolean,
) {
    companion object {
        fun fromEntity(event: Event): EventResponse =
            EventResponse(
                eventId = event.id.toString(),
                title = event.title ?: "",
                description = event.description ?: "",
                startDatetime = event.startDatetime ?: event.createdAt,
                endDatetime = event.endDatetime,
                locationId = event.location?.id?.toString() ?: "",
                price = event.price.toDouble(),
                currency = event.currency,
                posterUrl = event.posterUrl,
                status = event.eventStatus.name,
                createdAt = event.createdAt,
                updatedAt = event.updatedAt,
                maxParticipants = event.maxParticipants,
                isOnline = event.isOnline,
            )
    }
}
