package ru.underfish.app.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.io.Decoders
import org.springframework.stereotype.Component
import ru.underfish.app.config.JwtConfig
import java.util.Date

@Component
class JwtTokenUtil(private val jwtConfig: JwtConfig) {

    // Создаём ключ из секретной строки
    private val secretKey by lazy {
        Keys.hmacShaKeyFor(jwtConfig.secret.toByteArray(Charsets.UTF_8))
    }

    // Генерация токена
    fun generateToken(userId: Long, email: String): String {
        return Jwts.builder()
            .subject(email)
            .claim("userId", userId)
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + jwtConfig.expiration))
            .signWith(secretKey)
            .compact()
    }

    // Извлечение claims из токена
    fun getClaims(token: String): Claims {
        return Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .payload
    }

    // Проверка токена на валидность
    fun validateToken(token: String): Boolean {
        return try {
            val claims = getClaims(token)
            !claims.expiration.before(Date())
        } catch (e: Exception) {
            false
        }
    }

    // Извлечение email из токена
    fun getEmailFromToken(token: String): String {
        return getClaims(token).subject
    }

    // Извлечение userId из токена
    fun getUserIdFromToken(token: String): Long {
        return getClaims(token).get("userId", Long::class.java)
    }
}
