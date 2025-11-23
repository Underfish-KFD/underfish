package ru.underfish.app.entities

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

enum class AttendanceStatus {
    pending,
    confirmed,
    cancelled,
    attended
}

@Entity
@Table(name = "event_attendances")
data class EventAttendance(
    @Id
    @GeneratedValue
    @Column(name = "attendance_id")
    val id: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    val event: Event,

    @Enumerated(EnumType.STRING)
    var status: AttendanceStatus = AttendanceStatus.pending,

    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now()
)