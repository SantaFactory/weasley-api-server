package com.weasleyclock.weasley.service

import com.weasleyclock.weasley.domain.Member
import com.weasleyclock.weasley.domain.MemberUser
import com.weasleyclock.weasley.dto.MemberDTO
import com.weasleyclock.weasley.repository.MemberRepository
import com.weasleyclock.weasley.repository.MemberRoleRepository
import com.weasleyclock.weasley.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*


@Service
@Transactional(rollbackFor = [Exception::class])
class MemberService(
    private val memberRepository: MemberRepository,
    private val userRepository: UserRepository,
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

        val saveEntity = memberRepository.save(dto.toEntity())

        val leaderUser = userRepository.findById(dto.userId).orElseThrow()

        saveEntity.memberUserSet = listOf(MemberUser(leaderUser, saveEntity, "LEADER")).toSet()

        val weasleyItemSet = dto.getByWeasleyItemSet(saveEntity)

        saveEntity.weasleyItemSet = weasleyItemSet

        return saveEntity
    }

    @Transactional
    fun updateByMember(id: Long, dto: MemberDTO.Updated): Member {

        var updateEntity: Member? = null

        val updateEntityOptional = memberRepository.findById(id)

        if (updateEntityOptional.isEmpty) {

            val updateEntity = updateEntityOptional.get()

            updateEntity.title = dto.title
            updateEntity.memberUserSet = dto.memberUserSet
            updateEntity.weasleyItemSet = dto.weasleyItemSet

        }

        return Optional.ofNullable(updateEntity).orElse(Member())
    }

    @Transactional
    fun removeByMember(id: Long): Long {
        memberRepository.deleteById(id)
        return id
    }

}

