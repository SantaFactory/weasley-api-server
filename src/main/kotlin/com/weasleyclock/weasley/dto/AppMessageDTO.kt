package com.weasleyclock.weasley.dto

import io.swagger.annotations.ApiModelProperty

data class AppMessageDTO(
    @ApiModelProperty(
        example = "상태코드"
    )
    val stats: Int,
    @ApiModelProperty(
        example = "데이터"
    )
    val data: Any
)
