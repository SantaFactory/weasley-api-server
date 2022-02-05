package com.weasleyclock.weasleyclient.mock

import com.weasleyclock.weasleyclient.dto.IOnlyBandUser

class OnlyBandUser : IOnlyBandUser {

    private var members: MutableSet<IOnlyBandUser.UserAndBandRole>? = null

    constructor(members: MutableSet<IOnlyBandUser.UserAndBandRole>) {
        this.members = members
    }

    override fun getMembers(): MutableSet<IOnlyBandUser.UserAndBandRole>? = this.members
}
