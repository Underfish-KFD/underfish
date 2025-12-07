package ru.underfish.app.security

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.authority.SimpleGrantedAuthority
import ru.underfish.app.config.JwtConfig
import ru.underfish.app.exception.UnauthorizedException

@Component
class JwtAuthenticationFilter(
    private val jwtTokenUtil: JwtTokenUtil,
    private val jwtConfig: JwtConfig
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val header = request.getHeader(jwtConfig.header)

        if (header == null || !header.startsWith(jwtConfig.prefix)) {
            filterChain.doFilter(request, response)
            return
        }

        val token = header.replace(jwtConfig.prefix, "").trim()


        try {
            if (jwtTokenUtil.validateToken(token)) {
                val email = jwtTokenUtil.getEmailFromToken(token)
                val userId = jwtTokenUtil.getUserIdFromToken(token)
                val role = jwtTokenUtil.getRoleFromToken(token)


                val authorities = listOf(SimpleGrantedAuthority("ROLE_${role.name}"))
                val authentication = UsernamePasswordAuthenticationToken(
                    email, null, authorities
                )
                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authentication
            }else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token")
                return
            }
        } catch (e: Exception) {
            throw UnauthorizedException("Invalid JWT token: ${e.message}")
        }

        filterChain.doFilter(request, response)
    }
}
