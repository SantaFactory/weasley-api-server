package com.weasleyclock.weasleyclient.repository

import com.weasleyclock.weasleyclient.domain.Member
import com.weasleyclock.weasleyclient.domain.embedd.MemberKey
import com.weasleyclock.weasleyclient.enmus.RoleName
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository : JpaRepository<Member, MemberKey> {

    override fun <S : Member?> save(entity: S): S

    fun findByBand_Id(bandId: Long): List<Member>?

    fun findByBand_IdAndUser_IdAndBandRole_Title(bandId: Long, userId: Long, bandRoleTitle: RoleName): Member?
}
