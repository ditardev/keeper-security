package com.micro.security.exception

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class AuthExceptionHandler : ResponseEntityExceptionHandler() {

    val log: Logger = LoggerFactory.getLogger(AuthException::class.java)

    @ExceptionHandler(value = [AuthException::class])
    protected fun handleConflict(ex: AuthException, request: WebRequest?): ResponseEntity<Any>? {
        log.warn(ex.message)
        log.warn(ex.cause.toString())
        log.warn(ex.localizedMessage)
        val headers = HttpHeaders()
        headers.clearContentHeaders()
        return handleExceptionInternal(ex, ex.message, headers, ex.status, request!!)
    }

    @ExceptionHandler(value = [JwtAuthException::class])
    protected fun handleConflict(ex: JwtAuthException, request: WebRequest?): ResponseEntity<Any>? {
        log.warn(ex.message)
        log.warn(ex.cause.toString())
        log.warn(ex.localizedMessage)
        val headers = HttpHeaders()
        headers.clearContentHeaders()
        return handleExceptionInternal(ex, ex.message, headers, ex.httpStatus!!, request!!)
    }

    @ExceptionHandler(value = [RuntimeException::class])
    protected fun handleConflict(re: RuntimeException, request: WebRequest?): ResponseEntity<Any>? {
        log.warn(re.message)
        log.warn(re.cause.toString())
        log.warn(re.localizedMessage)
        val headers = HttpHeaders()
        headers.clearContentHeaders()
        return handleExceptionInternal(
            re, re.message, headers, HttpStatus.CONFLICT,
            request!!
        )
    }
}