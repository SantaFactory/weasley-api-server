package com.weasleyclock.weasleyclient.repository.querydsl

import com.weasleyclock.weasleyclient.dto.BandDTO

interface BandQuerydsl {
    fun selectBandOne(id: Long): BandDTO.Grouping?
}
