package ru.underfish.app.dto.response

import ru.underfish.app.database.entities.Community
import java.time.LocalDateTime

data class CommunityResponse(
    val communityId: String,
    val name: String,
    val description: String?,
    val organizerId: String,
    val coverUrl: String?,
    val createdAt: LocalDateTime,
    val isPrivate: Boolean,
) {
    companion object {
        fun fromEntity(
            community: Community,
            organizerId: Long,
        ): CommunityResponse =
            CommunityResponse(
                communityId = community.id.toString(),
                organizerId = organizerId.toString(),
                name = community.name,
                description = community.description,
                coverUrl = community.coverUrl,
                createdAt = community.createdAt,
                isPrivate = community.isPrivate,
            )
    }
}
