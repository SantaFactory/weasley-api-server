package com.weasleyclock.weasley.service

import com.weasleyclock.weasley.domain.Band
import com.weasleyclock.weasley.domain.BandRole
import com.weasleyclock.weasley.domain.BandUser
import com.weasleyclock.weasley.dto.BandDTO
import com.weasleyclock.weasley.enmus.BandRoles
import com.weasleyclock.weasley.repository.BandRepository
import com.weasleyclock.weasley.repository.BandUserRepository
import com.weasleyclock.weasley.repository.UserRepository
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
    // todo : 시큐리티시 삭제
    private val userId = 1L

    @Transactional(readOnly = true)
    fun getAllByGroups(): List<BandDTO.BandUserCount> {
        return bandRepository.findBy(BandDTO.BandUserCount::class.java)
    }

    @Transactional(readOnly = true)
    fun getGroupsBySelf(): List<BandDTO.BandUserCount> {
        return bandRepository.findByBandUserSet_User_Id(userId, BandDTO.BandUserCount::class.java)
    }

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

        val leaderUser = userRepository.findById(userId).orElseThrow()

        bandRepository.save(saveEntity)

        val bandUser = BandUser(saveEntity, leaderUser, BandRole(BandRoles.LEADER.name))

        saveEntity.bandUserSet = listOf(bandUser).toSet() as MutableSet<BandUser>

        saveEntity.bandWeasleySet = dto.toWeasleyItems(bandUser)

        return saveEntity
    }

    @Transactional
    fun updateByGroup(id: Long, dto: BandDTO.Updated): Band {
        val updateEntity = bandRepository.findById(id).orElseThrow()
        updateEntity.title = dto.title
        return updateEntity
    }

    @Transactional
    fun removeByGroup(id: Long): Long {

        val leaderRoleUser: BandUser =
            bandUserRepository.findByBand_IdAndUser_IdAndBandRole_Title(id, userId, BandRoles.LEADER.name)

        val isLeaderAble = ObjectUtils.isNotEmpty(leaderRoleUser)

        if (isLeaderAble) {

            bandRepository.deleteById(id)
            return id

        }else{
            // todo : exception
            throw NullPointerException()
        }

    }

    @Transactional
    fun saveByBandUser(bandId: Long, userId: Long) : BandUser {
        val band = bandRepository.findById(bandId).orElseThrow()
        val user = userRepository.findById(userId).orElseThrow()
        val entity = BandDTO.Member().toEntity(band, user)
        bandUserRepository.save(entity)
        return entity
    }

}

