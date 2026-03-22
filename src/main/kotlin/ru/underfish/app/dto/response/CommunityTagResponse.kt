package ru.underfish.app.dto.response

data class CommunityTagResponse(
    val communityId: String,
    val tagId: String,
) {
    companion object {
        fun fromIds(
            communityId: Long,
            tagId: Long,
        ): CommunityTagResponse =
            CommunityTagResponse(
                communityId = communityId.toString(),
                tagId = tagId.toString(),
            )
    }
}
