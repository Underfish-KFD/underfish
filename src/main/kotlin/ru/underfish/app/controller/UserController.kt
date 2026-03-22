package ru.underfish.app.controller

import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import ru.underfish.app.dto.request.UserLoginRequest
import ru.underfish.app.dto.request.UserRegistrationRequest
import ru.underfish.app.dto.request.UserUpdateRequest
import ru.underfish.app.dto.response.UserLoginResponse
import ru.underfish.app.dto.response.UserResponse
import ru.underfish.app.service.UserService

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService,
) {
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    fun registerUser(
        @RequestBody request: UserRegistrationRequest,
    ): UserResponse = userService.registerUser(request)

    @PostMapping("/login")
    fun loginUser(
        @RequestBody request: UserLoginRequest,
    ): UserLoginResponse = userService.loginUser(request)

    @GetMapping("/{user_id}")
    @PreAuthorize("hasRole('ADMIN')")
    fun getUserById(
        @PathVariable("user_id") userId: Long,
    ): UserResponse = userService.getUserById(userId)

    @PutMapping("/{user_id}")
    @PreAuthorize("hasRole('ADMIN')")
    fun updateUser(
        @PathVariable("user_id") userId: Long,
        @RequestBody request: UserUpdateRequest,
    ): UserResponse = userService.updateUser(userId, request)

    @DeleteMapping("/{user_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    fun deleteUser(
        @PathVariable("user_id") userId: Long,
    ) {
        userService.deleteUser(userId)
    }
}
