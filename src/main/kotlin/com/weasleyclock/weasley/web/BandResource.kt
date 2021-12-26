package com.weasleyclock.weasley.web

import com.weasleyclock.weasley.dto.AppMessageDTO
import com.weasleyclock.weasley.dto.BandDTO
import com.weasleyclock.weasley.service.BandService
import com.weasleyclock.weasley.web.docs.BandDocs
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/band")
class BandResource(private val service: BandService) : BandDocs {

    @GetMapping("/{id}")
    override fun showByBandOne(@PathVariable id: Long): ResponseEntity<AppMessageDTO> {
        val body = AppMessageDTO(HttpStatus.OK.value(), service.getBandOne(id))
        return ResponseEntity
            .ok()
            .body(body)
    }

    @GetMapping("/self")
    override fun showBandByUser(): ResponseEntity<AppMessageDTO> {
        val body = AppMessageDTO(HttpStatus.OK.value(), service.getGroupsBySelf())
        return ResponseEntity
            .ok()
            .body(body)
    }

    @GetMapping("/{id}/users")
    override fun showBandByUsers(@PathVariable id: Long): ResponseEntity<AppMessageDTO> {
        val body = AppMessageDTO(HttpStatus.OK.value(), service.getGroupByUsers(id))
        return ResponseEntity
            .ok()
            .body(body)
    }

    @GetMapping("")
    override fun showByAllBands(): ResponseEntity<AppMessageDTO> {
        val body = AppMessageDTO(HttpStatus.OK.value(), service.getAllByGroups())
        return ResponseEntity
            .ok()
            .body(body)
    }

    @PostMapping("")
    override fun saveByBand(@RequestBody dto: BandDTO.Created): ResponseEntity<AppMessageDTO> {

        val data = service.createByGroup(dto)

        val body = AppMessageDTO(HttpStatus.OK.value(), data)

        return ResponseEntity.ok()
            .body(body)
    }

    @PutMapping("/{id}")
    override fun updateByBand(
        @PathVariable id: Long,
        @RequestBody dto: BandDTO.Updated
    ): ResponseEntity<AppMessageDTO> {

        val data = service.updateByGroup(id, dto)

        val body = AppMessageDTO(HttpStatus.OK.value(), data)

        return ResponseEntity.ok()
            .body(body)
    }

    @DeleteMapping("/{id}")
    override fun removeByBand(@PathVariable id: Long): ResponseEntity<AppMessageDTO> {

        val data = service.removeByGroup(id)

        val body = AppMessageDTO(HttpStatus.OK.value(), data)

        return ResponseEntity.ok()
            .body(body)
    }

    @PostMapping("{bandId}/user/{userId}")
    override fun inviteFromMember(
        @PathVariable bandId: Long,
        @PathVariable userId: Long
    ): ResponseEntity<AppMessageDTO> {
        val body = AppMessageDTO(HttpStatus.OK.value(), service.saveByMember(bandId, userId))

        return ResponseEntity
            .ok()
            .body(body)
    }

}
