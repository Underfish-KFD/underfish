package ru.underfish.app.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import ru.underfish.app.dto.response.NotificationResponse
import ru.underfish.app.service.NotificationService

@RestController
class NotificationController(
    private val notificationService: NotificationService
) {
    @GetMapping("/api/v1/users/{user_id}/notifications")
    fun getUserNotifications(@PathVariable("user_id") userId: Long): List<NotificationResponse> {
        return notificationService.getUserNotifications(userId)
    }

    @PutMapping("/api/v1/notifications/{notification_id}/read")
    fun markAsRead(@PathVariable("notification_id") notificationId: Long): NotificationResponse {
        return notificationService.markAsRead(notificationId)
    }

    @DeleteMapping("/api/v1/notifications/{notification_id}/read")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteNotification(@PathVariable("notification_id") notificationId: Long) {
        notificationService.deleteNotification(notificationId)
    }
}

