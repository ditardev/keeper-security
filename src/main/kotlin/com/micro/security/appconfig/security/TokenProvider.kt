package com.micro.security.appconfig.security

import com.micro.security.appconfig.exception.InvalidJwtException
import com.micro.security.appconfig.utility.Constant
import com.micro.security.appconfig.utility.Messages
import com.micro.security.model.entity.UserEntity
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import java.util.*
import javax.crypto.SecretKey

@Component
class TokenProvider(
    @Value("\${server.jwt.secret}")
    private val secret: String? = null,

    @Value("\${server.jwt.header}")
    private val header: String? = null,

    @Value("\${server.jwt.expiration_jwt}")
    private val expiration: Int
) {

    private fun getSignInKey(): SecretKey {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret))
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parser()
            .verifyWith(getSignInKey())
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
        return Jwts.builder()
            .claims(claims)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + expirationMiliseconds()))
            .signWith(getSignInKey())
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
        }
        return generateTokenFromClaims(claims.build(), expirationMiliseconds())
    }

    fun generateRefreshToken(claims: Claims): String {
        return generateTokenFromClaims(claims, expirationMiliseconds())
    }

    fun validateToken(token: String): Boolean {
        try {
            Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .payload
            return true
        } catch (e: MalformedJwtException) {
            throw InvalidJwtException(Messages.JWT_MALFORMED)
        } catch (e: ExpiredJwtException) {
            throw InvalidJwtException(Messages.JWT_EXPIRED)
        } catch (e: Exception) {
            throw InvalidJwtException(Messages.JWT_VALIDATION_FAILED)
        }
    }

    fun getTokenFromHeader(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader(header)
        return if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(Constant.BEARER)) {
            bearerToken.substring(7, bearerToken.length)
        } else {
            null
        }
    }

    fun getClaimsFromToken(token: String): Claims? {
        return Jwts.parser()
            .verifyWith(getSignInKey())
            .build()
            .parseSignedClaims(token)
            ?.payload
    }
    fun expirationMiliseconds(): Long {
        return expiration.toLong() * 60000
    }
}