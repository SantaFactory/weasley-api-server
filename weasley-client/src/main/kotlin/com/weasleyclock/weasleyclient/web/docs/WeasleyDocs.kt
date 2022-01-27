package com.weasleyclock.weasleyclient.web.docs

import com.weasleyclock.weasleyclient.dto.AppMessageDTO
import com.weasleyclock.weasleyclient.dto.WeasleyDTO
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity

interface WeasleyDocs {

    @Operation(summary = "현재 위치 업데이트", description = "내위치 업데이트")
    fun modifyByMyWeasley(dto: WeasleyDTO.Current): ResponseEntity<AppMessageDTO>

    @Operation(summary = "weasly item save", description = "weasly item save")
    fun createWeasleies(dto: WeasleyDTO.Store): ResponseEntity<AppMessageDTO>
}
