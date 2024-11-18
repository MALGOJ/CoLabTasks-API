package org.example.colabtasksapi.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

/**
 * Configuration class for setting up CORS (Cross-Origin Resource Sharing) settings.
 */
@Configuration
class CorsConfig {

    /**
     * Creates and configures a CORS configuration source.
     *
     * @return The configured CORS configuration source.
     */
    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val corsConfiguration = CorsConfiguration()
        corsConfiguration.setAllowedOriginPatterns(listOf("*"))
        corsConfiguration.setAllowedMethods(listOf("GET", "POST", "PUT", "DELETE"))
        corsConfiguration.setAllowedHeaders(listOf("Authorization", "Content-Type"))
        corsConfiguration.setAllowCredentials(true)

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", corsConfiguration)
        return source
    }
}