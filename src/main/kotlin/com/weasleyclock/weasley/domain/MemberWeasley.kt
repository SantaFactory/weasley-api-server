package com.weasleyclock.weasley.domain

import java.math.BigDecimal
import javax.persistence.*

@Table
@Entity
class MemberWeasley : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Long? = null

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    private var user: User? = null

    @OneToOne
    @JoinColumn(name = "member_id", nullable = false, referencedColumnName = "id")
    private var member: Member? = null

    @OneToOne
    @JoinColumn(name = "weasley_item_id" , nullable = false , referencedColumnName = "id")
    private var weasleyItem: WeasleyItem? = null

    // 위도
    @Column(nullable = false, columnDefinition = "decimal(19,2) not null default 0")
    private var latitude: BigDecimal? = null

    // 경도
    @Column(nullable = false, columnDefinition = "decimal(19,2) not null default 0")
    private var longitude: BigDecimal? = null
}
