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
class UserDto {

    private val uuid: String? = null
    private val username: String? = null
    private val firstname: String? = null
    private val lastname: String? = null
    private val email: String? = null
    private val role: Role? = null
    private val status: Status? = null

}