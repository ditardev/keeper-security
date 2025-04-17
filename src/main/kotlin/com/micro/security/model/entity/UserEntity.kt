package com.micro.security.model.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.micro.security.model.Role
import com.micro.security.model.Status
import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.sql.Timestamp
import java.util.*

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "users")
class UserEntity :UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    var uuid: UUID? = UUID.randomUUID()

    var username: String? = null
    var firstname: String? = null
    var lastname: String? = null
    var email: String? = null
    var password: String? = null

    @Enumerated(EnumType.STRING)
    var role: Role? = null

    @Enumerated(EnumType.STRING)
    var status: Status? = null

    var created: Timestamp? = null
    var updated: Timestamp? = null


    override fun getAuthorities() = listOf(GrantedAuthority { role!!.name })

    override fun getPassword() = this.password

    override fun getUsername() = this.username

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true
}