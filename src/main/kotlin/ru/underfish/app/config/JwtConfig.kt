package ru.underfish.app.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
data class JwtConfig(
    @Value("\${jwt.secret}") val secret: String,
    @Value("\${jwt.expiration}") val expiration: Long,
    @Value("\${jwt.header}") val header: String,
    @Value("\${jwt.prefix}") val prefix: String
)
