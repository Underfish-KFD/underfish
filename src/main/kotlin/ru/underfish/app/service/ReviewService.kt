package ru.underfish.app.service

import org.springframework.stereotype.Service
import ru.underfish.app.database.dao.EventRepository
import ru.underfish.app.database.dao.ReviewRepository
import ru.underfish.app.database.dao.UserRepository
import ru.underfish.app.database.entities.Review
import ru.underfish.app.dto.request.ReviewRequest
import ru.underfish.app.dto.response.ReviewResponse
import ru.underfish.app.exception.BadRequestException
import ru.underfish.app.exception.NotFoundException

@Service
class ReviewService(
	private val reviewRepository: ReviewRepository,
	private val userRepository: UserRepository,
	private val eventRepository: EventRepository
) {
	fun createReview(eventId: Long, userId: Long, request: ReviewRequest): ReviewResponse {
		if (request.rating !in 1..5) {
			throw BadRequestException("Rating must be in range from 1 to 5")
		}

		if (reviewRepository.existsByEventIdAndUserId(eventId, userId)) {
			throw BadRequestException("Review already exists for this user and event")
		}

		val user = userRepository.findUserById(userId) ?: throw NotFoundException("User not found")
		val event = eventRepository.findById(eventId).orElseThrow {
			NotFoundException("Event not found")
		}

		val review = Review(user = user, event = event).apply {
			rating = request.rating
			comment = request.comment
		}

		return ReviewResponse.fromEntity(reviewRepository.save(review))
	}

	fun getEventReviews(eventId: Long): List<ReviewResponse> {
		eventRepository.findById(eventId).orElseThrow {
			NotFoundException("Event not found")
		}

		return reviewRepository.findByEventId(eventId)
			.map(ReviewResponse::fromEntity)
	}

	fun deleteReview(reviewId: Long) {
		val review = reviewRepository.findById(reviewId).orElseThrow {
			NotFoundException("Review not found")
		}
		reviewRepository.delete(review)
	}
}

