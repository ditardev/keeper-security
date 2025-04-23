package com.micro.security.appconfig.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.micro.security.appconfig.utility.Constant
import com.micro.security.appconfig.utility.Messages
import com.micro.security.model.Role
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
                    .requestMatchers(antMatcher("/api/auth/user/**")).hasRole(Role.USER.name)
                    .requestMatchers(antMatcher("/api/auth/admin/**")).hasRole(Role.ADMIN.name)
                    .anyRequest().authenticated()
            }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
        http
            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(exceptionHandlerFilter, SecurityFilter::class.java)
            .exceptionHandling { configurer ->
                configurer.authenticationEntryPoint { request, response, authException ->
                    val jsonResponse = mapOf(
                        Constant.STATUS to false,
                        Constant.MESSAGE to Messages.ACCESS_DENIED,
                        Constant.TIMESTAMP to LocalDateTime.now().toString()
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