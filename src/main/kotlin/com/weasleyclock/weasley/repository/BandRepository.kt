package com.weasleyclock.weasley.repository

import com.weasleyclock.weasley.domain.Band
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BandRepository : JpaRepository<Band, Long> {

    @EntityGraph(attributePaths = ["memberSet"])
    fun <T> findBy(type: Class<T>): List<T>

    @EntityGraph(attributePaths = ["memberSet", "memberSet.user"])
    fun <T> findByMemberSet_User_Id(userId: Long, type: Class<T>): List<T>

    @EntityGraph(attributePaths = ["memberSet", "memberSet.user", "bandWeasleySet"])
    fun <T> findById(id: Long, type: Class<T>): T?

    @EntityGraph(attributePaths = ["bandWeasleySet", "bandWeasleySet.user"])
    fun <T> findBandById(id: Long, type: Class<T>): T?

}
