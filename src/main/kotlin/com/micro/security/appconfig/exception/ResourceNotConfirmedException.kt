package com.micro.security.appconfig.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.CONFLICT)
class ResourceNotConfirmedException (
    val msg: String?,
    val httpStatus: HttpStatus = HttpStatus.CONFLICT
) : RuntimeException(msg)