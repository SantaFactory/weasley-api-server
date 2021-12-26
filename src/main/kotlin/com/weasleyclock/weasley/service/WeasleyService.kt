package com.weasleyclock.weasley.service

import com.weasleyclock.weasley.domain.Weasley
import com.weasleyclock.weasley.dto.WeasleyDTO
import com.weasleyclock.weasley.repository.WeasleyRepository
import com.weasleyclock.weasley.utils.SecurityUtils.Companion.getCurrentLoginUserId
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

}

