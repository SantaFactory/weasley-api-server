package com.weasleyclock.weasley.repository

import com.weasleyclock.weasley.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {

}
