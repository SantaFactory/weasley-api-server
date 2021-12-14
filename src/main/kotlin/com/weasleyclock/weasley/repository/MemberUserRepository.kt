package com.weasleyclock.weasley.repository

import com.weasleyclock.weasley.domain.MemberUser
import com.weasleyclock.weasley.domain.embedd.MemberUserKey
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberUserRepository : JpaRepository<MemberUser, MemberUserKey> {
}
