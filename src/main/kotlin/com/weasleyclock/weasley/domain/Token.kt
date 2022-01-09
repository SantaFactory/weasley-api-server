package com.weasleyclock.weasley.domain

import javax.persistence.*

@Table
@Entity
class Token {

    @Id
    var userId: Long? = null

    @Column(name = "token_value" , columnDefinition="TEXT")
    var token: String? = null

    constructor()

    constructor(userId: Long, token: String) {
        this.userId = userId
        this.token = token
    }

}
