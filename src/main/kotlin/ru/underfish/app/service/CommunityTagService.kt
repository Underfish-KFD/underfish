package ru.underfish.app.service

import org.springframework.stereotype.Service
import ru.underfish.app.database.dao.CommunityRepository
import ru.underfish.app.database.dao.TagRepository
import ru.underfish.app.dto.response.CommunityTagResponse
import ru.underfish.app.exception.BadRequestException
import ru.underfish.app.exception.NotFoundException

@Service
class CommunityTagService(
    private val communityRepository: CommunityRepository,
    private val tagRepository: TagRepository
) {
    fun addTagToCommunity(communityId: Long, tagId: Long): CommunityTagResponse {
        val community = communityRepository.findById(communityId).orElseThrow {
            NotFoundException("Community not found")
        }
        val tag = tagRepository.findById(tagId).orElseThrow {
            NotFoundException("Tag not found")
        }

        if (community.tags.any { it.id == tag.id }) {
            throw BadRequestException("Tag already added to community")
        }

        community.tags.add(tag)
        communityRepository.save(community)
        return CommunityTagResponse.fromIds(community.id, tag.id)
    }

    fun getCommunityTags(communityId: Long): List<CommunityTagResponse> {
        val community = communityRepository.findById(communityId).orElseThrow {
            NotFoundException("Community not found")
        }

        return community.tags.map { tag ->
            CommunityTagResponse.fromIds(community.id, tag.id)
        }
    }

    fun removeTagFromCommunity(communityId: Long, tagId: Long) {
        val community = communityRepository.findById(communityId).orElseThrow {
            NotFoundException("Community not found")
        }

        val removed = community.tags.removeIf { it.id == tagId }
        if (!removed) {
            throw NotFoundException("Tag not found in community")
        }

        communityRepository.save(community)
    }
}

