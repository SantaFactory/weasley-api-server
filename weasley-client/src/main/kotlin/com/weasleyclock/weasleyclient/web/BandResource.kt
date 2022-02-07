package com.weasleyclock.weasleyclient.web

import com.weasleyclock.weasleyclient.dto.AppMessageDTO
import com.weasleyclock.weasleyclient.dto.BandDTO
import com.weasleyclock.weasleyclient.service.BandService
import com.weasleyclock.weasleyclient.web.docs.BandDocs
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

    @GetMapping("/{id}/users")
    override fun showBandByUsers(@PathVariable id: Long): ResponseEntity<AppMessageDTO> {
        val body = AppMessageDTO(HttpStatus.OK.value(), service.getBandByUsers(id))
        return ResponseEntity
            .ok()
            .body(body)
    }

    @GetMapping("/self")
    override fun showBandByUser(): ResponseEntity<AppMessageDTO> {
        val body = AppMessageDTO(HttpStatus.OK.value(), service.getMyBands())
        return ResponseEntity
            .ok()
            .body(body)
    }

    @GetMapping("")
    override fun showByAllBands(): ResponseEntity<AppMessageDTO> {
        val body = AppMessageDTO(HttpStatus.OK.value(), service.getAllByBands())
        return ResponseEntity
            .ok()
            .body(body)
    }

    @PostMapping("")
    override fun saveByBand(@RequestBody dto: BandDTO.Created): ResponseEntity<AppMessageDTO> {
        val body = AppMessageDTO(HttpStatus.OK.value(), service.createByBand(dto))
        return ResponseEntity.ok()
            .body(body)
    }

    @PutMapping("/{id}")
    override fun updateByBand(
        @PathVariable id: Long,
        @RequestBody dto: BandDTO.Updated
    ): ResponseEntity<AppMessageDTO> {
        val body = AppMessageDTO(HttpStatus.OK.value(), service.updateBand(id, dto))
        return ResponseEntity.ok()
            .body(body)
    }

    @DeleteMapping("/{id}")
    override fun removeByBand(@PathVariable id: Long): ResponseEntity<AppMessageDTO> {
        val body = AppMessageDTO(HttpStatus.OK.value(), service.removeBand(id))
        return ResponseEntity.ok()
            .body(body)
    }

    @PostMapping("{bandId}/user/{userId}")
    override fun inviteFromMember(
        @PathVariable bandId: Long,
        @PathVariable userId: Long
    ): ResponseEntity<AppMessageDTO> {
        val body = AppMessageDTO(HttpStatus.OK.value(), service.saveMember(bandId, userId))
        return ResponseEntity
            .ok()
            .body(body)
    }

    @PutMapping("/{bandId}/members")
    fun exitBand(@PathVariable bandId: Long): ResponseEntity<AppMessageDTO> {
        val body = AppMessageDTO(HttpStatus.OK.value(), service.exitBand(bandId))
        return ResponseEntity
            .ok()
            .body(body)
    }

    @DeleteMapping("/{bandId}/member/{userId}")
    fun exileBandMember(@PathVariable bandId: Long, @PathVariable userId: Long): ResponseEntity<AppMessageDTO> {
        val body = AppMessageDTO(HttpStatus.OK.value(), service.exileBandMember(bandId, userId))
        return ResponseEntity
            .ok()
            .body(body)
    }
}
