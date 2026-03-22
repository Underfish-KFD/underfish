package ru.underfish.app.exception

// Исключение для случаев, когда пользователь не авторизован
class UnauthorizedException(message: String) : RuntimeException(message)

