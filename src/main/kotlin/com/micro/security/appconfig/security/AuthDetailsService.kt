package com.micro.security.appconfig.security

import com.hadiyarajesh.spring_security_demo.app.exception.ResourceNotFoundException
import com.micro.security.appconfig.utility.Messages
import com.micro.security.model.entity.UserEntity
import com.micro.security.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class AuthDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val userEntity: UserEntity = userRepository.findUserByUsername(username)
            ?: throw ResourceNotFoundException(Messages.USER_WITH_USERNAME + " \'" +username + "\' " + Messages.NOT_FOUND)
        return SecurityUser.fromUserEntity(userEntity)
    }
}