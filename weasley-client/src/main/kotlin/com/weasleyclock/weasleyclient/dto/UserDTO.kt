package com.weasleyclock.weasleyclient.dto

class UserDTO {

    data class Info(val email: String?, val sub: String?, val accessToken: String?, val refreshToken: String?)

    data class AccessToken(val accessToken: String?, val refreshToken: String?)

}
