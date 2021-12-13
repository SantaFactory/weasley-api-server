package com.weasleyclock.weasley.domain

import javax.persistence.*

@Entity
@Table
class Member : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Long? = null

    @Column(name = "member_title", nullable = false)
    private var title: String? = null

//    @OneToMany
//    @JoinColumn(name = "member_id")
//    private var weasleyItemSet : Set<WeasleyItem> = HashSet()

//    @ManyToMany
//    @JoinTable(
//        name = "user_member_relation",
//        joinColumns = [JoinColumn(name = "member_id", referencedColumnName = "id")],
//        inverseJoinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")]
//    )
//    private var userSet: Set<User> = HashSet()

}
