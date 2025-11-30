package ru.underfish.app.database.entities

import jakarta.persistence.*
import ru.underfish.app.database.entities.enums.EventStatus
import java.math.BigDecimal
import java.time.LocalDateTime
import lombok.Data

@Entity
@Data
@Table(name = "events")
data class Event(

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id", nullable = false) val user: User,

    @Column(length = 255) var title: String? = null
) : AbstractEntity() {

    @Column(name = "start_datetime", nullable = false)
    var startDatetime: LocalDateTime? = null

    @Column(name = "end_datetime")
    var endDatetime: LocalDateTime? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    var location: Location? = null

    @Column(name = "price", columnDefinition = "DECIMAL(10,2) DEFAULT 0")
    var price: BigDecimal = BigDecimal.ZERO

    @Column(name = "currency", length = 3)
    var currency: String = "RUB"

    @Column(name = "poster_url", columnDefinition = "TEXT")
    var posterUrl: String? = null

    @Enumerated(EnumType.STRING)
    @Column(
        name = "event_status",
        columnDefinition = "VARCHAR(20) DEFAULT 'draft' CHECK (event_status IN ('published', 'cancelled', 'completed', 'draft'))"
    )
    var eventStatus: EventStatus = EventStatus.draft


    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()

    @Column(name = "max_participants")
    var maxParticipants: Int = 0

    @Column(name = "is_online")
    var isOnline: Boolean = false

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "EventTags",
        joinColumns = [JoinColumn(name = "event_id")],
        inverseJoinColumns = [JoinColumn(name = "tag_id")]
    )
    var tags: MutableSet<Tag> = mutableSetOf()
}

