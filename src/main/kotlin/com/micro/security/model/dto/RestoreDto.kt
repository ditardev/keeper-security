package com.micro.security.model.dto

data class RestoreDto(
    val email: String,
    val code: String
) {}