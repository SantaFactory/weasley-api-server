package com.weasleyclock.weasley.web.docs

import com.weasleyclock.weasley.dto.AppMessageDTO
import com.weasleyclock.weasley.dto.BandDTO
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity


interface BandDocs {

    @Operation(summary = "밴드 하나의 정보", description = "밴드 하나의 정보")
    fun showByBandOne(id : Long): ResponseEntity<AppMessageDTO>

    @Operation(summary = "내가 속해있는 유저 그룹", description = "내가 속해있는 유저 그룹 목록 이다.")
    fun showGroupByUser(): ResponseEntity<AppMessageDTO>

    @Operation(summary = "밴드 하나의 유저 목록", description = "밴드 하나의 유저 목록을 출력")
    @ApiImplicitParams(
        value = [ApiImplicitParam(
            name = "id",
            value = "밴드 id value",
            example = "1"
        )]
    )
    fun showGroupByUsers(id: Long): ResponseEntity<AppMessageDTO>

    @Operation(summary = "밴드 전체 목록 출력", description = "밴드 전체의 목록 출력")
    fun showByGroups(): ResponseEntity<AppMessageDTO>

    @Operation(summary = "처음 밴드을 저장", description = "밴드을 만들때 사용하는 로직")
    fun saveByGroup(dto: BandDTO.Created): ResponseEntity<AppMessageDTO>

    @Operation(summary = "밴드을 업데이트 할때 사용하는 로직", description = "밴드에 대한 업데이트 로직")
    @ApiImplicitParams(
        value = [
            ApiImplicitParam(
                name = "id",
                value = "밴드의 아이디 값을 넣어주세요.",
                example = "1",
                required = true
            )
        ]
    )
    fun updateByGroup(id: Long, dto: BandDTO.Updated): ResponseEntity<AppMessageDTO>

    @Operation(summary = "그룹을 삭제 할때 사용", description = "그룹을 삭제 할때 사용 로직")
    @ApiImplicitParams(
        value = [
            ApiImplicitParam(
                name = "id",
                value = "밴드의 아이디 값을 넣어주세요.",
                example = "1",
                required = true
            )
        ]
    )
    fun removeByGroup(id: Long): ResponseEntity<AppMessageDTO>

    @Operation(summary = "유저 초대", description = "유저 초대하는 로직")
    @ApiImplicitParams(
        value = [
            ApiImplicitParam(
                name = "bandId",
                value = "밴드의 아이디 값을 넣어주세요.",
                example = "1",
                required = true
            ),
            ApiImplicitParam(
                name = "userId",
                value = "유저의 아이디 값을 넣어주세요.",
                example = "1",
                required = true
            )
        ]
    )
    fun inviteFromBandToUser(bandId: Long, userId: Long): ResponseEntity<AppMessageDTO>

}
