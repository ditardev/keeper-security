package com.micro.security.controller

import com.micro.security.model.dto.AuthDto
import com.micro.security.service.AuthService
import lombok.AllArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@AllArgsConstructor
@RequestMapping("\${server.endpoint.main}")
class AuthController(
    private val authService: AuthService,
) {
    @PostMapping("/signIn")
    fun signIn(@RequestBody request: AuthDto): ResponseEntity<*>? {
        return authService.signIn(request);
    }
    @PostMapping("/signUp")
    fun signUp(@RequestBody request: AuthDto): ResponseEntity<*>? {
        return authService.signUp(request);
    }
    @PostMapping("/signOut")
    fun signOut(@RequestBody request: AuthDto): ResponseEntity<*>? {
        return authService.signUp(request);
    }
    @PostMapping("/refresh")
    fun refresh(@RequestBody request: AuthDto): ResponseEntity<*>? {
        return authService.refresh(request);
    }
}