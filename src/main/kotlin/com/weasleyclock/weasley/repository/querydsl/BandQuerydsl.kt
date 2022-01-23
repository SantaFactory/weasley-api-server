package com.weasleyclock.weasley.repository.querydsl

import com.weasleyclock.weasley.dto.BandDTO

interface BandQuerydsl {
    fun selectBandOne(id: Long): BandDTO.Grouping?
}
