package com.weasleyclock.weasley.repository

import com.weasleyclock.weasley.domain.MemberRole
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRoleRepository : JpaRepository<MemberRole , Long> {
}
