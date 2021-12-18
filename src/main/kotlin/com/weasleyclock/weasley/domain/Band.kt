package com.weasleyclock.weasley.domain

import javax.persistence.*

@Table
@Entity
class Band : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(name = "title", nullable = false)
    var title: String? = null

}
