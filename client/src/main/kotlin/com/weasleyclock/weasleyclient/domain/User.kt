package com.weasleyclock.weasleyclient.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.weasleyclock.weasleyclient.converter.UserTypeConverter
import com.weasleyclock.weasleyclient.domain.base.BaseTimeEntity
import com.weasleyclock.weasleyclient.enmus.RoleType
import com.weasleyclock.weasleyclient.enmus.UserType
import org.springframework.beans.factory.support.ManagedSet
import javax.persistence.*

@Table(name = "app_user")
@Entity
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private var id: Long?,
    @Column(nullable = false, length = 300) private var email: String?,
    @Column(nullable = false, length = 300) private var name: String?,
    @Convert(converter = UserTypeConverter::class) @Column(
        name = "login_type", nullable = false, length = 10
    ) private var loginType: UserType?,
    @Column(name = "user_key", nullable = false) private var userKey: String?,
    @Column(name = "role", nullable = false) private var role: RoleType?,
    @JsonIgnore @OneToMany(
        mappedBy = "user", cascade = [CascadeType.ALL]
    ) private var weasleySet: MutableSet<Weasley> = ManagedSet()
) : BaseTimeEntity() {

    // jwt user
    constructor(id: Long, email: String, name: String, role: RoleType?) : this(id, email, name, null, null, role)

    constructor(email: String, name: String) : this(null, email, name, null, null, null)

    // new user
    constructor(
        email: String, name: String, loginType: UserType?, userKey: String, role: RoleType?
    ) : this(null, email, name, loginType, userKey, role)

    fun getId() = this.id!!.toLong()

    fun getEmail() = this.email

    fun getName() = this.name

    fun getUserKey() = this.userKey

    fun getRole() = this.role
}
