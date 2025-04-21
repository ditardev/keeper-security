package com.micro.security.service

import com.hadiyarajesh.spring_security_demo.app.exception.ResourceNotFoundException
import com.micro.security.appconfig.exception.ResourceAlreadyExistException
import com.micro.security.appconfig.exception.ResourceBannedException
import com.micro.security.appconfig.exception.ResourceNotConfirmedException
import com.micro.security.appconfig.security.TokenProvider
import com.micro.security.model.Role
import com.micro.security.model.Status
import com.micro.security.model.dto.AuthDto
import com.micro.security.model.dto.SignInDto
import com.micro.security.model.dto.SignUpDto
import com.micro.security.model.entity.UserEntity
import com.micro.security.repository.UserRepository
import io.jsonwebtoken.Claims
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.sql.Timestamp

@Service
class AuthService(
    private val authenticationManager: AuthenticationManager,
    private val userRepository: UserRepository,
    private val tokenProvider: TokenProvider,
    private val passwordEncoder: PasswordEncoder,
) {
    fun signIn(signInDto: SignInDto?): String {

        val userEntity = userRepository.findUserByUsername(signInDto?.username)
            ?: throw ResourceNotFoundException("User with username ${signInDto?.username} not found")

        when (userEntity.status) {
            Status.BANNED -> throw ResourceBannedException("User with username ${signInDto?.username} was blocked")
            Status.NEW -> throw ResourceNotConfirmedException("User with username ${signInDto?.username} not Confirmed")
            else -> {}
        }

        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                signInDto?.username,
                signInDto?.password
            )
        )
        SecurityContextHolder.getContext().authentication = authentication

        return getToken(userEntity)
    }


    fun signUp(signUpDto: SignUpDto?): ResponseEntity<*>? {
        if (userRepository.existsByUsername(signUpDto?.username)) {
            throw ResourceAlreadyExistException("Email ${signUpDto?.email} is already in use, please use different email.")
        }

        userRepository.save(
            UserEntity(
                username = signUpDto?.username,
                email = signUpDto?.email,
                password = passwordEncoder.encode(signUpDto?.password),
                role = Role.USER,
                status = Status.ACTIVE,
                created = Timestamp(System.currentTimeMillis()),
                updated = Timestamp(System.currentTimeMillis())
            )
        )

        return ResponseEntity.ok(HttpStatus.OK)
    }

    fun signOut(authDto: AuthDto?): ResponseEntity<*>? {

        val userEntity = userRepository.findUserByUsername(authDto?.username)
            ?: throw ResourceNotFoundException("Username ${authDto?.username} not found")

        userRepository.delete(userEntity)

        return ResponseEntity.ok(HttpStatus.OK)
    }

    fun getToken(userEntity: UserEntity): String {
        return tokenProvider.generateToken(userEntity)
    }

    fun getRefreshToken(claims: Claims): String {
        return tokenProvider.generateRefreshToken(claims)
    }

}