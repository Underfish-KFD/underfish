package ru.underfish.app.database.dao

import ru.underfish.app.database.entities.Review

interface ReviewRepository : AbstractRepository<Review> {
    fun findByEventId(eventId: Long): List<Review>

    fun existsByEventIdAndUserId(
        eventId: Long,
        userId: Long,
    ): Boolean
}
