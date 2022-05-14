package com.weasleyclock.weasleyclient.converter

import com.weasleyclock.weasleyclient.enmus.UserType
import javax.persistence.Converter

@Converter(autoApply = true)
class UserTypeConverter : BaseEnumConverter<UserType, String>() {
    override fun getEnumClass(): Class<UserType> = UserType::class.java
}
