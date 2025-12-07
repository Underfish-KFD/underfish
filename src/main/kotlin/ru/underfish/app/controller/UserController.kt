package ru.underfish.app.controller

import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import ru.underfish.app.dto.request.UserLoginRequest
import ru.underfish.app.dto.request.UserRegistrationRequest
import ru.underfish.app.dto.response.UserLoginResponse
import ru.underfish.app.dto.response.UserResponse
import ru.underfish.app.service.UserService

@RestController
@RequestMapping("/api/v1/users")
class UserController(private val userService: UserService) {

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    fun registerUser(@RequestBody request: UserRegistrationRequest): UserResponse {
        return userService.registerUser(request)
    }

    @PostMapping("/login")
    fun loginUser(@RequestBody request: UserLoginRequest): UserLoginResponse {
        return userService.loginUser(request)
    }
    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")  // Только ADMIN может вызывать этот эндпоинт
    fun getUserById(@PathVariable userId: Long): UserResponse {
        return userService.getUserById(userId)
    }
}
