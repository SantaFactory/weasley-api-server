package com.weasleyclock.weasleyclient.converter

import com.weasleyclock.weasleyclient.enmus.BandRoleType
import javax.persistence.Converter

@Converter(autoApply = true)
class BandRoleConverter : BaseEnumConverter<BandRoleType, String>() {
    override fun getEnumClass(): Class<BandRoleType> = BandRoleType::class.java
}
