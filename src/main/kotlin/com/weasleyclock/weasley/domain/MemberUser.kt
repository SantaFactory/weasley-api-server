package com.weasleyclock.weasley.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.weasleyclock.weasley.domain.embedd.MemberUserKey
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.jetbrains.annotations.NotNull
import javax.persistence.*

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "member_user")
class MemberUser {

    @EmbeddedId
    var memberUserKey: MemberUserKey? = null

    @Column(name = "member_role")
    var memberRole: String? = null

    @JsonIgnore
    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @MapsId(value = "userId")
    var user: User? = null

    @JsonIgnore
    @NotNull
    @ManyToOne
    @MapsId(value = "memberId")
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    var member: Member? = null

    constructor()

    constructor(memberUserKey: MemberUserKey) {
        this.memberUserKey = memberUserKey
    }

    constructor(user: User, member: Member, memberRole: String) {
        this.member = member
        this.user = user
        this.memberRole = memberRole
        this.memberUserKey = MemberUserKey(user.id!!, member.id!!)
    }

}
