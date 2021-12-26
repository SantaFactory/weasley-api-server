package com.weasleyclock.weasley.repository

import com.weasleyclock.weasley.domain.Member
import com.weasleyclock.weasley.domain.embedd.MemberKey
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface MemberRepository : JpaRepository<Member, MemberKey> {

    @Transactional
    override fun <S : Member?> save(entity: S): S

    @Transactional
    @EntityGraph(attributePaths = ["band", "user"])
    fun findByBand_IdAndUser_IdAndBandRole_Title(bandId: Long, userId: Long, title: String): Member?

}
