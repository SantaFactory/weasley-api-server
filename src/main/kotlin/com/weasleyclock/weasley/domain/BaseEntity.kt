package com.weasleyclock.weasley.domain

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*
import javax.persistence.*

@MappedSuperclass
@EntityListeners(value = [AuditingEntityListener::class])
abstract class BaseEntity {

    @CreatedBy
    @Column(nullable = false, name = "created_by")
    var createdBy: String? = null

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, name = "created_date")
    var createdDate: Date? = null

    @LastModifiedBy
    @Column(name = "last_modified_by", nullable = false)
    var lastModifiedBy: String? = null

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_modified_date", nullable = false)
    var lastModifiedDate: Date? = null


    @PrePersist
    fun onInsert() {
        this.createdBy = "system"
        this.lastModifiedBy = "system"
        this.createdDate = Date()
        this.lastModifiedDate = Date()
    }

    @PreUpdate
    fun onUpdate() {
        this.lastModifiedBy = "system"
        this.lastModifiedDate = Date()
    }

}
