package com.weasleyclock.weasleyclient.mock

import com.weasleyclock.weasleyclient.dto.IBandUserCount

class BandUserCount : IBandUserCount {

    private var id: Long? = null

    private var title: String? = null

    private var count: Long? = null

    constructor(id: Long, title: String, count: Long) {
        this.id = id
        this.title = title
        this.count = count
    }

    override fun getId(): Long? = this.id

    override fun getTitle(): String? = this.title

    override fun getUserCount(): Long? = this.count
}
