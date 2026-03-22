package ru.underfish.app.dto.response

import ru.underfish.app.database.entities.EventAttendance
import java.time.LocalDateTime

data class EventAttendanceResponse(
    val attendanceId: String,
    val userId: String,
    val eventId: String,
    val status: String,
    val createdAt: LocalDateTime
) {
    companion object {
        fun fromEntity(attendance: EventAttendance): EventAttendanceResponse {
            return EventAttendanceResponse(
                attendanceId = attendance.id.toString(),
                userId = attendance.user.id.toString(),
                eventId = attendance.event.id.toString(),
                status = attendance.status.name,
                createdAt = attendance.createdAt
            )
        }
    }
}

