package com.weasleyclock.weasley.domain

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.Column
import javax.persistence.MappedSuperclass
import javax.persistence.Temporal
import javax.persistence.TemporalType

@MappedSuperclass
abstract class BaseEntity {

    @CreatedBy
    @Column(nullable = false, name = "created_by")
    private var createdBy: String? = null

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, name = "created_date")
    private var createdDate: Date? = null

    @LastModifiedBy
    @Column(name = "last_modified_by", nullable = false)
    private var lastModifiedBy: String? = null

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_modified_date", nullable = false)
    private var lastModifiedDate: Date? = null

}
