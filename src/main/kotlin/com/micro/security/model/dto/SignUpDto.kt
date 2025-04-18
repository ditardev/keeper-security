package com.micro.security.model.dto

data class SignUpDto(
    var username: String,
    var firstName: String,
    var lastName: String,
    var email: String,
    var password: String,
)