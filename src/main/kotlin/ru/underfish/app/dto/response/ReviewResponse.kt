package ru.underfish.app.dto.response

import ru.underfish.app.database.entities.Review
import java.time.LocalDateTime

data class ReviewResponse(
    val reviewId: String,
    val userId: String,
    val eventId: String,
    val rating: Int?,
    val comment: String?,
    val createdAt: LocalDateTime
) {
    companion object {
        fun fromEntity(review: Review): ReviewResponse {
            return ReviewResponse(
                reviewId = review.id.toString(),
                userId = review.user.id.toString(),
                eventId = review.event.id.toString(),
                rating = review.rating,
                comment = review.comment,
                createdAt = review.createdAt
            )
        }
    }
}

