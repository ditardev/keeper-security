package com.micro.security.controller

import com.micro.security.appconfig.model.ApiResponse
import com.micro.security.model.dto.SignInDto
import com.micro.security.model.dto.SignUpDto
import com.micro.security.model.dto.UserDto
import com.micro.security.service.AuthService
import io.jsonwebtoken.Claims
import jakarta.servlet.http.HttpServletRequest
import lombok.AllArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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

    @PutMapping("/signOut")
    fun signOut(@RequestBody request: UserDto): ResponseEntity<*>? {
        authService.signOut(request);
        return ResponseEntity.ok(ApiResponse.Success(true, ""))
    }

    @GetMapping("/refresh")
    fun refresh(request: HttpServletRequest): ResponseEntity<ApiResponse.Success<Map<String, String>>> {
        val claims = request.getAttribute("claims") as Claims
        val token = authService.getRefreshToken(claims = claims)
        return ResponseEntity.ok(ApiResponse.Success(true, mapOf("token" to token)))
    }
}