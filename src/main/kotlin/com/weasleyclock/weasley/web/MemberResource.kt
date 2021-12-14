package com.weasleyclock.weasley.web

import com.weasleyclock.weasley.dto.AppMessageDTO
import com.weasleyclock.weasley.dto.MemberDTO
import com.weasleyclock.weasley.enmus.ApiType
import com.weasleyclock.weasley.service.MemberService
import com.weasleyclock.weasley.utils.HeaderUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/member")
class MemberResource(private val service: MemberService) {

    @Value("\${spring.application.name}")
    private val applicationName: String? = null

    private val entityName = "member"

    @GetMapping("")
    fun showByMembers(): ResponseEntity<AppMessageDTO> {
        val body = AppMessageDTO(HttpStatus.OK.value(), service.getAllByMembers())
        return ResponseEntity
            .ok()
            .headers(HeaderUtils.createByAlert(applicationName, entityName, "ALL", ApiType.SELECT))
            .body(body)
    }

    @PostMapping("")
    fun saveByMember(@RequestBody dto: MemberDTO.Created): ResponseEntity<AppMessageDTO> {
        val data = service.createByMember(dto)
        val body = AppMessageDTO(HttpStatus.OK.value(), data)
        return ResponseEntity.ok()
            .headers(HeaderUtils.createByAlert(applicationName, entityName, data.title.toString(), ApiType.CREATE))
            .body(body)
    }

}
