package com.weasleyclock.weasley.domain

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Table
@Entity
data class Auth(
    @Id
    val title: String? = null
)
