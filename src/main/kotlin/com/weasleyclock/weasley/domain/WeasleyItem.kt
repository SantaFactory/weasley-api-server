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

}
