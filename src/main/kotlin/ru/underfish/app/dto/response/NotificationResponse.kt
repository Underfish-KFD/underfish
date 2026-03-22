package ru.underfish.app.dto.response

import ru.underfish.app.database.entities.Notification
import java.time.LocalDateTime

data class NotificationResponse(
    val notificationId: String,
    val userId: String,
    val title: String,
    val message: String?,
    val type: String,
    val isRead: Boolean,
    val createdAt: LocalDateTime,
    val actionUrl: String?,
    val priority: String,
    val eventId: String?
) {
    companion object {
        fun fromEntity(notification: Notification): NotificationResponse {
            return NotificationResponse(
                notificationId = notification.id.toString(),
                userId = notification.user.id.toString(),
                title = notification.title,
                message = notification.message,
                type = notification.type.name,
                isRead = notification.isRead,
                createdAt = notification.createdAt,
                actionUrl = notification.actionUrl,
                priority = notification.priority.name,
                eventId = notification.event?.id?.toString()
            )
        }
    }
}

