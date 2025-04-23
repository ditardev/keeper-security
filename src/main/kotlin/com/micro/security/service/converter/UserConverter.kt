package com.micro.security.service.converter

import com.micro.security.model.dto.UserDto
import com.micro.security.model.entity.UserEntity
import org.springframework.stereotype.Component

@Component
class UserConverter {

    fun convertToDto(entity: UserEntity) : UserDto{
        return UserDto(
            uuid = entity.uuid.toString(),
            username = entity.username,
            firstname = entity.firstname,
            lastname = entity.lastname,
            email = entity.email,
            role = entity.role,
            status = entity.status
        )
    }

}