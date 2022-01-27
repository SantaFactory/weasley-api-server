package com.weasleyclock.weasleyclient.repository.querydsl

import com.querydsl.jpa.impl.JPAQueryFactory
import com.weasleyclock.weasleyclient.domain.Band
import com.weasleyclock.weasleyclient.dto.BandDTO
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository


@Repository
class BandQuerydslImpl(private val jpaQueryFactory: JPAQueryFactory) : QuerydslRepositorySupport(Band::class.java),
    BandQuerydsl {

    override fun selectBandOne(id: Long): BandDTO.Grouping? {
        return null
//        val weasleyItemList: List<BandOneDTO> = jpaQueryFactory
//            .select(
//                Projections.constructor(
//                    BandOneDTO::class.java,
//                    band.id,
//                    band.title,
//                    weasley.id.userId.`as`("userId"),
//                    weasley.user.name.`as`("userName"),
//                    weasley.id.title.`as`("itemTitle"),
//                    weasley.latitude,
//                    weasley.longitude,
//                    weasley.isCurrent
//                )
//            )
//            .from(band)
//            .join(band.memberSet, member)
//            .join(weasley).on(member.user.id.eq(weasley.user.id))
//            .where(band.id.eq(id))
//            .fetch()
//
//        val result = weasleyItemList
//            .groupBy { it -> BandDTO.GroupingKey(it.id!!, it.title!!) }
//            .entries
//            .map { it -> BandDTO.Grouping(it.key.id, it.key.title).toCreateGrouping(it.value) }
//            .toList()
//
//        if (result.isEmpty()) {
//            return null
//        }
//
//        return result[0]
    }

}

