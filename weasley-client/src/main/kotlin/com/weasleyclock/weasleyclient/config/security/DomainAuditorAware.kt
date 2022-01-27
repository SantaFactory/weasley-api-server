package com.weasleyclock.weasleyclient.config.security

import com.weasleyclock.weasleyclient.utils.SecurityUtils
import org.springframework.data.domain.AuditorAware
import java.util.*

class DomainAuditorAware : AuditorAware<String> {

    override fun getCurrentAuditor(): Optional<String> {
        return Optional.ofNullable(SecurityUtils.getCurrentLoginEmail())
    }

}
