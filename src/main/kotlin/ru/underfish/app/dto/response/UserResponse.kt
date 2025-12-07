package ru.underfish.app.dto.response

import ru.underfish.app.database.entities.User
import java.time.LocalDateTime

data class UserResponse(
    val userId: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val phone: String?,
    val avatarUrl: String?,
    val registrationDate: LocalDateTime,
    val lastLogin: LocalDateTime?
) {
    companion object {
        fun fromEntity(user: User): UserResponse {
            return UserResponse(
                userId = user.id.toString(),
                email = user.email,
                firstName = user.firstName ?: "",
                lastName = user.lastName ?: "",
                phone = user.phone,
                avatarUrl = user.avatarUrl,
                registrationDate = user.createdAt,
                lastLogin = user.lastLogin
            )
        }
    }
}
