package com.micro.security.model

import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.function.Function
import java.util.stream.Collectors

enum class Role {
    ADMIN, USER;
}