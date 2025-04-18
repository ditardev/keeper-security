package com.micro.security.appconfig.security

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher
import java.time.LocalDateTime


@Configuration
@EnableWebSecurity
open class SecurityConfig(
    private val securityFilter: SecurityFilter,
    private val exceptionHandlerFilter: ExceptionHandlerFilter,
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain? {
        http.csrf { it.disable() }
            .requiresChannel { c -> c.requestMatchers("/actuator/**").requiresInsecure() }
            .authorizeHttpRequests {
                it
                    .requestMatchers(antMatcher("/**")).permitAll()
                    .requestMatchers(antMatcher("/api/auth/**")).permitAll()
                    .requestMatchers(antMatcher("/api/auth/user/**")).hasRole("USER")
                    .requestMatchers(antMatcher("/api/auth/admin/**")).hasRole("ADMIN")
                    .anyRequest().authenticated()
            }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
        http
            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(exceptionHandlerFilter, SecurityFilter::class.java)
            .exceptionHandling { configurer ->
                configurer.authenticationEntryPoint { request, response, authException ->
                    val jsonResponse = mapOf(
                        "status" to false,
                        "message" to "Access denied",
                        "timestamp" to LocalDateTime.now().toString()
                    )

                    response.contentType = MediaType.APPLICATION_JSON_VALUE
                    response.status = HttpServletResponse.SC_UNAUTHORIZED
                    response.writer.write(
                        ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(jsonResponse)
                    )
                }
            }
        return http.build();
    }

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager? {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder? {
        return BCryptPasswordEncoder()
    }
}