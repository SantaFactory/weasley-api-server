package com.weasleyclock.weasley.utils

import com.weasleyclock.weasley.config.exception.AppException
import com.weasleyclock.weasley.config.security.AppProperties
import com.weasleyclock.weasley.config.security.DomainUserDetail
import com.weasleyclock.weasley.domain.User
import com.weasleyclock.weasley.enmus.ErrorTypes
import org.apache.commons.lang3.ObjectUtils
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*

class SecurityUtils {

    companion object {

        fun getCurrentLoginUserId(): Long =
            getCurrentLoginUser().orElseThrow { throw AppException(ErrorTypes.NOT_LOGIN_USER) }.user!!.id!!

        fun getCurrentLoginEmail(): String =
            getCurrentLoginUser().orElseThrow { throw AppException(ErrorTypes.NOT_LOGIN_USER) }.user!!.email!!

        fun getCurrentLoginUserName(): String =
            getCurrentLoginUser().orElseThrow { throw AppException(ErrorTypes.NOT_LOGIN_USER) }.user!!.name!!

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

