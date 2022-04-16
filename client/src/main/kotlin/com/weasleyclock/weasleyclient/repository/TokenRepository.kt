package com.weasleyclock.weasleyclient.repository

import com.weasleyclock.weasleyclient.domain.Token
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TokenRepository : JpaRepository<Token, String> {

    fun findByToken(token: String): Optional<Token>

    fun findByUserId(userId: Long): Optional<Token>

}
