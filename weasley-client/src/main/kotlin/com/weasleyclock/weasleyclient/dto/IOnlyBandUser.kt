package com.weasleyclock.weasleyclient.dto

import org.springframework.beans.factory.annotation.Value

interface IOnlyBandUser {

    @Value("#{target.memberSet}")
    fun getMembers(): MutableSet<UserAndBandRole>?

    interface UserAndBandRole {

        @Value("#{target.user.id}")
        fun getUserId(): Long?

        @Value("#{target.user.email}")
        fun getEmail(): String?

        @Value("#{target.bandRole.title}")
        fun getBandRole(): String?
    }

}
