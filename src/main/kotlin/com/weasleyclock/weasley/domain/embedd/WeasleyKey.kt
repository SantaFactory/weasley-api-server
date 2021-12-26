package com.weasleyclock.weasley.domain.embedd

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class WeasleyKey : Serializable {

    @Transient
    private val serialVersionUID = 1L

    @Column
    var userId: Long? = null

    @Column
    var bandId: Long? = null

    @Column(nullable = false)
    var title: String? = null

    constructor()

    constructor(userId: Long, bandId: Long, title: String) {
        this.userId = userId
        this.bandId = bandId
        this.title = title
    }

}
