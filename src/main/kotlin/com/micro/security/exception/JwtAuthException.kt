package com.micro.security.exception

import lombok.Getter
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException

@Getter
class JwtAuthException : AuthenticationException {

    var httpStatus: HttpStatus? = null

    constructor(msg: String?) : super(msg)

    constructor(msg: String?, httpStatus: HttpStatus?) : super(msg) {
        this.httpStatus = httpStatus
    }
}