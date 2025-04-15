package com.micro.security.repository

import com.micro.security.model.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findUserByUsername(name: String?): UserEntity?
}