package com.weasleyclock.weasley.domain.convert

import com.weasleyclock.weasley.enmus.UserTypes
import java.util.*
import javax.persistence.AttributeConverter
import javax.persistence.Convert

@Convert
class UserTypeConvert : AttributeConverter<UserTypes, String> {

    override fun convertToDatabaseColumn(attribute: UserTypes?): String {
        return Optional.ofNullable(attribute).orElse(UserTypes.DEFAULT).value
    }

    override fun convertToEntityAttribute(dbData: String?): UserTypes {
        return UserTypes.selectOf(dbData)
    }

}
