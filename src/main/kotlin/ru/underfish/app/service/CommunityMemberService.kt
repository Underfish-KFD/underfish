package ru.underfish.app.service

import org.springframework.stereotype.Service
import ru.underfish.app.database.dao.CommunityMemberRepository
import ru.underfish.app.database.dao.CommunityRepository
import ru.underfish.app.database.dao.UserRepository
import ru.underfish.app.database.entities.CommunityMember
import ru.underfish.app.dto.response.CommunityMemberResponse
import ru.underfish.app.exception.BadRequestException
import ru.underfish.app.exception.NotFoundException

@Service
class CommunityMemberService(
    private val communityMemberRepository: CommunityMemberRepository,
    private val userRepository: UserRepository,
    private val communityRepository: CommunityRepository
) {
    fun addMember(communityId: Long, userId: Long): CommunityMemberResponse {
        if (communityMemberRepository.existsByCommunityIdAndUserId(communityId, userId)) {
            throw BadRequestException("Member already exists in community")
        }

        val user = userRepository.findUserById(userId)
            ?: throw NotFoundException("User not found")
        val community = communityRepository.findById(communityId).orElseThrow {
            NotFoundException("Community not found")
        }

        val member = communityMemberRepository.save(
            CommunityMember(
                user = user,
                community = community
            )
        )

        return CommunityMemberResponse.fromEntity(member)
    }

    fun getMembersByCommunityId(communityId: Long): List<CommunityMemberResponse> {
        communityRepository.findById(communityId).orElseThrow {
            NotFoundException("Community not found")
        }

        return communityMemberRepository.findByCommunityId(communityId)
            .map(CommunityMemberResponse::fromEntity)
    }

    fun removeMember(communityId: Long, userId: Long) {
        val deleted = communityMemberRepository.deleteByCommunityIdAndUserId(communityId, userId)
        if (deleted == 0L) {
            throw NotFoundException("Member not found in community")
        }
    }
}

