package com.micro.security.repository

import com.micro.security.model.entity.RestoreEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface RestoreRepository : JpaRepository<RestoreEntity, Long> {
    fun findRestoreEntityByUuid(uuid: UUID): RestoreEntity?
    fun existsByEmail(email: String?): Boolean
    fun findRestoreEntityByEmail(email: String?): RestoreEntity
}