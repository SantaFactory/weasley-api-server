package com.weasleyclock.weasleyclient.utils

import com.weasleyclock.weasleyclient.config.exception.AppException
import com.weasleyclock.weasleyclient.config.security.DomainUserDetail
import com.weasleyclock.weasleyclient.domain.User
import com.weasleyclock.weasleyclient.enmus.ErrorTypes
import com.weasleyclock.weasleyclientclient.config.security.AppProperties
import org.apache.commons.lang3.ObjectUtils
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*

class SecurityUtils {

    companion object {

        fun getCurrentLoginUserId(): Long =
            getCurrentLoginUser().orElseThrow { throw AppException(ErrorTypes.NOT_LOGIN_USER) }.user!!.getId()!!

        fun getCurrentLoginEmail(): String =
            getCurrentLoginUser().orElseThrow { throw AppException(ErrorTypes.NOT_LOGIN_USER) }.user!!.getEmail()!!

        fun getCurrentLoginUserName(): String =
            getCurrentLoginUser().orElseThrow { throw AppException(ErrorTypes.NOT_LOGIN_USER) }.user!!.getName()!!

        fun getCurrentUser() : User = getCurrentLoginUser().orElseThrow { throw AppException(ErrorTypes.NOT_LOGIN_USER) }.user!!

        private fun getCurrentLoginUser(): Optional<DomainUserDetail> =
            Optional.ofNullable(extractPrincipal(SecurityContextHolder.getContext()))

        private fun extractPrincipal(securityContext: SecurityContext): DomainUserDetail? {
            if (ObjectUtils.isEmpty(securityContext)) {
                throw AppException(ErrorTypes.NOT_LOGIN_USER)
            } else if (ObjectUtils.isEmpty(securityContext.authentication)) {
                return DomainUserDetail(User(AppProperties.SYSTEM, AppProperties.SYSTEM))
            } else if (securityContext.authentication.principal is DomainUserDetail) {
                return securityContext.authentication.principal as DomainUserDetail
            }
            return null
        }

    }

}

