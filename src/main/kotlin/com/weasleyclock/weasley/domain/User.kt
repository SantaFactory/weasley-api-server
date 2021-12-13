package com.weasleyclock.weasley.domain

import com.weasleyclock.weasley.domain.convert.UserTypeConvert
import com.weasleyclock.weasley.enmus.UserType
import javax.persistence.*

@Entity
@Table(name = "user")
class User : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Long? = null

    @Column(nullable = false, length = 300)
    private var email: String? = null

    @Column(name = "hash_password", nullable = false, length = 300)
    private var password: String? = null

    @Convert(converter = UserTypeConvert::class)
    @Column(name = "login_type", nullable = false, length = 10)
    private var loginType: UserType? = null

}
