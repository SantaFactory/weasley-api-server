package com.weasleyclock.weasley.repository

import com.weasleyclock.weasley.domain.BandWeasley
import com.weasleyclock.weasley.domain.embedd.BandWeasleyKey
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface BandWeasleyRepository : JpaRepository<BandWeasley, BandWeasleyKey> {

    @Transactional
    @EntityGraph(attributePaths = ["band"])
    fun findByUser_Id(userId: Long): List<BandWeasley>

}
