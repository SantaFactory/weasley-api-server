package com.weasleyclock.weasley.domain

import org.jetbrains.annotations.NotNull
import javax.persistence.*

@Table
@Entity
class BandRole {

    @Id
    @NotNull
    @Column(nullable = false)
    var title: String? = null

}
