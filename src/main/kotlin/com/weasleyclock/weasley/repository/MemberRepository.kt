package com.weasleyclock.weasley.repository

import com.weasleyclock.weasley.domain.Member
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository : JpaRepository<Member, Long> {
    @EntityGraph(attributePaths = ["memberUserSet.user"])
    fun <T> findById(id: Long, type: Class<T>): T
}
