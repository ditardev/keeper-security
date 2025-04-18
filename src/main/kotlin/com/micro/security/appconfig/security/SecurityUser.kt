package com.micro.security.appconfig.security

import com.micro.security.model.Role
import com.micro.security.model.Status
import com.micro.security.model.entity.UserEntity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails

class SecurityUser(
    private var username: String = "",
    private var password: String = "",
    private val isActive: Boolean,
    private val role: Role
) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority(this.role.name))
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return username
    }

    override fun isAccountNonExpired(): Boolean {
        return isActive
    }

    override fun isAccountNonLocked(): Boolean {
        return isActive
    }

    override fun isCredentialsNonExpired(): Boolean {
        return isActive
    }

    override fun isEnabled(): Boolean {
        return isActive
    }

    companion object {
        fun fromUserEntity(userEntity: UserEntity): UserDetails {
            return User(
                userEntity.email,
                userEntity.password,
                userEntity.status!! == Status.ACTIVE,
                userEntity.status!! == Status.ACTIVE,
                userEntity.status!! == Status.ACTIVE,
                userEntity.status!! == Status.ACTIVE,
                listOf(GrantedAuthority { userEntity.role!!.name })
            )
        }
    }
}