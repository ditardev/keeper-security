package com.micro.security.appconfig.exception

import com.hadiyarajesh.spring_security_demo.app.exception.ResourceNotFoundException
import com.micro.security.appconfig.model.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFoundException(e: ResourceNotFoundException): ResponseEntity<ApiResponse.Error> {
        return ResponseEntity(
            ApiResponse.Error(status = false, message = e.message),
            e.httpStatus
        )
    }

    @ExceptionHandler(ResourceNotConfirmedException::class)
    fun handleResourceNotConfirmedException(e: ResourceNotConfirmedException): ResponseEntity<ApiResponse.Error> {
        return ResponseEntity(
            ApiResponse.Error(status = false, message = e.message),
            e.httpStatus
        )
    }

    @ExceptionHandler(ResoucePasswordIdentical::class)
    fun handleResoucePasswordIdentical(e: ResoucePasswordIdentical): ResponseEntity<ApiResponse.Error> {
        return ResponseEntity(
            ApiResponse.Error(status = false, message = e.message),
            e.httpStatus
        )
    }

    @ExceptionHandler(ResourceAlreadyExistException::class)
    fun handleResourceAlreadyExistException(e: ResourceAlreadyExistException): ResponseEntity<ApiResponse.Error> {
        return ResponseEntity(
            ApiResponse.Error(status = false, message = e.message),
            e.httpStatus
        )
    }

    @ExceptionHandler(InvalidJwtException::class)
    fun handleInvalidJwtException(e: InvalidJwtException): ResponseEntity<ApiResponse.Error> {
        return ResponseEntity(
            ApiResponse.Error(status = false, message = e.message),
            e.httpStatus
        )
    }

    @ExceptionHandler(MessagingDataException::class)
    fun handleInvalidJwtException(e: MessagingDataException): ResponseEntity<ApiResponse.Error> {
        return ResponseEntity(
            ApiResponse.Error(status = false, message = e.message),
            e.httpStatus
        )
    }

}