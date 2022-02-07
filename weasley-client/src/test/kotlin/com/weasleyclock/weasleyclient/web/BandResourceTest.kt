package com.weasleyclock.weasleyclient.web

import com.weasleyclock.weasleyclient.mock.UserMock
import com.weasleyclock.weasleyclient.repository.BandRepository
import io.mockk.every
import org.apache.catalina.security.SecurityConfig
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

//@WebMvcTest(BandResource::class)
//@AutoConfigureMockMvc

@SpringBootTest
@WebAppConfiguration
@ContextConfiguration(classes = [SecurityConfig::class])
internal class BandResourceTest {

//    @MockBean
//    private lateinit var bandRepository: BandRepository

    @Autowired
    private lateinit var context: WebApplicationContext

    private var mvc: MockMvc? = null

    private val apiUri = "/api/band"

    private val DEFAULT_ID = 1L

    @BeforeEach
    fun `셋업`() {

        mvc = MockMvcBuilders
            .webAppContextSetup(context)
            .apply<DefaultMockMvcBuilder>(springSecurity())
            .build()
    }

    @Test
    @Throws(Exception::class)
    fun `밴드 하나만 조회`() {

//        every {
//            bandRepository.selectBandOne(1L)
//        } returns UserMock.bandOne

//        mockMvc.get("$apiUri/$DEFAULT_ID") {
//            contentType = MediaType.APPLICATION_JSON
//        }
//            .andDo {
//                print()
//            }


    }

    @Test
    fun showBandByUsers() {
    }

    @Test
    fun showBandByUser() {
    }

    @Test
    fun showByAllBands() {
    }

    @Test
    fun saveByBand() {
    }

    @Test
    fun updateByBand() {
    }

    @Test
    fun removeByBand() {
    }

    @Test
    fun inviteFromMember() {
    }

    @Test
    fun exitBand() {
    }

    @Test
    fun exileBandMember() {
    }
}
