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
import ru.underfish.app.dto.request.CommunityMemberRequest
import ru.underfish.app.dto.response.CommunityMemberResponse
import ru.underfish.app.exception.BadRequestException
import ru.underfish.app.service.CommunityMemberService

@RestController
@RequestMapping("/api/v1/communities/{community_id}/members")
class CommunityMemberController(
    private val communityMemberService: CommunityMemberService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addMember(
        @PathVariable("community_id") communityId: Long,
        @RequestBody request: CommunityMemberRequest
    ): CommunityMemberResponse {
        val userId = request.userId.toLongOrNull() ?: throw BadRequestException("Invalid user id")
        return communityMemberService.addMember(communityId, userId)
    }

    @GetMapping
    fun getMembers(@PathVariable("community_id") communityId: Long): List<CommunityMemberResponse> {
        return communityMemberService.getMembersByCommunityId(communityId)
    }

    @DeleteMapping("/{user_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun removeMember(
        @PathVariable("community_id") communityId: Long,
        @PathVariable("user_id") userId: Long
    ) {
        communityMemberService.removeMember(communityId, userId)
    }
}

