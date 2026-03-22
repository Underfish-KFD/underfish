package ru.underfish.app.database.dao

import ru.underfish.app.database.entities.CommunityMember

interface CommunityMemberRepository : AbstractRepository<CommunityMember> {
    fun findByCommunityId(communityId: Long): List<CommunityMember>
    fun findByCommunityIdAndUserId(communityId: Long, userId: Long): CommunityMember?
    fun existsByCommunityIdAndUserId(communityId: Long, userId: Long): Boolean
    fun deleteByCommunityIdAndUserId(communityId: Long, userId: Long): Long
}
