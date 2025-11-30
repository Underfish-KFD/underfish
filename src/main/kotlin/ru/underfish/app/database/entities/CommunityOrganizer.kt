package ru.underfish.app.database.entities

import jakarta.persistence.*
import lombok.Data

@Entity
@Data
@Table(name = "community_organizer")
data class CommunityOrganizer(

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "community_id", nullable = false) val community: Community,

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id", nullable = false) val user: User
) : AbstractEntity()