package com.micro.security.model.dto

import com.micro.security.model.Role
import com.micro.security.model.Status
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
data class UserDto(
    val uuid: String? = null,
    val username: String? = null,
    val firstname: String? = null,
    val lastname: String? = null,
    val email: String? = null,
    val role: Role? = null,
    val status: Status? = null
) {}