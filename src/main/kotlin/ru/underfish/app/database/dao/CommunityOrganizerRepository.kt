package ru.underfish.app.database.dao

import ru.underfish.app.database.entities.CommunityOrganizer

interface CommunityOrganizerRepository : AbstractRepository<CommunityOrganizer> {
    fun findByCommunityId(communityId: Long): List<CommunityOrganizer>

    fun findByCommunityIdAndUserId(
        communityId: Long,
        userId: Long,
    ): CommunityOrganizer?

    fun existsByCommunityIdAndUserId(
        communityId: Long,
        userId: Long,
    ): Boolean

    fun deleteByCommunityIdAndUserId(
        communityId: Long,
        userId: Long,
    ): Long
}
