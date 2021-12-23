package com.weasleyclock.weasley.service

import com.weasleyclock.weasley.config.exception.AppException
import com.weasleyclock.weasley.domain.Band
import com.weasleyclock.weasley.domain.BandRole
import com.weasleyclock.weasley.domain.BandUser
import com.weasleyclock.weasley.dto.BandDTO
import com.weasleyclock.weasley.enmus.BandRoles
import com.weasleyclock.weasley.enmus.ErrorTypes
import com.weasleyclock.weasley.repository.BandRepository
import com.weasleyclock.weasley.repository.BandUserRepository
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
    private val bandUserRepository: BandUserRepository,
) {

    @Transactional(readOnly = true)
    fun getAllByGroups(): List<BandDTO.BandUserCount> = bandRepository.findBy(BandDTO.BandUserCount::class.java)

    @Transactional(readOnly = true)
    fun getGroupsBySelf(): List<BandDTO.BandUserCount> =
        bandRepository.findByBandUserSet_User_Id(getCurrentLoginUserId(), BandDTO.BandUserCount::class.java)

    @Transactional(readOnly = true)
    fun getGroupByUsers(groupId: Long): Set<BandDTO.UserInfo> {
        return bandRepository.findById(groupId, BandDTO.OnlyBandUser::class.java)?.getBandUserSet()
            .map { bandUser ->
                bandUser.getUser()?.let {
                    BandDTO.UserInfo(it.getId()!!, it.getEmail()!!, bandUser.getBandRole()!!.title!!)
                }
            }
            .toSet()
    }

    @Transactional
    fun createByGroup(dto: BandDTO.Created): Band {

        val saveEntity = dto.toEntity()

        // todo :  exception
        val leaderUser = userRepository.findById(getCurrentLoginUserId()).orElseThrow { throw NullPointerException() }

        bandRepository.save(saveEntity)

        val bandUser = BandUser(saveEntity, leaderUser, BandRole(BandRoles.LEADER.name))

        saveEntity.bandUserSet = listOf(bandUser).toSet() as MutableSet<BandUser>

        saveEntity.bandWeasleySet = dto.toWeasleyItems(bandUser)

        return saveEntity
    }

    @Transactional
    fun updateByGroup(id: Long, dto: BandDTO.Updated): Band {
        // todo : exception
        val updateEntity = bandRepository.findById(id).orElseThrow { throw NullPointerException() }
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
    fun saveByBandUser(bandId: Long, userId: Long): BandUser {
        // todo : exception
        val band = bandRepository.findById(bandId).orElseThrow { throw NullPointerException() }
        val user = userRepository.findById(userId).orElseThrow { throw NullPointerException() }
        val entity = BandDTO.Member().toEntity(band, user)
        bandUserRepository.save(entity)
        return entity
    }

    private fun isLeaderAble(bandId: Long, userId: Long): Boolean {
        val leaderRoleUser: BandUser =
            bandUserRepository.findByBand_IdAndUser_IdAndBandRole_Title(bandId, userId, BandRoles.LEADER.name)
        return ObjectUtils.isNotEmpty(leaderRoleUser)
    }

}

