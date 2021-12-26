package com.weasleyclock.weasley.repository

import com.weasleyclock.weasley.domain.Band
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BandRepository : JpaRepository<Band, Long> {

    @EntityGraph(attributePaths = ["bandUserSet"])
    fun <T> findBy(type: Class<T>): List<T>

    @EntityGraph(attributePaths = ["bandUserSet", "bandUserSet.user"])
    fun <T> findByBandUserSet_User_Id(userId: Long, type: Class<T>): List<T>

    @EntityGraph(attributePaths = ["bandUserSet", "bandUserSet.user", "bandWeasleySet"])
    fun <T> findById(id: Long, type: Class<T>): T?

    @EntityGraph(attributePaths = ["bandWeasleySet", "bandWeasleySet.user"])
    fun <T> findBandById(id: Long, type: Class<T>): T?

}
