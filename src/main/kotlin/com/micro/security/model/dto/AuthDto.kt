package com.micro.security.model.dto

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor

@Data
@AllArgsConstructor
@NoArgsConstructor
class AuthDto {

    var username: String? = null
    var password: String? = null

}