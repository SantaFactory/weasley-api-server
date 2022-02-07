package com.weasleyclock.weasleyclient.mock

import com.weasleyclock.weasleyclient.config.security.DomainUserDetail
import com.weasleyclock.weasleyclient.domain.*
import com.weasleyclock.weasleyclient.dto.BandDTO
import com.weasleyclock.weasleyclient.enmus.AppRole
import com.weasleyclock.weasleyclient.enmus.RoleName
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*

class UserMock {

    companion object {

        val DEFAULT_ID = 1L

        val DEFAULT_TITLE = "test"

        val authoritySet = listOf(Authority(AppRole.ADMIN.name)).toMutableSet()

        val user = User(1, "admin", "admin", authoritySet)

        val otherUser = User(2, "otherUser", "otherUser", authoritySet)

        val members = listOf(
            Member(user.getId(), DEFAULT_ID, BandRole(RoleName.LEADER)),
            Member(2, DEFAULT_ID, BandRole(RoleName.SUB_LEADER)),
        ).toMutableSet()

        val bandOptional = Optional.of(Band(DEFAULT_TITLE).id(DEFAULT_ID).memberSet(members))

        fun setUpUser() {

            val jwtUser = DomainUserDetail(user)

            val userToken = UsernamePasswordAuthenticationToken(jwtUser, null, jwtUser.authorities)

            SecurityContextHolder.getContext().authentication = userToken
        }

        val bandOne: BandDTO.Grouping = BandDTO.Grouping(DEFAULT_ID, DEFAULT_TITLE)
    }
}
