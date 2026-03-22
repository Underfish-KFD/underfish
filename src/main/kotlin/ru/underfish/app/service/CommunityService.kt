package ru.underfish.app.service

import org.springframework.stereotype.Service
import ru.underfish.app.database.dao.CommunityRepository
import ru.underfish.app.database.entities.Community
import ru.underfish.app.dto.request.CommunityCreateRequest
import ru.underfish.app.dto.response.CommunityResponse
import ru.underfish.app.exception.NotFoundException

@Service
class CommunityService(
    private val communityRepository: CommunityRepository,
    private val communityOrganizerService: CommunityOrganizerService,
) {
    fun createCommunity(
        request: CommunityCreateRequest,
        organizerId: Long,
    ): CommunityResponse {
        val community =
            Community(name = request.name).apply {
                description = request.description
                coverUrl = request.coverUrl
                isPrivate = request.isPrivate
            }

        val savedCommunity = communityRepository.save(community)
        val communityOrganizer = communityOrganizerService.createOrganizer(organizerId, savedCommunity.id)

        return CommunityResponse.fromEntity(savedCommunity, communityOrganizer.user.id)
    }

    fun getCommunities(): List<CommunityResponse> =
        communityRepository.findAll().map { community ->
            val organizerId = communityOrganizerService.getOrganizerIdByCommunityId(community.id)
            CommunityResponse.fromEntity(community, organizerId)
        }

    fun getCommunityById(communityId: Long): CommunityResponse {
        val community =
            communityRepository.findById(communityId).orElseThrow {
                NotFoundException("Community not found")
            }
        val organizerId = communityOrganizerService.getOrganizerIdByCommunityId(community.id)
        return CommunityResponse.fromEntity(community, organizerId)
    }

    fun updateCommunity(
        communityId: Long,
        request: CommunityCreateRequest,
    ): CommunityResponse {
        val community =
            communityRepository.findById(communityId).orElseThrow {
                NotFoundException("Community not found")
            }

        community.name = request.name
        community.description = request.description
        community.coverUrl = request.coverUrl
        community.isPrivate = request.isPrivate

        val updatedCommunity = communityRepository.save(community)
        val organizerId = communityOrganizerService.getOrganizerIdByCommunityId(updatedCommunity.id)
        return CommunityResponse.fromEntity(updatedCommunity, organizerId)
    }

    fun deleteCommunity(communityId: Long) {
        val community =
            communityRepository.findById(communityId).orElseThrow {
                NotFoundException("Community not found")
            }
        communityRepository.delete(community)
    }
}
