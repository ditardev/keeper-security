package com.micro.security.controller

import com.micro.security.appconfig.model.ApiResponse
import com.micro.security.model.dto.AuthDto
import com.micro.security.model.dto.SignInDto
import com.micro.security.model.dto.SignUpDto
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
    fun signIn(@RequestBody request: SignInDto): ResponseEntity<*>? {
        val data = authService.signIn(request);
        return ResponseEntity.ok(ApiResponse.Success(true, data))
    }

    @PostMapping("/signUp")
    fun signUp(@RequestBody request: SignUpDto): ResponseEntity<*>? {
        val data = authService.signUp(request);
        return ResponseEntity.ok(ApiResponse.Success(true, data))
    }

    @PostMapping("/signOut")
    fun signOut(@RequestBody request: AuthDto): ResponseEntity<*>? {
        return authService.signOut(request);
    }

    @PostMapping("/refresh")
    fun refresh(@RequestBody request: AuthDto): ResponseEntity<*>? {
        return authService.refresh(request);
    }
}