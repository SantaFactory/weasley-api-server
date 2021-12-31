package com.weasleyclock.weasley.repository

import com.weasleyclock.weasley.domain.Token
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
interface TokenRepository : JpaRepository<Token, String> {

    @Transactional(readOnly = true)
    fun findByToken(token: String): Optional<Token>

    @Transactional(readOnly = true)
    fun findByUserId(userId: Long): Optional<Token>

}
