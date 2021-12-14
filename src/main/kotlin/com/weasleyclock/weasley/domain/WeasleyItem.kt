package com.weasleyclock.weasley.domain

import javax.persistence.*

@Table
@Entity
class WeasleyItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Long? = null

    @Column(nullable = false)
    private var title: String? = null

    @ManyToOne
    @JoinColumn(name = "menber_id", nullable = false)
    private var member: Member? = null

    constructor()

    constructor(title: String, member: Member) {
        this.title = title
        this.member = member
    }

}
