package com.weasleyclock.weasley.web

import com.weasleyclock.weasley.dto.AppMessageDTO
import com.weasleyclock.weasley.dto.GroupDTO
import com.weasleyclock.weasley.enmus.ApiTypes
import com.weasleyclock.weasley.service.BandService
import com.weasleyclock.weasley.utils.HeaderUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/band")
class BandResource(private val service: BandService) {

    @Value("\${spring.application.name}")
    private val applicationName: String? = null
    private val entityName = "band"

    @GetMapping("/self")
    fun showGroupByUser(): ResponseEntity<AppMessageDTO> {

//        val body = AppMessageDTO(HttpStatus.OK.value(), service.getGroupsBySelf())
        val body = AppMessageDTO(HttpStatus.OK.value(), "")

        return ResponseEntity
            .ok()
            .headers(HeaderUtils.createByAlert(applicationName, entityName, "self", ApiTypes.SELECT))
            .body(body)
    }

    @GetMapping("/{id}/users")
    fun showGroupByUsers(@PathVariable id: Long): ResponseEntity<AppMessageDTO> {

//        val body = AppMessageDTO(HttpStatus.OK.value(), service.getGroupByUsers(id))
        val body = AppMessageDTO(HttpStatus.OK.value(), "")

        return ResponseEntity
            .ok()
            .headers(HeaderUtils.createByAlert(applicationName, entityName, id.toString(), ApiTypes.SELECT))
            .body(body)
    }

    @GetMapping("")
    fun showByGroups(): ResponseEntity<AppMessageDTO> {

        val body = AppMessageDTO(HttpStatus.OK.value(), service.getAllByGroups())

        return ResponseEntity
            .ok()
            .headers(HeaderUtils.createByAlert(applicationName, entityName, "ALL", ApiTypes.SELECT))
            .body(body)
    }

    @PostMapping("")
    fun saveByGroup(@RequestBody dto: GroupDTO.Created): ResponseEntity<AppMessageDTO> {

        val data = service.createByGroup(dto)

        val body = AppMessageDTO(HttpStatus.OK.value(), data)

        return ResponseEntity.ok()
            .headers(HeaderUtils.createByAlert(applicationName, entityName, data.title.toString(), ApiTypes.CREATE))
            .body(body)
    }

    @PutMapping("/{id}")
    fun updateByGroup(@PathVariable id: Long, @RequestBody dto: GroupDTO.Updated): ResponseEntity<AppMessageDTO> {

        val data = service.updateByGroup(id, dto)

        val body = AppMessageDTO(HttpStatus.OK.value(), data)

        return ResponseEntity.ok()
            .headers(HeaderUtils.createByAlert(applicationName, entityName, id.toString(), ApiTypes.UPDATE))
            .body(body)
    }

    @DeleteMapping("/{id}")
    fun removeByGroup(@PathVariable id: Long): ResponseEntity<AppMessageDTO> {

        val data = service.removeByGroup(id)

        val body = AppMessageDTO(HttpStatus.OK.value(), data)

        return ResponseEntity.ok()
            .headers(HeaderUtils.createByAlert(applicationName, entityName, id.toString(), ApiTypes.DELETE))
            .body(body)
    }

}
