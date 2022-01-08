package com.weasleyclock.weasley.service

import com.weasleyclock.weasley.config.exception.AppException
import com.weasleyclock.weasley.domain.Band
import com.weasleyclock.weasley.domain.BandRole
import com.weasleyclock.weasley.domain.Member
import com.weasleyclock.weasley.dto.BandDTO
import com.weasleyclock.weasley.enmus.BandRoles
import com.weasleyclock.weasley.enmus.ErrorTypes
import com.weasleyclock.weasley.repository.BandRepository
import com.weasleyclock.weasley.repository.MemberRepository
import com.weasleyclock.weasley.repository.UserRepository
import com.weasleyclock.weasley.utils.SecurityUtils.Companion.getCurrentLoginUserId
import org.apache.commons.lang3.ObjectUtils
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class BandService(
    private val bandRepository: BandRepository,
    private val userRepository: UserRepository,
    private val memberRepository: MemberRepository,
) {

    @Transactional(readOnly = true)
    fun getBandOne(id: Long): BandDTO.BandOne? = bandRepository.findBandById(id, BandDTO.BandOne::class.java)

    @Transactional(readOnly = true)
    fun getAllByGroups(): List<BandDTO.BandUserCount>? = bandRepository.findBy(BandDTO.BandUserCount::class.java)

    @Transactional(readOnly = true)
    fun getGroupsBySelf(): List<BandDTO.BandUserCount>? =
        bandRepository.findByMemberSet_User_Id(getCurrentLoginUserId(), BandDTO.BandUserCount::class.java)

    @Transactional(readOnly = true)
    fun getGroupByUsers(groupId: Long): Set<BandDTO.OnlyBandUser.UserAndBandRole>? =
        bandRepository.findById(groupId, BandDTO.OnlyBandUser::class.java)?.getMembers()?.toSet()

    @Transactional
    fun createByBand(dto: BandDTO.Created): Band? {

        val saveEntity = dto.toEntity()

        val leaderUser = userRepository.findById(getCurrentLoginUserId())
            .orElseThrow { throw AppException(ErrorTypes.USER_NOT_FOUND) }

        bandRepository.save(saveEntity)

        val member = Member(saveEntity, leaderUser, BandRole(BandRoles.LEADER.name))

        saveEntity.memberSet = listOf(member).toSet() as MutableSet<Member>

        saveEntity.weasleySet = dto.toWeasleyItems(member)

        return saveEntity
    }

    @Transactional
    fun updateByGroup(id: Long, dto: BandDTO.Updated): Band? {
        val updateEntity = bandRepository.findById(id).orElseThrow { throw AppException(ErrorTypes.BAND_NOT_FOUND) }
        updateEntity.title = dto.title
        return updateEntity
    }

    @Transactional
    fun removeByGroup(id: Long): Long {
        if (isLeaderAble(id, getCurrentLoginUserId())) {
            bandRepository.deleteById(id)
            return id
        }
        throw AppException(ErrorTypes.IS_NOT_READER)
    }

    @Transactional
    fun saveByMember(bandId: Long, userId: Long): Member? {
        val band = bandRepository.findById(bandId).orElseThrow { throw AppException(ErrorTypes.BAND_NOT_FOUND) }
        val user = userRepository.findById(userId).orElseThrow { throw AppException(ErrorTypes.USER_NOT_FOUND) }
        val entity = Member(band, user, BandRole(BandRoles.MEMBER.name))
        memberRepository.save(entity)
        return entity
    }

    private fun isLeaderAble(bandId: Long, userId: Long): Boolean =
        ObjectUtils.isNotEmpty(
            memberRepository.findByBand_IdAndUser_IdAndBandRole_Title(
                bandId,
                userId,
                BandRoles.LEADER.name
            )
        )

}

