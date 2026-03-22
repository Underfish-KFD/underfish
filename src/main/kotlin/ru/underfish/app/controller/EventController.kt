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
import ru.underfish.app.dto.request.EventRequest
import ru.underfish.app.dto.request.EventTagRequest
import ru.underfish.app.dto.response.EventResponse
import ru.underfish.app.dto.response.EventTagResponse
import ru.underfish.app.service.EventService
import ru.underfish.app.service.UserService

@RestController
@RequestMapping("/api/v1/events")
class EventController(
    private val eventService: EventService,
    private val userService: UserService,
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createEvent(
        @RequestBody request: EventRequest,
        authentication: Authentication,
    ): EventResponse {
        val userId = userService.getUserIdByEmail(authentication.name)
        return eventService.createEvent(request, userId)
    }

    @GetMapping
    fun getEvents(): List<EventResponse> = eventService.getEvents()

    @GetMapping("/{event_id}")
    fun getEventById(
        @PathVariable("event_id") eventId: Long,
    ): EventResponse = eventService.getEventById(eventId)

    @PutMapping("/{event_id}")
    fun updateEvent(
        @PathVariable("event_id") eventId: Long,
        @RequestBody request: EventRequest,
    ): EventResponse = eventService.updateEvent(eventId, request)

    @DeleteMapping("/{event_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteEvent(
        @PathVariable("event_id") eventId: Long,
    ) {
        eventService.deleteEvent(eventId)
    }

    @PostMapping("/{event_id}/tags")
    @ResponseStatus(HttpStatus.CREATED)
    fun addTagToEvent(
        @PathVariable("event_id") eventId: Long,
        @RequestBody request: EventTagRequest,
    ): EventTagResponse = eventService.addTagToEvent(eventId, request)

    @GetMapping("/{event_id}/tags")
    fun getEventTags(
        @PathVariable("event_id") eventId: Long,
    ): List<EventTagResponse> = eventService.getEventTags(eventId)

    @DeleteMapping("/{event_id}/tags/{tag_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun removeTagFromEvent(
        @PathVariable("event_id") eventId: Long,
        @PathVariable("tag_id") tagId: Long,
    ) {
        eventService.removeTagFromEvent(eventId, tagId)
    }
}
