package ru.underfish.app.entities


import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*


enum class NotificationType {
    event,
    system,
    message
}

enum class NotificationPriority {
    low,
    normal,
    high
}


@Entity
@Table(name = "Notifications")
data class Notification(
    @Id
    @GeneratedValue
    @Column(name = "notification_id")
    val notificationId: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,

    @Column(name = "title", nullable = false, length = 255)
    var title: String,

    @Column(name = "message", columnDefinition = "TEXT")
    var message: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    var type: NotificationType? = null,

    @Column(name = "is_read")
    var isRead: Boolean = false,

    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    var event: Event? = null, //Может не должен быть nullable? (Тогда нужно поменять и в БД)

    @Column(name = "action_url", columnDefinition = "TEXT")
    var actionUrl: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    var priority: NotificationPriority = NotificationPriority.normal
)
