package ru.underfish.app.dto.request

data class ReviewRequest(
    val rating: Int,
    val comment: String,
)
