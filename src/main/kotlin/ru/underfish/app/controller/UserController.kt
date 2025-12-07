package ru.underfish.app.controller

import org.springframework.http.HttpStatus
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
}
