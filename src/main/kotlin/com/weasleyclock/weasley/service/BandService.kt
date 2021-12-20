package com.weasleyclock.weasley.service

import com.weasleyclock.weasley.domain.Band
import com.weasleyclock.weasley.domain.BandRole
import com.weasleyclock.weasley.domain.BandUser
import com.weasleyclock.weasley.dto.BandDTO
import com.weasleyclock.weasley.enmus.BandRoles
import com.weasleyclock.weasley.repository.BandRepository
import com.weasleyclock.weasley.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional(rollbackFor = [Exception::class])
class BandService(
    private val bandRepository: BandRepository,
    private val userRepository: UserRepository,
) {

    // todo : 시큐리티시 삭제
    private val userId = 1L

    @Transactional(readOnly = true)
    fun getAllByGroups(): List<BandDTO.Base> {
        return bandRepository.findAll()
            ?.map { group -> BandDTO.Base(group.id!!, group.title!!) }
            .toList()
    }

    @Transactional(readOnly = true)
    fun getGroupsBySelf(): List<BandDTO.Base> {
        return bandRepository.findByBandUserSet_User_Id(userId)
            ?.map { band -> BandDTO.Base(band.id!!, band.title!!) }
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
        bandRepository.deleteById(id)
        return id
    }

}

