package ru.underfish.app.dto.response

import ru.underfish.app.database.entities.CommunityOrganizer

data class CommunityOrganizerResponse(
    val communityId: String,
    val userId: String
) {
    companion object {
        fun fromEntity(organizer: CommunityOrganizer): CommunityOrganizerResponse {
            return CommunityOrganizerResponse(
                communityId = organizer.community.id.toString(),
                userId = organizer.user.id.toString()
            )
        }
    }
}

