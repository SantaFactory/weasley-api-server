package com.weasleyclock.weasley.dto

class UserDTO {
    data class Info(val email: String?, val sub: String?, val accessToken: String?, val refreshToken: String?)
}
