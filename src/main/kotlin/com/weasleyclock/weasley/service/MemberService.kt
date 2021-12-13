package com.weasleyclock.weasley.service

import com.weasleyclock.weasley.domain.Member
import com.weasleyclock.weasley.repository.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class MemberService(private val repository: MemberRepository) {

    /**
     * All Member Data By List </br>
     * @return List<Member>
     */
    @Transactional(readOnly = true)
    fun getAllByMembers () : List<Member> {
        return repository.findAll()
    }

}
