package com.weasleyclock.weasley.web

import com.weasleyclock.weasley.dto.AppMessageDTO
import com.weasleyclock.weasley.dto.WeasleyDTO
import com.weasleyclock.weasley.service.WeasleyService
import com.weasleyclock.weasley.web.docs.WeasleyDocs
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/weasley")
class WeasleyResource(private val service: WeasleyService) : WeasleyDocs {

    @PutMapping()
    override fun modifyByMyWeasley(@RequestBody dto: WeasleyDTO.Current): ResponseEntity<AppMessageDTO> {
        val body = AppMessageDTO(HttpStatus.OK.value(), service.updateByMyWeasley(dto))
        return ResponseEntity.ok()
            .body(body)
    }


}
