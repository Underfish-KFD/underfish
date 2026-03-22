package ru.underfish.app.service

import org.springframework.stereotype.Service
import ru.underfish.app.database.dao.EventRepository
import ru.underfish.app.database.dao.LocationRepository
import ru.underfish.app.database.dao.TagRepository
import ru.underfish.app.database.dao.UserRepository
import ru.underfish.app.database.entities.Event
import ru.underfish.app.database.entities.enums.EventStatus
import ru.underfish.app.dto.request.EventRequest
import ru.underfish.app.dto.request.EventTagRequest
import ru.underfish.app.dto.response.EventResponse
import ru.underfish.app.dto.response.EventTagResponse
import ru.underfish.app.exception.BadRequestException
import ru.underfish.app.exception.NotFoundException
import java.math.BigDecimal
import java.time.LocalDateTime

@Service
class EventService(
    private val eventRepository: EventRepository,
    private val userRepository: UserRepository,
    private val locationRepository: LocationRepository,
    private val tagRepository: TagRepository,
) {
    fun createEvent(
        request: EventRequest,
        userId: Long,
    ): EventResponse {
        val user = userRepository.findUserById(userId) ?: throw NotFoundException("User not found")
        val locationId = request.locationId.toLongOrNull() ?: throw BadRequestException("Invalid location id")
        val location =
            locationRepository.findById(locationId).orElseThrow {
                NotFoundException("Location not found")
            }

        val event =
            Event(user = user, title = request.title).apply {
                description = request.description
                startDatetime = request.startDatetime
                endDatetime = request.endDatetime
                this.location = location
                price = request.price?.let { BigDecimal.valueOf(it) } ?: BigDecimal.ZERO
                currency = request.currency ?: "RUB"
                posterUrl = request.posterUrl
                eventStatus = parseEventStatus(request.status)
                updatedAt = LocalDateTime.now()
                maxParticipants = request.maxParticipants ?: 0
                isOnline = request.isOnline ?: false
            }

        return EventResponse.fromEntity(eventRepository.save(event))
    }

    fun getEvents(): List<EventResponse> = eventRepository.findAll().map(EventResponse::fromEntity)

    fun getEventById(eventId: Long): EventResponse {
        val event =
            eventRepository.findById(eventId).orElseThrow {
                NotFoundException("Event not found")
            }
        return EventResponse.fromEntity(event)
    }

    fun updateEvent(
        eventId: Long,
        request: EventRequest,
    ): EventResponse {
        val event =
            eventRepository.findById(eventId).orElseThrow {
                NotFoundException("Event not found")
            }
        val locationId = request.locationId.toLongOrNull() ?: throw BadRequestException("Invalid location id")
        val location =
            locationRepository.findById(locationId).orElseThrow {
                NotFoundException("Location not found")
            }

        event.title = request.title
        event.description = request.description
        event.startDatetime = request.startDatetime
        event.endDatetime = request.endDatetime
        event.location = location
        event.price = request.price?.let { BigDecimal.valueOf(it) } ?: BigDecimal.ZERO
        event.currency = request.currency ?: "RUB"
        event.posterUrl = request.posterUrl
        event.eventStatus = parseEventStatus(request.status)
        event.maxParticipants = request.maxParticipants ?: 0
        event.isOnline = request.isOnline ?: false
        event.updatedAt = LocalDateTime.now()

        return EventResponse.fromEntity(eventRepository.save(event))
    }

    fun deleteEvent(eventId: Long) {
        val event =
            eventRepository.findById(eventId).orElseThrow {
                NotFoundException("Event not found")
            }
        eventRepository.delete(event)
    }

    fun addTagToEvent(
        eventId: Long,
        request: EventTagRequest,
    ): EventTagResponse {
        val event =
            eventRepository.findById(eventId).orElseThrow {
                NotFoundException("Event not found")
            }
        val tagId = request.tagId.toLongOrNull() ?: throw BadRequestException("Invalid tag id")
        val tag =
            tagRepository.findById(tagId).orElseThrow {
                NotFoundException("Tag not found")
            }
        event.tags.add(tag)
        eventRepository.save(event)
        return EventTagResponse(eventId = eventId.toString(), tagId = tagId.toString())
    }

    fun removeTagFromEvent(
        eventId: Long,
        tagId: Long,
    ) {
        val event =
            eventRepository.findById(eventId).orElseThrow {
                NotFoundException("Event not found")
            }
        val tag =
            tagRepository.findById(tagId).orElseThrow {
                NotFoundException("Tag not found")
            }
        event.tags.remove(tag)
        eventRepository.save(event)
    }

    fun getEventTags(eventId: Long): List<EventTagResponse> {
        val event =
            eventRepository.findById(eventId).orElseThrow {
                NotFoundException("Event not found")
            }
        return event.tags.map { tag ->
            EventTagResponse(eventId = eventId.toString(), tagId = tag.id.toString())
        }
    }

    private fun parseEventStatus(status: String): EventStatus =
        EventStatus.entries.firstOrNull { it.name.equals(status, ignoreCase = true) }
            ?: throw BadRequestException("Invalid event status")
}
