package com.micro.security.appconfig.security

import com.micro.security.appconfig.exception.InvalidJwtException
import com.micro.security.model.entity.UserEntity
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import java.util.*
import javax.crypto.spec.SecretKeySpec

@Component
class TokenProvider(
    @Value("\${server.jwt.secret}")
    private val secret: String? = null,

    @Value("\${server.jwt.header}")
    private val header: String? = null,

    @Value("\${server.jwt.expiration}")
    private val expiration: Long
) {

    private val signingKey: SecretKeySpec
        get() {
            val keyBytes: ByteArray = Base64.getDecoder().decode(secret)
            return SecretKeySpec(keyBytes, 0, keyBytes.size, "HmacSHA256")
        }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parser()
            .verifyWith(signingKey)
            .build()
            .parseSignedClaims(token)
            .payload;
    }

    private fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims = extractAllClaims(token)
        return claimsResolver(claims)
    }

    fun extractEmail(token: String): String {
        return extractClaim(token) { claims -> claims.subject }
    }

    private fun generateTokenFromClaims(
        claims: Claims,
        tokenValidity: Long
    ): String {
        val now = Date()
        return Jwts.builder()
            .claims(claims)
            .issuedAt(now)
            .expiration(Date(now.time + tokenValidity))
            .signWith(signingKey)
            .compact()
    }

    fun generateToken(
        userEntity: UserEntity
    ): String {
        val claims = Jwts.claims().subject(userEntity.email)
        claims.apply {
            add("uuid", userEntity.uuid)
            add("role", userEntity.role)
            add("status", userEntity.status)
            add("email", userEntity.email)
            add("username", userEntity.username)
            add("firstname", userEntity.firstname)
            add("lastname", userEntity.lastname)
        }
        return generateTokenFromClaims(claims.build(), expiration)
    }

    fun generateRefreshToken(claims: Claims): String {
        return generateTokenFromClaims(claims, expiration)
    }

    fun validateToken(token: String): Boolean {
        try {
            Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .payload
            return true
        } catch (e: MalformedJwtException) {
            throw InvalidJwtException("JWT token is malformed.")
        } catch (e: ExpiredJwtException) {
            throw InvalidJwtException("JWT token is expired.")
        } catch (e: Exception) {
            throw InvalidJwtException("JWT token validation failed.")
        }
    }

    fun getTokenFromHeader(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader(header)
        return if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7, bearerToken.length)
        } else {
            null
        }
    }

    fun getClaimsFromToken(token: String): Claims? {
        return Jwts.parser()
            .verifyWith(signingKey)
            .build()
            .parseSignedClaims(token)
            ?.payload
    }
}