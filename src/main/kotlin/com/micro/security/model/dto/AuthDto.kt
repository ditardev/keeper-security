package com.micro.security.model.dto

data class AuthDto(
    val token: String?=null,
    val user: UserDto?=null
) {
}