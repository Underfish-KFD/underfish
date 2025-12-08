package ru.underfish.app.exception

// Исключение для случаев, когда данные некорректны
class BadRequestException(message: String) : RuntimeException(message)

