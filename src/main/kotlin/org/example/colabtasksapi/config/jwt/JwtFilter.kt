package org.example.colabtasksapi.config.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

/**
 * Filter that processes JWT authentication for incoming HTTP requests.
 *
 * @property jwtUtil Utility class for handling JWT operations.
 * @property userDetailsService Service for loading user-specific data.
 */
@Component
class JwtFilter(
    private val jwtUtil: JwtUtil,
    private val userDetailsService: UserDetailsService
) : OncePerRequestFilter() {

    /**
     * Filters each request to check for a valid JWT token and sets the authentication context.
     *
     * @param request The HTTP request.
     * @param response The HTTP response.
     * @param filterChain The filter chain.
     */
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val requestURI = request.requestURI
        if (requestURI == "/api/auth" || requestURI == "/signup") {
            filterChain.doFilter(request, response)
            return
        }

        val authorization = request.getHeader("Authorization")

        if (authorization != null && authorization.startsWith("Bearer ")) {
            val jwt = authorization.substring(7)

            if (jwtUtil.isValid(jwt)) {
                val email = jwtUtil.getEmail(jwt)

                if (email != null) {
                    val userDetails = userDetailsService.loadUserByUsername(email)

                    val authentication = UsernamePasswordAuthenticationToken(
                        userDetails, null, emptyList()
                    )

                    SecurityContextHolder.getContext().authentication = authentication
                }
            }
        }

        filterChain.doFilter(request, response)
    }
}