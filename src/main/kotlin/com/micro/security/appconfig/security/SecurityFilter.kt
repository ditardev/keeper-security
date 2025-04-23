package com.micro.security.appconfig.security

import com.micro.security.appconfig.utility.Constant
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class SecurityFilter(
    private val tokenProvider: TokenProvider,
    private val authDetailsService: AuthDetailsService
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = tokenProvider.getTokenFromHeader(request)
        if (token != null && tokenProvider.validateToken(token)) {
            val email = tokenProvider.extractEmail(token)
            val userDetails = authDetailsService.loadUserByUsername(email)
            val authentication =
                UsernamePasswordAuthenticationToken(userDetails.username, null, userDetails.authorities)
            SecurityContextHolder.getContext().authentication = authentication
        }

        if (request.requestURI.equals("/refresh")) {
            request.getAttribute(Constant.CLAIMS)
                ?: request.setAttribute(
                    Constant.CLAIMS,
                    tokenProvider.getClaimsFromToken(token!!)
                )
        }

        filterChain.doFilter(request, response)
    }
}