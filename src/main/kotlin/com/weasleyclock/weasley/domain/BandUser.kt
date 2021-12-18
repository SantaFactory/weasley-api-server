package com.weasleyclock.weasley.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.weasleyclock.weasley.domain.embedd.BandUserKey
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import javax.persistence.*
import kotlin.jvm.Transient

@Table
@Entity
@DynamicInsert
@DynamicUpdate
class BandUser {

    @EmbeddedId
    var id: BandUserKey? = null

    @OneToOne
    var bandRole: BandRole? = null

    @ManyToOne
    @JsonIgnore
    @MapsId(value = "userId")
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    var user: User? = null

    @ManyToOne
    @JsonIgnore
    @MapsId(value = "bandId")
    @JoinColumn(name = "band_id", referencedColumnName = "id")
    var band: Band? = null

    constructor()

    constructor(id: BandUserKey) {
        this.id = id
    }

    constructor(band: Band, user: User, id: BandUserKey, bandRole: BandRole) {
        this.band = band
        this.user = user
        this.bandRole = bandRole
        this.id = id
    }

}
