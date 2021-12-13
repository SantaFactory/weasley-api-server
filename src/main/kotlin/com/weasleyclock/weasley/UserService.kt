package com.weasleyclock.weasley

import com.weasleyclock.weasley.domain.User
import com.weasleyclock.weasley.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService(private val repository: UserRepository) {

    @Transactional(readOnly = true)
    fun getByUsers(): List<User> {
        return repository.findAll()
    }

}
