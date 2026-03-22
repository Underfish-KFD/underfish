package ru.underfish.app.exception

// Исключение для случаев, когда ресурс не найден
class NotFoundException(message: String) : RuntimeException(message)