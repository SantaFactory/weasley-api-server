package com.weasleyclock.weasley.web

import com.weasleyclock.weasley.dto.AppMessageDTO
import com.weasleyclock.weasley.dto.BandDTO
import com.weasleyclock.weasley.service.BandService
import com.weasleyclock.weasley.web.docs.BandDocs
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/band")
class BandResource(private val service: BandService) : BandDocs {

    @GetMapping("/self")
    override fun showGroupByUser(): ResponseEntity<AppMessageDTO> {
        val body = AppMessageDTO(HttpStatus.OK.value(), service.getGroupsBySelf())
        return ResponseEntity
            .ok()
            .body(body)
    }

    @GetMapping("/{id}/users")
    override fun showGroupByUsers(@PathVariable id: Long): ResponseEntity<AppMessageDTO> {
        val body = AppMessageDTO(HttpStatus.OK.value(), service.getGroupByUsers(id))
        return ResponseEntity
            .ok()
            .body(body)
    }

    @GetMapping("")
    override fun showByGroups(): ResponseEntity<AppMessageDTO> {
        val body = AppMessageDTO(HttpStatus.OK.value(), service.getAllByGroups())
        return ResponseEntity
            .ok()
            .body(body)
    }

    @PostMapping("")
    override fun saveByGroup(@RequestBody dto: BandDTO.Created): ResponseEntity<AppMessageDTO> {

        val data = service.createByGroup(dto)

        val body = AppMessageDTO(HttpStatus.OK.value(), data)

        return ResponseEntity.ok()
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
            .body(body)
    }

    @DeleteMapping("/{id}")
    override fun removeByGroup(@PathVariable id: Long): ResponseEntity<AppMessageDTO> {

        val data = service.removeByGroup(id)

        val body = AppMessageDTO(HttpStatus.OK.value(), data)

        return ResponseEntity.ok()
            .body(body)
    }

    @PostMapping("{bandId}/user/{userId}")
    override fun inviteFromBandToUser(
        @PathVariable bandId: Long,
        @PathVariable userId: Long
    ): ResponseEntity<AppMessageDTO> {
        val body = AppMessageDTO(HttpStatus.OK.value(), service.saveByBandUser(bandId, userId))

        return ResponseEntity
            .ok()
            .body(body)
    }

}
