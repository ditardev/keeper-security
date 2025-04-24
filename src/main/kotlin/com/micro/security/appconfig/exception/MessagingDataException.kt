package com.micro.security.appconfig.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
data class MessagingDataException (
    val msg: String?,
    val httpStatus: HttpStatus = HttpStatus.BAD_REQUEST
) : RuntimeException(msg)