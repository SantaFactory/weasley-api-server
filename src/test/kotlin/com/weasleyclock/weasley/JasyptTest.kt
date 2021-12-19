package com.weasleyclock.weasley

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor
import org.junit.jupiter.api.Test

class JasyptTest {

    @Test
    fun case() {

        val jasypt = StandardPBEStringEncryptor()
        val array = arrayOf(
            "",
            "",
            ""
        )
        jasypt.setPassword("")
        jasypt.setAlgorithm("PBEWithMD5AndDES")
        for (encrypt in array) {
            val encryptedText = jasypt.encrypt(encrypt)
            val realTest = jasypt.decrypt(encryptedText)
            println("encryptedText:  $encryptedText")
            println("realTest:  $realTest")
        }

    }

}
