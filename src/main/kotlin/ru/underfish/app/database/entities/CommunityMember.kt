package ru.underfish.app.database.entities

import jakarta.persistence.*
import lombok.Data
import ru.underfish.app.database.entities.enums.MemberRole

@Entity
@Table(name = "community_member")
@Data
data class CommunityMember(

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id", nullable = false) val user: User,

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "community_id", nullable = false) val community: Community
) : AbstractEntity() {

    @Enumerated(EnumType.STRING)
    @Column(
        name = "member_role",
        columnDefinition = "VARCHAR(20) DEFAULT 'member' CHECK (member_role IN ('member', 'organizer'))"
    )
    val role: MemberRole = MemberRole.member
}