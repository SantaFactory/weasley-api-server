package com.weasleyclock.weasley.domain

import org.jetbrains.annotations.NotNull
import javax.persistence.*

@Entity
@Table(name = "member_role")
class MemberRole {

    @Id
    @NotNull
    @Column(name = "title", nullable = false)
    var title: String? = null

}
