package com.weasleyclock.weasley.domain

import com.weasleyclock.weasley.domain.convert.UserTypeConvert
import com.weasleyclock.weasley.enmus.UserTypes
import org.springframework.beans.factory.support.ManagedSet
import javax.persistence.*

@Table
@Entity
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(nullable = false, length = 300)
    var email: String? = null,
    @Column(nullable = false, length = 300)
    var name: String? = null,
    @Convert(converter = UserTypeConvert::class)
    @Column(name = "login_type", nullable = false, length = 10)
    var loginType: UserTypes? = null,
    @OneToMany
    @JoinTable(
        name = "user_auth",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "auth_name", referencedColumnName = "title")]
    )
    var authSet: MutableSet<Auth> = ManagedSet()
) : BaseEntity()
