package com.weasleyclock.weasley.web.swagger

import com.weasleyclock.weasley.dto.AppMessageDTO
import com.weasleyclock.weasley.dto.BandDTO
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity


interface BandSwagger {

    @Operation(summary = "내가 속해있는 유저 그룹", description = "내가 속해있는 유저 그룹 목록 이다.")
    fun showGroupByUser(): ResponseEntity<AppMessageDTO>

    @Operation(summary = "그룹 하나의 유저 목록", description = "그룹 하나의 유저 목록을 출력")
    @ApiImplicitParams(
        value = [ApiImplicitParam(
            name = "id",
            value = "그룹 id value",
            example = "1"
        )]
    )
    fun showGroupByUsers(id: Long): ResponseEntity<AppMessageDTO>

    @Operation(summary = "그룹 전체 목록 출력", description = "그룹 전체의 목록 출력")
    fun showByGroups(): ResponseEntity<AppMessageDTO>

    @Operation(summary = "처음 그룹을 저장", description = "그룹을 만들때 사용하는 로직")
    fun saveByGroup(dto: BandDTO.Created): ResponseEntity<AppMessageDTO>

    @Operation(summary = "그룹을 업데이트 할때 사용하는 로직", description = "그룹에 대한 업데이트 로직")
    fun updateByGroup(id: Long, dto: BandDTO.Updated): ResponseEntity<AppMessageDTO>

    @Operation(summary = "그룹을 삭제 할때 사용", description = "그룹을 삭제 할때 사용 로직")
    fun removeByGroup(id: Long): ResponseEntity<AppMessageDTO>

    @Operation(summary = "유저 초대", description = "유저 초대하는 로직")
    fun inviteFromBandToUser(bandId : Long , userId : Long) : ResponseEntity<AppMessageDTO>

}
