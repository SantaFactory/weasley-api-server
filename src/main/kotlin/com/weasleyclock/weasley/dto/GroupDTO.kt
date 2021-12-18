package com.weasleyclock.weasley.dto

import com.weasleyclock.weasley.domain.Band
import com.weasleyclock.weasley.domain.BandUser

class GroupDTO {

    data class Created(
        val title: String,
    ) {
        fun toEntity(): Band {
//            return Group(title)
            return Band()
        }
    }

    data class Updated(val title: String, val bandUserSet: Set<BandUser>)

    data class User(val id: Long, val email: String, val role: String)

//    interface OnlyGroupUserSet {
//        fun getMemberUserSet(): Set<GroupUser>
//    }

    data class Defualt(val id: Long, val title: String)

}
