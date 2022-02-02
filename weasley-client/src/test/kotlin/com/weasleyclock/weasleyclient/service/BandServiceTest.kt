package com.weasleyclock.weasleyclient.service

import com.mysema.commons.lang.Assert.assertThat
import com.weasleyclock.weasleyclient.domain.Band
import com.weasleyclock.weasleyclient.dto.BandDTO
import com.weasleyclock.weasleyclient.repository.BandRepository
import com.weasleyclock.weasleyclient.repository.MemberRepository
import com.weasleyclock.weasleyclient.repository.UserRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.MockitoAnnotations
import io.mockk.confirmVerified

@ExtendWith(MockKExtension::class)
internal class BandServiceTest {

    @InjectMockKs
    lateinit var bandService: BandService

    @MockK
    private lateinit var bandRepository: BandRepository

    @MockK
    private lateinit var userRepository: UserRepository

    @MockK
    private lateinit var memberRepository: MemberRepository

    private var DEFAULT_ID = 1L

    private var DEFAULT_TITLE = "test"

    @BeforeEach
    fun `앞서서`() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `하나의 밴드 조회`() {

        val bandOne: BandDTO.Grouping = BandDTO.Grouping(DEFAULT_ID, DEFAULT_TITLE)

        every {
            bandRepository.selectBandOne(1L)
        } returns bandOne

        val whenData = bandService.getBandOne(1L)

        assertThat(whenData).isEqualTo(bandOne)

        verify {
            bandRepository.selectBandOne(DEFAULT_ID)
        }
        confirmVerified(bandRepository)

    }


    @Test
    fun getAllByGroups() {
    }

    @Test
    fun getGroupsBySelf() {
    }

    @Test
    fun getBandByUsers() {
    }

    @Test
    fun createByBand() {
    }

    @Test
    fun updateBand() {
    }

    @Test
    fun removeBand() {
    }

    @Test
    fun exitBand() {
    }

    @Test
    fun exileBandMember() {
    }

    @Test
    fun saveByMember() {
    }
}
