package com.weasleyclock.weasley.domain

import org.springframework.beans.factory.support.ManagedSet
import javax.persistence.*

@Table
@Entity
class Band : BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false)
    var title: String? = null

    @OneToMany(mappedBy = "band" , cascade = [CascadeType.ALL])
    var memberSet: MutableSet<Member> = ManagedSet()

    @OneToMany(mappedBy = "band" , cascade = [CascadeType.ALL])
    var bandWeasleySet : MutableSet<BandWeasley> = ManagedSet()

    constructor(title: String) {
        this.title = title
    }

    constructor()
}
