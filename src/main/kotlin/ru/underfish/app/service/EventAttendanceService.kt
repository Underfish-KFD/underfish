package ru.underfish.app.service

import org.springframework.stereotype.Service
import ru.underfish.app.database.dao.EventAttendanceRepository
import ru.underfish.app.database.dao.EventRepository
import ru.underfish.app.database.dao.UserRepository
import ru.underfish.app.database.entities.EventAttendance
import ru.underfish.app.database.entities.enums.AttendanceStatus
import ru.underfish.app.dto.response.EventAttendanceResponse
import ru.underfish.app.exception.BadRequestException
import ru.underfish.app.exception.NotFoundException

@Service
class EventAttendanceService(
    private val eventAttendanceRepository: EventAttendanceRepository,
    private val eventRepository: EventRepository,
    private val userRepository: UserRepository
) {
    fun addAttendance(eventId: Long, userId: Long, status: String): EventAttendanceResponse {
        val alreadyExists = eventAttendanceRepository.findAll()
            .any { it.event.id == eventId && it.user.id == userId }
        if (alreadyExists) {
            throw BadRequestException("Attendance already exists")
        }

        val event = eventRepository.findById(eventId).orElseThrow {
            NotFoundException("Event not found")
        }
        val user = userRepository.findUserById(userId) ?: throw NotFoundException("User not found")

        val attendance = EventAttendance(user = user, event = event).apply {
            this.status = parseAttendanceStatus(status)
        }

        return EventAttendanceResponse.fromEntity(eventAttendanceRepository.save(attendance))
    }

    fun getEventAttendances(eventId: Long): List<EventAttendanceResponse> {
        eventRepository.findById(eventId).orElseThrow {
            NotFoundException("Event not found")
        }

        return eventAttendanceRepository.findAll()
            .filter { it.event.id == eventId }
            .map(EventAttendanceResponse::fromEntity)
    }

    fun updateAttendance(eventId: Long, userId: Long, status: String): EventAttendanceResponse {
        val attendance = eventAttendanceRepository.findAll()
            .firstOrNull { it.event.id == eventId && it.user.id == userId }
            ?: throw NotFoundException("Attendance not found")

        attendance.status = parseAttendanceStatus(status)
        return EventAttendanceResponse.fromEntity(eventAttendanceRepository.save(attendance))
    }

    fun removeAttendance(eventId: Long, userId: Long) {
        val attendance = eventAttendanceRepository.findAll()
            .firstOrNull { it.event.id == eventId && it.user.id == userId }
            ?: throw NotFoundException("Attendance not found")
        eventAttendanceRepository.delete(attendance)
    }

    private fun parseAttendanceStatus(status: String): AttendanceStatus {
        return AttendanceStatus.entries.firstOrNull { it.name.equals(status, ignoreCase = true) }
            ?: throw BadRequestException("Invalid attendance status")
    }
}
