package com.weasleyclock.weasley.web

import com.weasleyclock.weasley.dto.AppMessageDTO
import com.weasleyclock.weasley.enmus.ApiType
import com.weasleyclock.weasley.utils.HeaderUtils
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class StartResource {

    @Value("\${spring.application.name}")
    private val applicationName: String? = null

    private val entityName = "start"

    @GetMapping("/start")
    @Operation(summary = "test api start", description = "swagger example")
    fun showByStart(): ResponseEntity<AppMessageDTO> {
        val data = "start project"
        val appMessageDTO = AppMessageDTO(HttpStatus.OK.value(), data)
//        return ResponseEntity.ok()
//            .headers(HeaderUtils.createByAlert(applicationName, entityName, "start", ApiType.CREATE))
//            .body(appMessageDTO)
        throw NullPointerException()
    }

}
