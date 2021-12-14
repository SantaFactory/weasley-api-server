package com.weasleyclock.weasley.repository

import com.weasleyclock.weasley.domain.WeasleyItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WeasleyItemRepository : JpaRepository<WeasleyItem, Long> {
}
