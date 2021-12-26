package com.weasleyclock.weasley.service

import com.weasleyclock.weasley.domain.BandWeasley
import com.weasleyclock.weasley.dto.WeasleyDTO
import com.weasleyclock.weasley.repository.BandWeasleyRepository
import com.weasleyclock.weasley.utils.SecurityUtils.Companion.getCurrentLoginUserId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class WeasleyService(private val bandWeasleyRepository: BandWeasleyRepository) {

    @Transactional
    fun updateByMyWeasley(dto: WeasleyDTO.Current): MutableList<BandWeasley> {

        val weasleyList = bandWeasleyRepository.findByUser_Id(getCurrentLoginUserId())

        val resultWeasleyList: MutableList<BandWeasley> = mutableListOf()

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

