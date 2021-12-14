package com.weasleyclock.weasley.domain

import javax.persistence.*

@Entity
@Table(name = "member_role")
class MemberRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id : Long? = null

    @Column(name = "title" , nullable = false)
    private var title : String? = null

}
