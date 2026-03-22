package ru.underfish.app.database.entities


import jakarta.persistence.*
import ru.underfish.app.database.entities.enums.NotificationPriority
import ru.underfish.app.database.entities.enums.NotificationType
import lombok.Data


@Entity
@Data
@Table(name = "notifications")
data class Notification(

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id", nullable = false) var user: User,

    @Column(name = "title", nullable = false, length = 255) var title: String
) : AbstractEntity() {

    @Column(name = "message", columnDefinition = "TEXT")
    var message: String? = null

    @Enumerated(EnumType.STRING)
    @Column(
        name = "type", columnDefinition = "VARCHAR(20) DEFAULT 'system' CHECK (type IN ('event', 'system', 'message'))"
    )
    var type: NotificationType = NotificationType.system

    @Column(name = "is_read")
    var isRead: Boolean = false


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    var event: Event? = null

    @Column(name = "action_url", columnDefinition = "TEXT")
    var actionUrl: String? = null

    @Enumerated(EnumType.STRING)
    @Column(
        name = "priority",
        columnDefinition = "VARCHAR(20) DEFAULT 'normal' CHECK (priority IN ('low', 'normal', 'high'))"
    )
    var priority: NotificationPriority = NotificationPriority.normal
}
