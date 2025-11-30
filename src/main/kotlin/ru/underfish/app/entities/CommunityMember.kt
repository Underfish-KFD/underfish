package ru.underfish.app.entities

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

enum class MemberRole {
    member, organizer
}

@Entity
@Table(name = "community_member")
data class CommunityMember(
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    val id: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id", nullable = false)
    val community: Community,

    @Enumerated(EnumType.STRING)
    @Column(name = "member_role")
    val role: MemberRole = MemberRole.member,

    @Column(name = "joined_at")
    val joinedAt: LocalDateTime = LocalDateTime.now()
)