package com.micro.security.appconfig.security

import com.hadiyarajesh.spring_security_demo.app.exception.ResourceNotFoundException
import com.micro.security.model.entity.UserEntity
import com.micro.security.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class AuthDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(email: String): UserDetails {
        val userEntity: UserEntity = userRepository.findUserByUsername(email)
            ?: throw ResourceNotFoundException("User with username $email not found")
        return SecurityUser.fromUserEntity(userEntity)
    }
}