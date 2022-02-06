package com.weasleyclock.weasleyclient.service

import com.weasleyclock.weasleyclient.config.exception.AppException
import com.weasleyclock.weasleyclient.config.exception.NotFoundDataException
import com.weasleyclock.weasleyclient.domain.Band
import com.weasleyclock.weasleyclient.domain.BandRole
import com.weasleyclock.weasleyclient.domain.Member
import com.weasleyclock.weasleyclient.dto.BandDTO
import com.weasleyclock.weasleyclient.dto.IBandUserCount
import com.weasleyclock.weasleyclient.dto.IOnlyBandUser
import com.weasleyclock.weasleyclient.enmus.ErrorTypes
import com.weasleyclock.weasleyclient.enmus.RoleName
import com.weasleyclock.weasleyclient.repository.BandRepository
import com.weasleyclock.weasleyclient.repository.MemberRepository
import com.weasleyclock.weasleyclient.repository.UserRepository
import com.weasleyclock.weasleyclient.utils.SecurityUtils.Companion.getCurrentLoginUserId
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
    fun getBandOne(id: Long): BandDTO.Grouping? = bandRepository.selectBandOne(id)

    @Transactional(readOnly = true)
    fun getAllByBands(): List<IBandUserCount>? = bandRepository.findBy(IBandUserCount::class.java)

    @Transactional(readOnly = true)
    fun getMyBands(): List<IBandUserCount>? =
        bandRepository.findByMemberSet_User_Id(getCurrentLoginUserId(), IBandUserCount::class.java)

    @Transactional(readOnly = true)
    fun getBandByUsers(groupId: Long): Set<IOnlyBandUser.UserAndBandRole>? =
        bandRepository.findById(groupId, IOnlyBandUser::class.java)?.getMembers()?.toSet()

    @Transactional
    fun createByBand(dto: BandDTO.Created): Band? {

        val saveEntity = dto.toEntity()

        val leaderUser = userRepository.findById(getCurrentLoginUserId()).orElseThrow { throw NotFoundDataException() }

        bandRepository.save(saveEntity)

        val member = Member(saveEntity, leaderUser, BandRole(RoleName.LEADER))

        saveEntity.changeBandMember(listOf(member).toSet() as MutableSet<Member>)

        return saveEntity
    }

    @Transactional
    fun updateBand(id: Long, dto: BandDTO.Updated): Band? {
        val updateEntity = bandRepository.findById(id).orElseThrow { throw NotFoundDataException() }
        updateEntity.updateBandTitle(dto.title)
        return updateEntity
    }

    @Transactional
    fun removeBand(id: Long): Long {
        if (isLeaderAble(id, getCurrentLoginUserId())) {
            bandRepository.deleteById(id)
            return id
        }
        throw AppException(ErrorTypes.IS_NOT_READER)
    }

    @Transactional
    fun exitBand(bandId: Long): Band? {

        val userId = getCurrentLoginUserId()

        val band = bandRepository.findById(bandId).orElseThrow { throw NotFoundDataException() }

        val memberSet = band.getMemberSet()

        val myMember = memberSet?.stream()?.filter { member -> member.eqUserId(userId) }
            ?.findAny()
            ?.orElseThrow()

        if (myMember?.isLeader() == true) {
            val subLeader = getSubLeader(memberSet)
            subLeader!!.changeBandRole(RoleName.LEADER)
        }

        val nowMemberSet = removeBandMembers(memberSet, userId)

        if (nowMemberSet.isEmpty()) {
            bandRepository.deleteById(bandId)
        } else {
            band.changeBandMember(memberSet)
        }

        return band
    }

    @Transactional
    fun exileBandMember(userId: Long, bandId: Long): Band? {

        val band = bandRepository.findById(bandId).orElseThrow { throw NotFoundDataException() }

        val memberSet = band.getMemberSet()

        val nowMemberSet = removeBandMembers(memberSet, userId)

        band.changeBandMember(nowMemberSet)

        return band
    }

    @Transactional
    fun saveByMember(bandId: Long, userId: Long): Member? {
        val band = bandRepository.findById(bandId).orElseThrow { throw NotFoundDataException() }
        val user = userRepository.findById(userId).orElseThrow { throw NotFoundDataException() }
        val entity = Member(band, user, BandRole(RoleName.MEMBER))
        memberRepository.save(entity)
        return entity
    }

    private fun isLeaderAble(bandId: Long, userId: Long): Boolean = ObjectUtils.isNotEmpty(
        memberRepository.findByBand_IdAndUser_IdAndBandRole_Title(
            bandId,
            userId,
            RoleName.LEADER
        )
    )

    private fun removeBandMembers(memberSet: MutableSet<Member>, removeUserId: Long) =
        memberSet?.filter { member -> member.notEqUserId(removeUserId) }!!.toMutableSet()

    private fun getSubLeader(memberSet: MutableSet<Member>) =
        memberSet?.stream()?.filter { member -> RoleName.SUB_LEADER == member.getBandRoleTitle() }
            ?.findAny()
            ?.orElseThrow { throw NotFoundDataException() }
}

