package com.weasleyclock.weasley.service

import com.weasleyclock.weasley.domain.Member
import com.weasleyclock.weasley.domain.MemberUser
import com.weasleyclock.weasley.dto.MemberDTO
import com.weasleyclock.weasley.enmus.MemberRoles
import com.weasleyclock.weasley.repository.MemberRepository
import com.weasleyclock.weasley.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional(rollbackFor = [Exception::class])
class MemberService(
    private val memberRepository: MemberRepository,
    private val userRepository: UserRepository,
) {

    // todo : 시큐리티시 삭제
    private val userId = 1L

    @Transactional(readOnly = true)
    fun getAllByMembers(): List<MemberDTO.Defualt> {
        return memberRepository.findAll()
            ?.map { member -> MemberDTO.Defualt(member.id!!, member.title!!) }.toList()
    }

    @Transactional(readOnly = true)
    fun getMembersBySelf(): List<MemberDTO.Defualt> {
        return memberRepository.findByMemberUserSet_User_Id(userId)
            ?.map { member -> MemberDTO.Defualt(member.id!!, member.title!!) }.toList()
    }

    @Transactional(readOnly = true)
    fun getMemberByUsers(memberId: Long): Set<MemberDTO.User?> {
        return memberRepository.findById(memberId, MemberDTO.OnlyMemberUserSet::class.java)?.getMemberUserSet()
            .map { memberUser ->
                memberUser.user?.let {
                    MemberDTO.User(it.id!!, it.email!!, memberUser.memberRole!!)
                }
            }
            .toSet()
    }

    @Transactional
    fun createByMember(dto: MemberDTO.Created): Member {

        val saveEntity = dto.toEntity()

        val leaderUser = userRepository.findById(userId).orElseThrow()

        memberRepository.save(saveEntity)

        saveEntity.memberUserSet = listOf(MemberUser(leaderUser, saveEntity, MemberRoles.LEADER.name)).toSet()

        val weasleyItemSet = dto.getByWeasleyItemSet(saveEntity)

        saveEntity.weasleyItemSet = weasleyItemSet

        return saveEntity
    }

    @Transactional
    fun updateByMember(id: Long, dto: MemberDTO.Updated): Member {

        val updateEntity = memberRepository.findById(id).orElseThrow()

        updateEntity.title = dto.title
        updateEntity.memberUserSet = dto.memberUserSet
        updateEntity.weasleyItemSet = dto.weasleyItemSet

        return updateEntity
    }

    @Transactional
    fun removeByMember(id: Long): Long {
        memberRepository.deleteById(id)
        return id
    }

}

