package com.weasleyclock.weasley.repository.querydsl

import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import com.weasleyclock.weasley.domain.Band
import com.weasleyclock.weasley.domain.QBand.band
import com.weasleyclock.weasley.dto.projection.IBand
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository


@Repository
class BandQuerydslImpl(private val jpaQueryFactory: JPAQueryFactory) : QuerydslRepositorySupport(Band::class.java),
    BandQuerydsl {

    override fun selectBandOne(id: Long): IBand {
        return jpaQueryFactory
            .select(Projections.bean(IBand::class.java, band.id, band.title))
            .from(band)
            .where(band.id.eq(id))
            .fetchOne() as IBand
    }

}

