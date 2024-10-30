package org.example.colabtasksapi.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
class CorsConfig {
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