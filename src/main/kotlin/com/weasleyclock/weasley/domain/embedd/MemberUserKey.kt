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

    @Column(name = "member_role_id")
    var memberRoleId: Long? = null

    constructor()

    constructor(userId: Long, memberRoleId: Long) {
        this.userId = userId
        this.memberRoleId = memberRoleId
    }

    constructor(userId: Long, memberId: Long, memberRoleId: Long) {
        this.userId = userId
        this.memberId = memberId
        this.memberRoleId = memberRoleId
    }

}
