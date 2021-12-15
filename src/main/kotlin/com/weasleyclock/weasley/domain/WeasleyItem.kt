package com.weasleyclock.weasley.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Table
@Entity
class WeasleyItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false)
    var title: String? = null

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "menber_id", nullable = false)
     var member: Member? = null

    constructor()

    constructor(title: String, member: Member) {
        this.title = title
        this.member = member
    }

}
