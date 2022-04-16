package com.weasleyclock.weasleyclient.repository

import com.weasleyclock.weasleyclient.domain.Weasley
import com.weasleyclock.weasleyclient.domain.embedd.WeasleyKey
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface WeasleyRepository : JpaRepository<Weasley, WeasleyKey> {

    @Transactional
    @EntityGraph(attributePaths = ["band"])
    fun findByUser_Id(userId: Long): List<Weasley>

}
