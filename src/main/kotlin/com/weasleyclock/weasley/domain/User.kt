package com.weasleyclock.weasley.domain

import com.weasleyclock.weasley.domain.convert.UserTypeConvert
import com.weasleyclock.weasley.enmus.UserTypes
import org.hibernate.Hibernate
import org.springframework.beans.factory.support.ManagedSet
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import javax.persistence.*

@Table(name = "weasley_user")
@Entity
class User() : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false, length = 300)
    var email: String? = null

    @Column(nullable = false, length = 300)
    var name: String? = null

    @Convert(converter = UserTypeConvert::class)
    @Column(name = "login_type", nullable = false, length = 10)
    var loginType: UserTypes? = null

    @Column(name = "user_key", nullable = false)
    var userKey: String? = null

    @OneToMany
    @JoinTable(
        name = "user_auth",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "auth_name", referencedColumnName = "title")]
    )
    private var authSet: MutableSet<Auth> = ManagedSet()

    fun getAuthorities(): List<GrantedAuthority> {
        return authSet.map { auth -> SimpleGrantedAuthority(auth.title) }.toList()
    }

    constructor (id: Long, email: String, name: String, authSet: MutableSet<Auth>) : this() {
        this.id = id
        this.email = email
        this.name = name
        this.authSet = authSet
    }

    constructor(email: String, name: String) : this() {
        this.email = email
        this.name = name
    }

    constructor(
        email: String,
        name: String,
        loginType: UserTypes?,
        userKey: String,
        authSet: MutableSet<Auth>
    ) : this() {
        this.email = email
        this.name = name
        this.loginType = loginType
        this.userKey = userKey
        this.authSet = authSet
    }

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        val anyOther = other as User
        return anyOther.id!! == this.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
