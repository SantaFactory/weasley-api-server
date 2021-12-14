package com.weasleyclock.weasley.domain

import com.weasleyclock.weasley.domain.embedd.MemberUserKey
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import javax.persistence.*

@Entity
//@DynamicInsert
//@DynamicUpdate
@Table(name = "member_user")
class MemberUser {

    @EmbeddedId
    var memberUserKey: MemberUserKey? = null

//    @OneToOne
//    @MapsId(value = "userId")
//    var user: User? = null
//
//    @OneToOne
//    @MapsId(value = "memberId")
//    var member: Member? = null
//
//    @OneToOne
//    @MapsId(value = "memberRoleId")
//    var memberRole: MemberRole? = null

    constructor()

    constructor(memberUserKey: MemberUserKey) {
        this.memberUserKey = memberUserKey
    }

}
