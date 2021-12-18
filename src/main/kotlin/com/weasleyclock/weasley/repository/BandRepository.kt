package com.weasleyclock.weasley.repository

import com.weasleyclock.weasley.domain.Band
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BandRepository : JpaRepository<Band, Long> {
}
