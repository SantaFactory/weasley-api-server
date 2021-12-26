package com.weasleyclock.weasley.domain.embedd

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class MemberKey : Serializable {

    @Transient
    private val serialVersionUID = 1L

    @Column
    var userId: Long? = null

    @Column
    var bandId: Long? = null

    constructor()

    constructor(userId: Long) {
        this.userId = userId
    }

    constructor(userId: Long?, bandId: Long?) {
        this.userId = userId
        this.bandId = bandId
    }

}
