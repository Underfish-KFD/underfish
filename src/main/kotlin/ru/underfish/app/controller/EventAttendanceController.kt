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
import ru.underfish.app.dto.request.EventAttendanceRequest
import ru.underfish.app.dto.request.EventAttendanceUpdateRequest
import ru.underfish.app.dto.response.EventAttendanceResponse
import ru.underfish.app.service.EventAttendanceService
import ru.underfish.app.service.UserService

@RestController
@RequestMapping("/api/v1/events/{event_id}/attendance")
class EventAttendanceController(
    private val eventAttendanceService: EventAttendanceService,
    private val userService: UserService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addAttendance(
        @PathVariable("event_id") eventId: Long,
        @RequestBody request: EventAttendanceRequest,
        authentication: Authentication
    ): EventAttendanceResponse {
        val userId = userService.getUserIdByEmail(authentication.name)
        return eventAttendanceService.addAttendance(eventId, userId, request.status)
    }

    @GetMapping
    fun getAttendances(@PathVariable("event_id") eventId: Long): List<EventAttendanceResponse> {
        return eventAttendanceService.getEventAttendances(eventId)
    }

    @PutMapping("/{user_id}")
    fun updateAttendance(
        @PathVariable("event_id") eventId: Long,
        @PathVariable("user_id") userId: Long,
        @RequestBody request: EventAttendanceUpdateRequest
    ): EventAttendanceResponse {
        return eventAttendanceService.updateAttendance(eventId, userId, request.status)
    }

    @DeleteMapping("/{user_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun removeAttendance(
        @PathVariable("event_id") eventId: Long,
        @PathVariable("user_id") userId: Long
    ) {
        eventAttendanceService.removeAttendance(eventId, userId)
    }
}
