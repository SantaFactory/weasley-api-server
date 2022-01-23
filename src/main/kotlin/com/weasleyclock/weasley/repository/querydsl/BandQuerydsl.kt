package com.weasleyclock.weasley.repository.querydsl

import com.weasleyclock.weasley.dto.projection.IBand

interface BandQuerydsl {
    fun selectBandOne(id: Long): IBand
}
