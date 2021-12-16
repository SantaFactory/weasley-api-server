package com.weasleyclock.weasley.web

import com.weasleyclock.weasley.dto.AppMessageDTO
import com.weasleyclock.weasley.dto.MemberDTO
import com.weasleyclock.weasley.enmus.ApiType
import com.weasleyclock.weasley.service.MemberService
import com.weasleyclock.weasley.utils.HeaderUtils
import io.swagger.annotations.ApiParam
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

    @GetMapping("/self")
    fun showMemberByUser(): ResponseEntity<AppMessageDTO> {

        val body = AppMessageDTO(HttpStatus.OK.value(), service.getMembersBySelf())

        return ResponseEntity
            .ok()
            .headers(HeaderUtils.createByAlert(applicationName, entityName, "self", ApiType.SELECT))
            .body(body)
    }

    @GetMapping("/{id}/users")
    fun showMemberByUsers(@PathVariable id: Long): ResponseEntity<AppMessageDTO> {

        val body = AppMessageDTO(HttpStatus.OK.value(), service.getMemberByUsers(id))

        return ResponseEntity
            .ok()
            .headers(HeaderUtils.createByAlert(applicationName, entityName, id.toString(), ApiType.SELECT))
            .body(body)
    }

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

    @PutMapping("/{id}")
    fun updateByMember(@PathVariable id: Long, @RequestBody dto: MemberDTO.Updated): ResponseEntity<AppMessageDTO> {

        val data = service.updateByMember(id, dto)

        val body = AppMessageDTO(HttpStatus.OK.value(), data)

        return ResponseEntity.ok()
            .headers(HeaderUtils.createByAlert(applicationName, entityName, id.toString(), ApiType.UPDATE))
            .body(body)
    }

    @DeleteMapping("/{id}")
    fun removeByMember(@PathVariable id: Long): ResponseEntity<AppMessageDTO> {

        val data = service.removeByMember(id)

        val body = AppMessageDTO(HttpStatus.OK.value(), data)

        return ResponseEntity.ok()
            .headers(HeaderUtils.createByAlert(applicationName, entityName, id.toString(), ApiType.DELETE))
            .body(body)
    }

}
