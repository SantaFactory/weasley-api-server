package com.weasleyclock.weasley.domain

import com.weasleyclock.weasley.domain.embedd.BandUserKey
import java.math.BigDecimal
import javax.persistence.*

@Table
@Entity
class BandWeasley : BaseEntity() {

//    @OneToOne
//    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
//    private var user: User? = null
//
//    @OneToOne
//    @JoinColumn(name = "group_id", nullable = false, referencedColumnName = "id")
//    private var group: Group? = null

    @EmbeddedId
    var bandUserKey : BandUserKey? = null

    @Column(name = "weasley_item", nullable = false)
    private var weasleyItem: String? = null

    // 위도
    @Column(nullable = false, columnDefinition = "decimal(19,2) not null default 0")
    private var latitude: BigDecimal? = null

    // 경도
    @Column(nullable = false, columnDefinition = "decimal(19,2) not null default 0")
    private var longitude: BigDecimal? = null

}
