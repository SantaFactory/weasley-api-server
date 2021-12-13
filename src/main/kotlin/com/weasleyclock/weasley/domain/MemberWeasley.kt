package com.weasleyclock.weasley.domain

import java.math.BigDecimal
import javax.persistence.*

@Table
@Entity
class MemberWeasley : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Long? = null

    @Column(nullable = false)
    private var userId: Long? = null

    @Column(nullable = false)
    private var menuberId: Long? = null

    @Column(nullable = false)
    private var weasleyTitle: String? = null

    // 위도
    // todo : default value check
    @Column(nullable = false , columnDefinition = "default 0")
    private var latitude: BigDecimal? = null

    // 경도
    @Column(nullable = false , columnDefinition = "default 0")
    private var longitude: BigDecimal? = null

}