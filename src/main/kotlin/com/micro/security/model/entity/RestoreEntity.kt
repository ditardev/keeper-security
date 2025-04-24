package com.micro.security.model.entity

import jakarta.persistence.*
import java.sql.Timestamp
import java.util.*

@Entity
@Table(name = "recovery")
data class RestoreEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var uuid: UUID? = null,

    var email: String? = null,
    var code: String? = null,
    var created: Timestamp? = null,
    var expired: Timestamp? = null,
)