package com.weasleyclock.weasley.domain.convert

import com.weasleyclock.weasley.enmus.UserType
import java.util.*
import javax.persistence.AttributeConverter
import javax.persistence.Convert

@Convert
class UserTypeConvert : AttributeConverter<UserType, String> {

    override fun convertToDatabaseColumn(attribute: UserType?): String {
        return Optional.ofNullable(attribute).orElse(UserType.DEFAULT).value
    }

    override fun convertToEntityAttribute(dbData: String?): UserType {
        return UserType.selectOf(dbData)
    }

}
