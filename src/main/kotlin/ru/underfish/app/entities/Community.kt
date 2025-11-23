package ru.underfish.app.entities

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "Communities")
data class Community(
    @Id
    @GeneratedValue
    @Column(name = "community_id")
    val communityId: UUID?,

    @Column(length = 255, nullable = false)
    var name: String? = null,

    @Column(columnDefinition = "TEXT")
    var description: String? = null,

    @Column(name = "created_at")
    var createdAt: LocalDateTime? = LocalDateTime.now(),

    @Column(name = "is_private")
    var isPrivate: Boolean? = false,

    @Column(name = "cover_url", columnDefinition = "TEXT")
    var coverUrl: String? = null
)
