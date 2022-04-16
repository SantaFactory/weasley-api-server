package com.weasleyclock.weasleyclient.domain

import com.weasleyclock.weasleyclient.enmus.RoleName
import org.hibernate.Hibernate
import org.jetbrains.annotations.NotNull
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Table
@Entity
class BandRole() {

    @Id
    @NotNull
    @Column(nullable = false)
    private var title: RoleName? = null

    constructor(title: RoleName) : this() {
        this.title = title
    }

    override fun hashCode(): Int {
        return title.hashCode();
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        val anyOther = other as BandRole
        return anyOther.title!! == other.title
    }

    fun getTitle() = this.title
}
