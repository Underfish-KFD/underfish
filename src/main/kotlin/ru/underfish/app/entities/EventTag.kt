package ru.underfish.app.entities


import jakarta.persistence.*
import java.io.Serializable
import java.util.*


@Embeddable
data class EventTagId(
    @Column(name = "event_id")
    val eventId: UUID? = null,

    @Column(name = "tag_id")
    val tagId: UUID? = null
) : Serializable


@Entity
@Table(name = "EventTags")
data class EventTag(
    @EmbeddedId
    val id: EventTagId = EventTagId(),

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("eventId")
    @JoinColumn(name = "event_id", nullable = false)
    var event: Event,

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("tagId")
    @JoinColumn(name = "tag_id", nullable = false)
    var tag: Tag
)
