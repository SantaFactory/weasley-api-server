package com.weasleyclock.weasley.domain.base

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@MappedSuperclass
@EntityListeners(value = [AuditingEntityListener::class])
abstract class BaseEntity : BaseTimeEntity() {

    @CreatedBy
    @Column(nullable = false, name = "created_by")
    var createdBy: String? = null

    @LastModifiedBy
    @Column(name = "last_modified_by", nullable = false)
    var lastModifiedBy: String? = null

}
