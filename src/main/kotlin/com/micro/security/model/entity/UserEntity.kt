package com.micro.security.model.entity

import com.micro.security.model.Role
import com.micro.security.model.Status
import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor
import java.sql.Timestamp
import java.util.*

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "users")
//class UserEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    var id: Long? = null
//
//    var uuid: UUID? = UUID.randomUUID()
//
//    var username: String? = null
//    var firstname: String? = null
//    var lastname: String? = null
//    var email: String? = null
//    var password: String? = null
//
//    @Enumerated(EnumType.STRING)
//    var role: Role? = null
//
//    @Enumerated(EnumType.STRING)
//    var status: Status? = null
//
//    var created: Timestamp? = null
//    var updated: Timestamp? = null
//
//}

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