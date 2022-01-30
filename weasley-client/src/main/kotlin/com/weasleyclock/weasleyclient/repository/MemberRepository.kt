package com.weasleyclock.weasleyclient.repository

import com.weasleyclock.weasleyclient.domain.Member
import com.weasleyclock.weasleyclient.domain.embedd.MemberKey
import com.weasleyclock.weasleyclient.enmus.RoleName
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

    fun findByBand_Id(bandId: Long): List<Member>?

    fun findByBand_IdAndUser_IdAndBandRole_Title(bandId: Long, userId: Long, bandRoleTitle: RoleName): Member?

}
