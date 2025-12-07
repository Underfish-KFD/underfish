package ru.underfish.app.service

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.underfish.app.database.dao.UserRepository
import ru.underfish.app.database.entities.enums.Role
import ru.underfish.app.dto.request.UserLoginRequest
import ru.underfish.app.dto.request.UserRegistrationRequest
import ru.underfish.app.dto.response.UserLoginResponse
import ru.underfish.app.dto.response.UserResponse
import ru.underfish.app.exception.BadRequestException
import ru.underfish.app.exception.NotFoundException
import ru.underfish.app.exception.UnauthorizedException
import ru.underfish.app.security.JwtTokenUtil

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenUtil: JwtTokenUtil
) {

    fun registerUser(request: UserRegistrationRequest): UserResponse {
        if (userRepository.existsByEmail(request.email)) {
            throw BadRequestException("User with this email already exists")
        }

        val user = ru.underfish.app.database.entities.User(
            email = request.email,
            passwordHash = passwordEncoder.encode(request.password),
            firstName = request.firstName,
            lastName = request.lastName,
            phone = request.phone,
            role = Role.USER,
        )

        val savedUser = userRepository.save(user)
        return UserResponse.fromEntity(savedUser)
    }

    fun loginUser(request: UserLoginRequest): UserLoginResponse {
        val user = userRepository.findByEmail(request.email)
            ?: throw UnauthorizedException("Invalid email or password")

        if (!passwordEncoder.matches(request.password, user.passwordHash)) {
            throw UnauthorizedException("Invalid email or password")
        }

        val token = jwtTokenUtil.generateToken(user.id, user.email, user.role)
        return UserLoginResponse(token = token)
    }

    fun getUserById(userId: Long): UserResponse {
        val user = userRepository.findUserById(userId)
            ?: throw NotFoundException("User not found")
        return UserResponse.fromEntity(user)
    }
}
