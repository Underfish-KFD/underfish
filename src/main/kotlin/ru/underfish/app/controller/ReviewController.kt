package ru.underfish.app.controller

import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import ru.underfish.app.dto.request.ReviewRequest
import ru.underfish.app.dto.response.ReviewResponse
import ru.underfish.app.service.ReviewService
import ru.underfish.app.service.UserService

@RestController
class ReviewController(
    private val reviewService: ReviewService,
    private val userService: UserService,
) {
    @PostMapping("/api/v1/events/{event_id}/reviews")
    @ResponseStatus(HttpStatus.CREATED)
    fun createReview(
        @PathVariable("event_id") eventId: Long,
        @RequestBody request: ReviewRequest,
        authentication: Authentication,
    ): ReviewResponse {
        val userId = userService.getUserIdByEmail(authentication.name)
        return reviewService.createReview(eventId, userId, request)
    }

    @GetMapping("/api/v1/events/{event_id}/reviews")
    fun getEventReviews(
        @PathVariable("event_id") eventId: Long,
    ): List<ReviewResponse> = reviewService.getEventReviews(eventId)

    @DeleteMapping("/api/v1/reviews/{review_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteReview(
        @PathVariable("review_id") reviewId: Long,
    ) {
        reviewService.deleteReview(reviewId)
    }
}
