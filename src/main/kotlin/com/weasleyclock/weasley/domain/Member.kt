package com.weasleyclock.weasley.domain

import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import javax.persistence.*

@Table
@Entity
@DynamicInsert
@DynamicUpdate
class Member : BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(name = "member_title", nullable = false)
    var title: String? = null

    @JoinColumn(name = "member_id")
    @OneToMany(cascade = [CascadeType.ALL])
    var memberUserSet: Set<MemberUser> = HashSet();

    @JoinColumn(name = "menber_id")
    @OneToMany(cascade = [CascadeType.ALL])
    var weasleyItemSet: Set<WeasleyItem> = HashSet()

    constructor(title: String) {
        this.title = title
    }

    constructor(title: String, memberUserSet: Set<MemberUser>) {
        this.title = title
        this.memberUserSet = memberUserSet
    }

    constructor(id: Long, title: String, memberUserSet: Set<MemberUser>) {
        this.id = id
        this.title = title
        this.memberUserSet = memberUserSet
    }

    constructor(id: Long, title: String, memberUserSet: Set<MemberUser>, weasleyItemSet: Set<WeasleyItem>) {
        this.id = id
        this.title = title
        this.memberUserSet = memberUserSet
        this.weasleyItemSet = weasleyItemSet
    }

    constructor()

}
