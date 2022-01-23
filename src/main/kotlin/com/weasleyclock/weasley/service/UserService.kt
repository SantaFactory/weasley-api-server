package com.weasleyclock.weasley.service

import com.weasleyclock.weasley.domain.Auth
import com.weasleyclock.weasley.domain.User
import com.weasleyclock.weasley.enmus.AppRole
import com.weasleyclock.weasley.enmus.UserType
import com.weasleyclock.weasley.repository.UserRepository
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

        val userName = authInfo["email"] as String

        val name = authInfo["name"] as String

        val sub = authInfo["sub"] as String

        val newEntity = User(userName, name, UserType.GOOGLE, sub, linkedSetOf(Auth(AppRole.USER.value!!)))

        repository.save(newEntity)

        return newEntity
    }

    @Transactional(readOnly = true)
    fun getByLoginUser(email: String): Optional<User> = repository.findByEmail(email)

}
