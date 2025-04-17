package com.micro.security.service

import com.micro.security.model.dto.AuthDto
import com.micro.security.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val authenticationManager: AuthenticationManager,
    private val userRepository: UserRepository,
) {
    fun signIn(authDto: AuthDto?): ResponseEntity<*>? {
        var userEntity =
            userRepository.findUserByUsername(authDto?.username) ?: throw UsernameNotFoundException("User not found")
        return ResponseEntity.ok(HttpStatus.OK)
    }


    fun signUp(authDto: AuthDto?): ResponseEntity<*>? {
        var userEntity =
            userRepository.findUserByUsername(authDto?.username) ?: throw UsernameNotFoundException("User not found")
        return ResponseEntity.ok(HttpStatus.OK)
    }

    fun signOut(authDto: AuthDto?): ResponseEntity<*>? {
        var userEntity =
            userRepository.findUserByUsername(authDto?.username) ?: throw UsernameNotFoundException("User not found")
        return ResponseEntity.ok(HttpStatus.OK)
    }

    fun refresh(authDto: AuthDto?): ResponseEntity<*>? {
        var userEntity =
            userRepository.findUserByUsername(authDto?.username) ?: throw UsernameNotFoundException("User not found")
        return ResponseEntity.ok(HttpStatus.OK)
    }

}