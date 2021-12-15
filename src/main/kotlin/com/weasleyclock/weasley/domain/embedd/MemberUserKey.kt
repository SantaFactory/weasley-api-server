package com.weasleyclock.weasley.domain.embedd

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class MemberUserKey : Serializable {

    @Transient
    private val serialVersionUID = 1L

    @Column(name = "user_id")
    var userId: Long? = null

    @Column(name = "member_id")
    var memberId: Long? = null

    constructor()

    constructor(userId: Long) {
        this.userId = userId
    }

    constructor(userId: Long, memberId: Long) {
        this.userId = userId
        this.memberId = memberId
    }


}
