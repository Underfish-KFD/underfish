package ru.underfish.app.database.dao

import ru.underfish.app.database.entities.EventAttendance

interface EventAttendanceRepository : AbstractRepository<EventAttendance> {
    fun findByEventId(eventId: Long): List<EventAttendance>
    fun findByEventIdAndUserId(eventId: Long, userId: Long): EventAttendance?
    fun existsByEventIdAndUserId(eventId: Long, userId: Long): Boolean
    fun deleteByEventIdAndUserId(eventId: Long, userId: Long): Long
}
