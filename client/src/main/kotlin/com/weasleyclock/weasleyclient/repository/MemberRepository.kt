package com.weasleyclock.weasleyclient.repository

import com.weasleyclock.weasleyclient.domain.Member
import com.weasleyclock.weasleyclient.domain.embedd.MemberKey
import com.weasleyclock.weasleyclient.enmus.BandRoleType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository : JpaRepository<Member, MemberKey> {

    override fun <S : Member?> save(entity: S): S

    fun findByBand_IdAndUser_IdAndRole(bandId: Long, userId: Long, bandRoleTitle: BandRoleType): Member?
}
