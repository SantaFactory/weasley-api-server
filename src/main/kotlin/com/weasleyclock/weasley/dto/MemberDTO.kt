package com.weasleyclock.weasley.dto

import com.weasleyclock.weasley.domain.Member
import com.weasleyclock.weasley.domain.MemberUser
import com.weasleyclock.weasley.domain.WeasleyItem
import org.springframework.util.CollectionUtils

class MemberDTO {

    data class Created(
        val title: String,
        val userId: Long,
        val weasleyTitleSet: Set<String>
    ) {

        fun toEntity(): Member {
            return Member(title)
        }

        fun getByWeasleyItemSet(member: Member): Set<WeasleyItem> {

            if (CollectionUtils.isEmpty(weasleyTitleSet)) return HashSet()

            return this.weasleyTitleSet
                .map { title -> WeasleyItem(title, member) }
                .toSet()
        }

    }

    data class Updated(val title: String, val memberUserSet: Set<MemberUser>, val weasleyItemSet: Set<WeasleyItem>)

}
