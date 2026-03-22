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
import ru.underfish.app.dto.request.CommunityOrganizerRequest
import ru.underfish.app.dto.response.CommunityOrganizerResponse
import ru.underfish.app.exception.BadRequestException
import ru.underfish.app.service.CommunityOrganizerService

@RestController
@RequestMapping("/api/v1/communities/{community_id}/organizers")
class CommunityOrganizerController(
    private val communityOrganizerService: CommunityOrganizerService,
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addOrganizer(
        @PathVariable("community_id") communityId: Long,
        @RequestBody request: CommunityOrganizerRequest,
    ): CommunityOrganizerResponse {
        val userId = request.userId.toLongOrNull() ?: throw BadRequestException("Invalid user id")
        return communityOrganizerService.addOrganizer(communityId, userId)
    }

    @GetMapping
    fun getOrganizers(
        @PathVariable("community_id") communityId: Long,
    ): List<CommunityOrganizerResponse> = communityOrganizerService.getOrganizersByCommunityId(communityId)

    @DeleteMapping("/{user_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun removeOrganizer(
        @PathVariable("community_id") communityId: Long,
        @PathVariable("user_id") userId: Long,
    ) {
        communityOrganizerService.removeOrganizer(communityId, userId)
    }
}
