package ru.underfish.app.service

import org.springframework.stereotype.Service
import ru.underfish.app.database.dao.NotificationRepository
import ru.underfish.app.dto.response.NotificationResponse
import ru.underfish.app.exception.NotFoundException

@Service
class NotificationService(
    private val notificationRepository: NotificationRepository,
) {
    fun getUserNotifications(userId: Long): List<NotificationResponse> =
        notificationRepository
            .findByUserIdOrderByCreatedAtDesc(userId)
            .map(NotificationResponse::fromEntity)

    fun markAsRead(notificationId: Long): NotificationResponse {
        val notification =
            notificationRepository.findById(notificationId).orElseThrow {
                NotFoundException("Notification not found")
            }

        notification.isRead = true
        return NotificationResponse.fromEntity(notificationRepository.save(notification))
    }

    fun deleteNotification(notificationId: Long) {
        val notification =
            notificationRepository.findById(notificationId).orElseThrow {
                NotFoundException("Notification not found")
            }
        notificationRepository.delete(notification)
    }
}
