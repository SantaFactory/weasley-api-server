package com.weasleyclock.weasley.web

import com.weasleyclock.weasley.dto.AppMessageDTO
import com.weasleyclock.weasley.dto.BandDTO
import com.weasleyclock.weasley.enmus.ApiTypes
import com.weasleyclock.weasley.service.BandService
import com.weasleyclock.weasley.utils.HeaderUtils
import com.weasleyclock.weasley.web.swagger.BandSwagger
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/band")
class BandResource(private val service: BandService) : BandSwagger {

    @Value("\${spring.application.name}")
    private val applicationName: String? = null
    private val entityName = "band"

    @GetMapping("/self")
    override fun showGroupByUser(): ResponseEntity<AppMessageDTO> {
        val body = AppMessageDTO(HttpStatus.OK.value(), service.getGroupsBySelf())
        return ResponseEntity
            .ok()
            .headers(HeaderUtils.createByAlert(applicationName, entityName, "self", ApiTypes.SELECT))
            .body(body)
    }

    @GetMapping("/{id}/users")
    override fun showGroupByUsers(@PathVariable id: Long): ResponseEntity<AppMessageDTO> {
        val body = AppMessageDTO(HttpStatus.OK.value(), service.getGroupByUsers(id))
        return ResponseEntity
            .ok()
            .headers(HeaderUtils.createByAlert(applicationName, entityName, id.toString(), ApiTypes.SELECT))
            .body(body)
    }

    @GetMapping("")
    override fun showByGroups(): ResponseEntity<AppMessageDTO> {
        val body = AppMessageDTO(HttpStatus.OK.value(), service.getAllByGroups())
        return ResponseEntity
            .ok()
            .headers(HeaderUtils.createByAlert(applicationName, entityName, "ALL", ApiTypes.SELECT))
            .body(body)
    }

    @PostMapping("")
    override fun saveByGroup(@RequestBody dto: BandDTO.Created): ResponseEntity<AppMessageDTO> {

        val data = service.createByGroup(dto)

        val body = AppMessageDTO(HttpStatus.OK.value(), data)

        return ResponseEntity.ok()
            .headers(HeaderUtils.createByAlert(applicationName, entityName, data.title.toString(), ApiTypes.CREATE))
            .body(body)
    }

    @PutMapping("/{id}")
    override fun updateByGroup(
        @PathVariable id: Long,
        @RequestBody dto: BandDTO.Updated
    ): ResponseEntity<AppMessageDTO> {

        val data = service.updateByGroup(id, dto)

        val body = AppMessageDTO(HttpStatus.OK.value(), data)

        return ResponseEntity.ok()
            .headers(HeaderUtils.createByAlert(applicationName, entityName, id.toString(), ApiTypes.UPDATE))
            .body(body)
    }

    @DeleteMapping("/{id}")
    override fun removeByGroup(@PathVariable id: Long): ResponseEntity<AppMessageDTO> {

        val data = service.removeByGroup(id)

        val body = AppMessageDTO(HttpStatus.OK.value(), data)

        return ResponseEntity.ok()
            .headers(HeaderUtils.createByAlert(applicationName, entityName, id.toString(), ApiTypes.DELETE))
            .body(body)
    }

}
