package ru.underfish.app.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import ru.underfish.app.dto.request.CommunityTagRequest
import ru.underfish.app.dto.response.CommunityTagResponse
import ru.underfish.app.exception.BadRequestException
import ru.underfish.app.service.CommunityTagService

@RestController
@RequestMapping("/api/v1/communities/{community_id}/tags")
class CommunityTagController(
    private val communityTagService: CommunityTagService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addTag(
        @PathVariable("community_id") communityId: Long,
        @RequestBody request: CommunityTagRequest
    ): CommunityTagResponse {
        val tagId = request.tagId.toLongOrNull() ?: throw BadRequestException("Invalid tag id")
        return communityTagService.addTagToCommunity(communityId, tagId)
    }

    @GetMapping
    fun getTags(@PathVariable("community_id") communityId: Long): List<CommunityTagResponse> {
        return communityTagService.getCommunityTags(communityId)
    }

    @DeleteMapping("/{tag_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun removeTag(
        @PathVariable("community_id") communityId: Long,
        @PathVariable("tag_id") tagId: Long
    ) {
        communityTagService.removeTagFromCommunity(communityId, tagId)
    }
}

