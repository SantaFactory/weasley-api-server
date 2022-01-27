package com.weasleyclock.weasleyclient.repository

import com.weasleyclock.weasleyclient.domain.Band
import com.weasleyclock.weasleyclient.repository.querydsl.BandQuerydsl
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BandRepository : JpaRepository<Band, Long> , BandQuerydsl {

    @EntityGraph(attributePaths = ["memberSet"])
    fun <T> findBy(type: Class<T>): List<T>

    @EntityGraph(attributePaths = ["memberSet", "memberSet.user"])
    fun <T> findByMemberSet_User_Id(userId: Long, type: Class<T>): List<T>

    @EntityGraph(attributePaths = ["memberSet", "memberSet.user", "weasleySet"])
    fun <T> findById(id: Long, type: Class<T>): T?

    @EntityGraph(attributePaths = ["weasleySet", "weasleySet.user"])
    fun <T> findBandById(id: Long, type: Class<T>): T?

}
