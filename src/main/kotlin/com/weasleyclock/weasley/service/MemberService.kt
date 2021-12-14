package com.weasleyclock.weasley.service

import com.weasleyclock.weasley.domain.Member
import com.weasleyclock.weasley.domain.MemberUser
import com.weasleyclock.weasley.domain.embedd.MemberUserKey
import com.weasleyclock.weasley.dto.MemberDTO
import com.weasleyclock.weasley.repository.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
//@Transactional(rollbackFor = [Exception::class])
class MemberService(
    private val memberRepository: MemberRepository
) {

    /**
     * All Member Data By List </br>
     * @return List<Member>
     */
    @Transactional(readOnly = true)
    fun getAllByMembers(): List<Member> {
        return memberRepository.findAll()
    }

    @Transactional
    fun createByMember(dto: MemberDTO.Created): Member {

        val userId = dto.userId

        val saveEntity = memberRepository.save(dto.toEntity())

        val memberUserSet = listOf(MemberUser(MemberUserKey(userId, saveEntity.id!!, 1))).toSet()

        saveEntity.memberUserSet = memberUserSet

        val weasleyItemSet = dto.getByWeasleyItemSet(saveEntity)

        saveEntity.weasleyItemSet = weasleyItemSet

        return saveEntity
    }

}
