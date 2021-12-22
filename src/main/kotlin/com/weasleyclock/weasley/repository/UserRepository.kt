package com.weasleyclock.weasley.repository

import com.weasleyclock.weasley.domain.User
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, Long> {

    @EntityGraph(attributePaths = ["authSet"])
    fun findByEmail(email: String): Optional<User>

}
