package ru.underfish.app.dto.request

data class UserRegistrationRequest(
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val phone: String?
)
