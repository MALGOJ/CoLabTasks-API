package org.example.colabtasksapi.config

import org.example.colabtasksapi.config.jwt.JwtFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

/**
 * Configuration class for setting up security configurations including JWT filters.
 *
 * @property jwtFilter The JWT filter to be used for authentication.
 */
@Configuration
class SecurityConfig(
    private val jwtFilter: JwtFilter
) {

    /**
     * Configures the security filter chain.
     *
     * @param httpSecurity The HttpSecurity object to configure.
     * @return The configured SecurityFilterChain.
     */
    @Bean
    fun filterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        httpSecurity.csrf { csrf -> csrf.disable() }
            .cors { }
            .sessionManagement { sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authorizeHttpRequests { authz ->
                authz
                    .requestMatchers("/api/auth/**", "/api/signup/**").permitAll()
                    .requestMatchers("/api/tasks/**").authenticated()
                    .requestMatchers("/api/projects/**").authenticated()
                    .anyRequest().authenticated()
            }
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)

        return httpSecurity.build()
    }

    /**
     * Provides the authentication manager bean.
     *
     * @param authenticationConfiguration The authentication configuration.
     * @return The AuthenticationManager bean.
     */
    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    /**
     * Provides the password encoder bean.
     *
     * @return The PasswordEncoder bean.
     */
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}