package com.weasleyclock.weasley.domain.embedd

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
data class BandUserKey(
    @Column
    var userId: Long? = null,
    @Column
    var bandId: Long? = null,
) : Serializable {

    @Transient
    private val serialVersionUID = 1L

    constructor(userId: Long) : this() {
        this.userId = userId
    }

}
