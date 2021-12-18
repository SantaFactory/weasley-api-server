package com.weasleyclock.weasley.service

import com.weasleyclock.weasley.domain.Band
import com.weasleyclock.weasley.dto.GroupDTO
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
    fun getAllByGroups(): List<GroupDTO.Defualt> {
        return bandRepository.findAll()
            ?.map { group -> GroupDTO.Defualt(group.id!!, group.title!!) }.toList()
    }

//    @Transactional(readOnly = true)
//    fun getGroupsBySelf(): List<GroupDTO.Defualt> {
//        return groupRepository.findByGroupUserSet_User_Id(userId)
//            ?.map { group -> GroupDTO.Defualt(group.id!!, group.title!!) }.toList()
//    }

//    @Transactional(readOnly = true)
//    fun getGroupByUsers(groupId: Long): Set<GroupDTO.User?> {
//        return null
////        return groupRepository.findById(groupId, GroupDTO.OnlyGroupUserSet::class.java)?.getMemberUserSet()
////            .map { groupUser ->
////                groupUser.user?.let {
////                    GroupDTO.User(it.id!!, it.email!!, groupUser.groupRole!!)
////                }
////            }
////            .toSet()
//    }

    @Transactional
    fun createByGroup(dto: GroupDTO.Created): Band {

        val saveEntity = dto.toEntity()

        val leaderUser = userRepository.findById(userId).orElseThrow()

        bandRepository.save(saveEntity)

//        saveEntity.groupUserSet = listOf(GroupUser(leaderUser, saveEntity, GroupRoles.LEADER.name)).toSet()

        return saveEntity
    }

    @Transactional
    fun updateByGroup(id: Long, dto: GroupDTO.Updated): Band {
        val updateEntity = bandRepository.findById(id).orElseThrow()
        updateEntity.title = dto.title
//        updateEntity.groupUserSet = dto.groupUserSet
        return updateEntity
    }

    @Transactional
    fun removeByGroup(id: Long): Long {
        bandRepository.deleteById(id)
        return id
    }

}

