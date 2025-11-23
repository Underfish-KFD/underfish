package ru.underfish.app.entities

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "community_organizer")
data class CommunityOrganizer(
    @Id
    @GeneratedValue
    val id: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id", nullable = false)
    val community: Community,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User
)