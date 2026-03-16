package ru.underfish.app.controller

import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import ru.underfish.app.dto.request.CommunityCreateRequest
import ru.underfish.app.dto.response.CommunityResponse
import ru.underfish.app.service.CommunityService
import ru.underfish.app.service.UserService

@RestController
@RequestMapping("/api/v1/communities")
class CommunityController(
    private val communityService: CommunityService,
    private val userService: UserService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createCommunity(
        @RequestBody request: CommunityCreateRequest,
        authentication: Authentication
    ): CommunityResponse {
        val organizerId = userService.getUserIdByEmail(authentication.name)
        return communityService.createCommunity(request, organizerId)
    }

    @GetMapping
    fun getCommunities(): List<CommunityResponse> {
        return communityService.getCommunities()
    }

    @GetMapping("/{community_id}")
    fun getCommunityById(@PathVariable("community_id") communityId: Long): CommunityResponse {
        return communityService.getCommunityById(communityId)
    }

    @PutMapping("/{community_id}")
    fun updateCommunity(
        @PathVariable("community_id") communityId: Long,
        @RequestBody request: CommunityCreateRequest
    ): CommunityResponse {
        return communityService.updateCommunity(communityId, request)
    }

    @DeleteMapping("/{community_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCommunity(@PathVariable("community_id") communityId: Long) {
        communityService.deleteCommunity(communityId)
    }
}