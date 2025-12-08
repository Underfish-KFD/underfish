package ru.underfish.app.database.entities


import jakarta.persistence.*
import java.time.LocalDateTime
import lombok.Data


@Entity
@Data
@Table(name = "users")
data class User(

    @Column(name = "email", nullable = false, unique = true, length = 255) var email: String,

    @Column(name = "password_hash", nullable = false, length = 255) var passwordHash: String
) : AbstractEntity() {

    @Column(name = "phone", unique = true, length = 20)
    var phone: String? = null

    @Column(name = "first_name", nullable = true, length = 100)
    var firstName: String? = null

    @Column(name = "last_name", nullable = true, length = 100)
    var lastName: String? = null

    @Column(name = "avatar_url", columnDefinition = "TEXT")
    var avatarUrl: String? = null


    @Column(name = "last_login")
    var lastLogin: LocalDateTime? = null

    @Column(name = "is_banned")
    var isBanned: Boolean = false
}