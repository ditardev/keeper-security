package com.micro.security.controller

import com.micro.security.appconfig.model.ApiResponse
import com.micro.security.appconfig.utility.Constant
import com.micro.security.model.dto.RestoreDto
import com.micro.security.model.dto.SignInDto
import com.micro.security.model.dto.SignUpDto
import com.micro.security.model.dto.UserDto
import com.micro.security.service.AuthService
import com.micro.security.service.RestoreService
import io.jsonwebtoken.Claims
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("\${server.endpoint.main}")
class AuthController(
    private val authService: AuthService,
    private val restoreService: RestoreService,
) {
    @PostMapping("/signIn")
    fun signIn(@RequestBody request: SignInDto): ResponseEntity<*>? {
        val data = authService.signIn(request)
        return ResponseEntity.ok(ApiResponse.Success(true, data))
    }

    @PostMapping("/signUp")
    fun signUp(@RequestBody request: SignUpDto): ResponseEntity<*>? {
        val data = authService.signUp(request)
        return ResponseEntity.ok(ApiResponse.Success(true, data))
    }

    @PutMapping("/signOut")
    fun signOut(@RequestBody request: UserDto): ResponseEntity<*>? {
        authService.signOut(request)
        return ResponseEntity.ok(ApiResponse.Success(true, ""))
    }

    @PostMapping("/signRestore")
    fun signRestore(@RequestBody request: RestoreDto): ResponseEntity<*>? {
        val data = restoreService.signRestore(request)
        return ResponseEntity.ok(ApiResponse.Success(true, data))
    }

    @PostMapping("/verifyRestore")
    fun verifyRestore(@RequestBody request: RestoreDto): ResponseEntity<*>? {
        val data = restoreService.verifyRestoreCode(request)
        return ResponseEntity.ok(ApiResponse.Success(true, data))
    }

    @PostMapping("/passwordRestore")
    fun passwordRestore(@RequestBody request: RestoreDto): ResponseEntity<*>? {
        val data = restoreService.passwordRestore(request)
        return ResponseEntity.ok(ApiResponse.Success(true, data))
    }

    @GetMapping("/refresh")
    fun refresh(request: HttpServletRequest): ResponseEntity<ApiResponse.Success<Map<String, String>>> {
        val claims = request.getAttribute(Constant.CLAIMS) as Claims
        val token = authService.getRefreshToken(claims = claims)
        return ResponseEntity.ok(ApiResponse.Success(true, mapOf(Constant.TOKEN to token)))
    }
}