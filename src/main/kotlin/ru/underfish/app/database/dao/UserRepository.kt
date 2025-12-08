package ru.underfish.app.database.dao

import ru.underfish.app.database.entities.User

interface UserRepository : AbstractRepository<User> {
    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): User?
    fun findUserById(id: Long): User?
}
