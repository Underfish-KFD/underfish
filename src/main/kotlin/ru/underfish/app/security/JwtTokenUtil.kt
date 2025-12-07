package ru.underfish.app.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import ru.underfish.app.config.JwtConfig
import ru.underfish.app.database.entities.enums.Role
import java.util.Date

@Component
class JwtTokenUtil(private val jwtConfig: JwtConfig) {

    // Создаём ключ из секретной строки
    private val secretKey by lazy {
        Keys.hmacShaKeyFor(jwtConfig.secret.toByteArray(Charsets.UTF_8))
    }

    // Генерация токена
    fun generateToken(userId: Long, email: String, role: Role): String {
        return Jwts.builder()
            .subject(email)
            .claim("userId", userId)
            .claim("role", role.name)
            .issuedAt(Date(System.currentTimeMillis()))
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
        val userId = getClaims(token).get("userId", Number::class.java)
        return userId.toLong()  // ✅ Number.toLong() работает всегда
    }

    // Извлечение роли из токена
    fun getRoleFromToken(token: String): Role {
        val roleName = getClaims(token).get("role", String::class.java)
        println(roleName)
        return Role.valueOf(roleName)
    }
}
