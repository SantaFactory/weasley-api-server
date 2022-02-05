package com.weasleyclock.weasleyclient.mock

import com.weasleyclock.weasleyclient.dto.IOnlyBandUser

class UserAndBandRole : IOnlyBandUser.UserAndBandRole {

    private var userId: Long? = null

    private var email: String? = null

    private var bandRole: String? = null

    constructor(userId: Long, email: String, bandRole: String) {
        this.userId = userId
        this.email = email
        this.bandRole = bandRole
    }

    override fun getUserId(): Long? = this.userId

    override fun getEmail(): String? = this.email

    override fun getBandRole(): String? = this.bandRole
}
