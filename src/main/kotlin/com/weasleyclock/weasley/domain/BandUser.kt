package com.weasleyclock.weasley.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.weasleyclock.weasley.domain.embedd.BandUserKey
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.jetbrains.annotations.NotNull
import javax.persistence.*

@Table
@Entity
@DynamicInsert
@DynamicUpdate
class BandUser {

    @EmbeddedId
    var bandUserKey: BandUserKey? = null

    @OneToOne
    var bandRole: BandRole? = null

    @JsonIgnore
    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @MapsId(value = "userId")
    var user: User? = null

    @JsonIgnore
    @NotNull
    @ManyToOne
    @MapsId(value = "bandId")
    @JoinColumn(name = "band_id", referencedColumnName = "id")
    var band: Band? = null

    constructor()

    constructor(bandUserKey: BandUserKey) {
        this.bandUserKey = bandUserKey
    }

    constructor(user: User, band: Band, bandRole: BandRole) {
//        this.group = group
//        this.user = user
        this.bandRole = bandRole
        this.bandUserKey = BandUserKey(user.id!!, band.id!!)
    }

}
