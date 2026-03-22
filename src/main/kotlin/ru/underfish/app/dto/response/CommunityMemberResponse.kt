package ru.underfish.app.dto.response

import ru.underfish.app.database.entities.CommunityMember
import java.time.LocalDateTime

data class CommunityMemberResponse(
    val memberId: String,
    val userId: String,
    val communityId: String,
    val joinedAt: LocalDateTime,
) {
    companion object {
        fun fromEntity(member: CommunityMember): CommunityMemberResponse =
            CommunityMemberResponse(
                memberId = member.id.toString(),
                userId = member.user.id.toString(),
                communityId = member.community.id.toString(),
                joinedAt = member.createdAt,
            )
    }
}
