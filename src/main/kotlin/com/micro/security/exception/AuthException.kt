package com.micro.security.exception

import lombok.Getter
import org.springframework.http.HttpStatus

@Getter
class AuthException : RuntimeException {
    override val message: String
    val status: HttpStatus
    override var cause: Exception? = null

    constructor(message: String, status: HttpStatus) : super() {
        this.status = status
        this.message = message
    }

    constructor(message: String, cause: Exception?, status: HttpStatus) : super() {
        this.cause = cause
        this.status = status
        this.message = message
    }

    constructor(cause: Exception?, status: HttpStatus) : super() {
        this.status = status
        this.cause = cause
        this.message = ""
    }
}