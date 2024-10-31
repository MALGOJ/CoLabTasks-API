package org.example.colabtasksapi.config.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import org.springframework.stereotype.Component
import java.util.Date
import java.util.concurrent.TimeUnit

@Component
class JwtUtil {
    private val secretKey = "chbFeiFHWAKq2PDvL346MhRHTWUNesN7lCh9qTWpmUxAV3wPSzhitWg38fD6j4LtJE50zoCeLBxwG2TXsCIYr1h6mUzIqFJg9ZYaOota1Wlz1tIu5Sk8AKiHXoEQhDvBB66Jpx0JdE3QLqZm4BFV9AfgMA0bF687TsHPTBd5Uudn5Nv3BcSzLuGwKlvi9PSAmh4Uxcll"
    private val algorithm = Algorithm.HMAC256(secretKey)

    fun create(email: String): String {
        return JWT.create()
            .withSubject(email)
            .withIssuer("CoLabTasks-API")
            .withIssuedAt(Date())
            .withExpiresAt(Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(8)))
            .sign(algorithm)
    }

    fun isValid(jwt: String): Boolean {
        return try {
            JWT.require(algorithm)
                .build()
                .verify(jwt)
            true
        } catch (e: JWTVerificationException) {
            false
        }
    }
    fun getEmail(jwt: String): String? {
        return try {
            val decodedJWT = JWT.require(algorithm)
                .build()
                .verify(jwt)
            decodedJWT.subject
        } catch (e: JWTVerificationException) {
            null
        }
    }

}