package ru.underfish.app.entities


import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*


@Entity
@Table(name = "Users")
data class User(
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    val userId: UUID? = null,

    @Column(name = "email", nullable = false, unique = true, length = 255)
    var email: String,

    @Column(name = "phone", unique = true, length = 20)
    var phone: String? = null,

    @Column(name = "password_hash", nullable = false, length = 255)
    var passwordHash: String,

    @Column(name = "first_name", nullable = false, length = 100)
    var firstName: String,

    @Column(name = "last_name", nullable = false, length = 100)
    var lastName: String,

    @Column(name = "avatar_url", columnDefinition = "TEXT")
    var avatarUrl: String? = null,

    @Column(name = "registration_date")
    val registrationDate: LocalDateTime = LocalDateTime.now(),

    @Column(name = "last_login")
    var lastLogin: LocalDateTime? = null,

    @Column(name = "is_banned")
    var isBanned: Boolean = false
)
