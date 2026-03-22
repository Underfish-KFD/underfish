package ru.underfish.app.service

import org.springframework.stereotype.Service
import ru.underfish.app.database.dao.CommunityMemberRepository
import ru.underfish.app.database.dao.CommunityOrganizerRepository
import ru.underfish.app.database.dao.CommunityRepository
import ru.underfish.app.database.dao.UserRepository
import ru.underfish.app.database.entities.CommunityMember
import ru.underfish.app.database.entities.CommunityOrganizer
import ru.underfish.app.dto.response.CommunityOrganizerResponse
import ru.underfish.app.exception.BadRequestException
import ru.underfish.app.exception.NotFoundException

@Service
class CommunityOrganizerService(
    private val communityOrganizerRepository: CommunityOrganizerRepository,
    private val userRepository: UserRepository,
    private val communityRepository: CommunityRepository,
    private val communityMemberRepository: CommunityMemberRepository
) {
    fun createOrganizer(organizerId: Long, communityId: Long): CommunityOrganizer {
        val user = userRepository.findUserById(organizerId)
            ?: throw NotFoundException("Organizer user not found")
        val community = communityRepository.findById(communityId).orElseThrow {
            NotFoundException("Community not found")
        }

        val communityOrganizer = CommunityOrganizer(
            community = community,
            user = user
        )

        val savedOrganizer = communityOrganizerRepository.save(communityOrganizer)

        if (!communityMemberRepository.existsByCommunityIdAndUserId(communityId, organizerId)) {
            communityMemberRepository.save(
                CommunityMember(
                    user = user,
                    community = community
                )
            )
        }

        return savedOrganizer
    }

    fun addOrganizer(communityId: Long, userId: Long): CommunityOrganizerResponse {
        if (communityOrganizerRepository.existsByCommunityIdAndUserId(communityId, userId)) {
            throw BadRequestException("Organizer already exists in community")
        }

        val organizer = createOrganizer(userId, communityId)
        return CommunityOrganizerResponse.fromEntity(organizer)
    }

    fun getOrganizersByCommunityId(communityId: Long): List<CommunityOrganizerResponse> {
        communityRepository.findById(communityId).orElseThrow {
            NotFoundException("Community not found")
        }

        return communityOrganizerRepository.findByCommunityId(communityId)
            .map(CommunityOrganizerResponse::fromEntity)
    }

    fun removeOrganizer(communityId: Long, userId: Long) {
        val deleted = communityOrganizerRepository.deleteByCommunityIdAndUserId(communityId, userId)
        if (deleted == 0L) {
            throw NotFoundException("Organizer not found in community")
        }
    }

    fun getOrganizerIdByCommunityId(communityId: Long): Long {
        val organizer = communityOrganizerRepository.findByCommunityId(communityId).firstOrNull()
            ?: throw NotFoundException("Organizer for community not found")
        return organizer.user.id
    }
}