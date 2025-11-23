package ru.underfish.app.entities

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

enum class EventStatus {
    published,
    cancelled,
    completed,
    draft
}

@Entity
@Table(name = "Events")
data class Event(
    @Id
    @GeneratedValue
    @Column(name = "event_id")
    val eventId: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @Column(length = 255)
    var title: String? = null,

    @Column(name = "start_datetime", nullable = false)
    var startDatetime: LocalDateTime? = null,

    @Column(name = "end_datetime")
    var endDatetime: LocalDateTime? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    var location: Location? = null,

    @Column(columnDefinition = "DECIMAL(10,2) DEFAULT 0")
    var price: BigDecimal? = BigDecimal.ZERO,

    @Column(length = 3)
    var currency: String? = "RUB",

    @Column(name = "poster_url", columnDefinition = "TEXT")
    var posterUrl: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "event_status")
    var eventStatus: EventStatus = EventStatus.draft,

    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "max_participants")
    var maxParticipants: Int? = null,

    @Column(name = "is_online")
    var isOnline: Boolean = false
)

