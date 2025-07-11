package com.micro.security.model.entity

import com.micro.security.model.Role
import com.micro.security.model.Status
import jakarta.persistence.*
import java.sql.Timestamp
import java.util.*

@Entity
@Table(name = "users")
data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var uuid: UUID? = UUID.randomUUID(),

    var username: String? = null,
    var firstname: String? = "",
    var lastname: String? = "",
    var email: String? = null,
    var password: String? = null,

    @Enumerated(EnumType.STRING)
    var role: Role? = null,

    @Enumerated(EnumType.STRING)
    var status: Status? = null,

    var created: Timestamp? = null,
    var updated: Timestamp? = null,
)