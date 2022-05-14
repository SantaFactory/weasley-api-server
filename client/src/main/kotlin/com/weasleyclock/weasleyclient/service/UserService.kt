package com.weasleyclock.weasleyclient.service

import com.weasleyclock.weasleyclient.domain.User
import com.weasleyclock.weasleyclient.enmus.RoleType
import com.weasleyclock.weasleyclient.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(rollbackFor = [Exception::class])
class UserService(private val repository: UserRepository) {

    @Transactional(readOnly = true)
    fun getByUsers(): List<User> {
        return repository.findAll()
    }

    @Transactional
    fun createByNewUser(authInfo: MutableMap<*, *>): User? {

        val email = authInfo["email"] as String

        val name = authInfo["name"] as String

        val sub = authInfo["sub"] as String

        val newEntity =
            User(email, name, com.weasleyclock.weasleyclient.enmus.UserType.GOOGLE, sub, RoleType.ROLE_USER)

        repository.save(newEntity)

        return newEntity
    }

    @Transactional(readOnly = true)
    fun getByLoginUser(email: String): Optional<User> = repository.findByEmail(email)

}
