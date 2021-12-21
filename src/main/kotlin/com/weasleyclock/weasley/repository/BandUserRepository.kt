package com.weasleyclock.weasley.repository

import com.weasleyclock.weasley.domain.BandUser
import com.weasleyclock.weasley.domain.embedd.BandUserKey
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface BandUserRepository : JpaRepository<BandUser, BandUserKey> {

    @Transactional
    override fun <S : BandUser?> save(entity: S): S

    @Transactional
    @EntityGraph(attributePaths = ["band", "user"])
    fun findByBand_IdAndUser_IdAndBandRole_Title(bandId: Long, userId: Long, title: String): BandUser

}
