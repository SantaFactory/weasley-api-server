package com.weasleyclock.weasleyclient.converter

import com.weasleyclock.weasleyclient.enmus.BaseEnum
import javax.persistence.AttributeConverter

abstract class BaseEnumConverter<E : BaseEnum<T>, T> : AttributeConverter<E, T> {

    protected abstract fun getEnumClass(): Class<E>

    private fun foundOf(t: T): E {
        val enums = getEnumClass().enumConstants as Array<E>
        for (e in enums) {
            if (e.getValue()!! == t) return e
        }
        throw NullPointerException()
    }

    override fun convertToDatabaseColumn(attribute: E): T = attribute.getValue()

    override fun convertToEntityAttribute(dbData: T): E = foundOf(dbData)
}
