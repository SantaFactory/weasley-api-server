package com.weasleyclock.weasleyclient.service

import com.weasleyclock.weasleyclient.domain.Weasley
import com.weasleyclock.weasleyclient.dto.WeasleyDTO
import com.weasleyclock.weasleyclient.repository.WeasleyRepository
import com.weasleyclock.weasleyclient.utils.SecurityUtils
import com.weasleyclock.weasleyclient.utils.SecurityUtils.Companion.getCurrentLoginUserId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class WeasleyService(private val weasleyRepository: WeasleyRepository) {

    @Transactional
    fun updateByMyWeasley(dto: WeasleyDTO.Current): MutableList<Weasley> {

        val weasleyList = weasleyRepository.findByUser_Id(getCurrentLoginUserId())

        val resultWeasleyList: MutableList<Weasley> = mutableListOf()

        weasleyList
            .forEach { weasley ->
                weasley.isCurrent = (weasley.latitude == dto.latitude && weasley.longitude == dto.longitude)
                if (weasley.isCurrent!!) {
                    resultWeasleyList.add(weasley)
                }
            }

        return resultWeasleyList
    }

    @Transactional
    fun saveWeasley(dto: WeasleyDTO.Store) : List<Weasley> {

        val user = SecurityUtils.getCurrentUser()

        val entities = dto.toEntities(user)

        weasleyRepository.saveAll(entities)

        return entities
    }

}

