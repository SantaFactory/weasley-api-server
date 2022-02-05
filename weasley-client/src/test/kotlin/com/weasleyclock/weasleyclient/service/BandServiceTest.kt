package com.weasleyclock.weasleyclient.service

import com.mysema.commons.lang.Assert.assertThat
import com.weasleyclock.weasleyclient.config.security.DomainUserDetail
import com.weasleyclock.weasleyclient.domain.Auth
import com.weasleyclock.weasleyclient.domain.Band
import com.weasleyclock.weasleyclient.domain.User
import com.weasleyclock.weasleyclient.dto.BandDTO
import com.weasleyclock.weasleyclient.dto.IBandUserCount
import com.weasleyclock.weasleyclient.enmus.AppRole
import com.weasleyclock.weasleyclient.mock.BandUserCount
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder

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

    private final val DEFAULT_ID = 1L

    private final val DEFAULT_TITLE = "test"

    private fun setUpUser() {

        val authSet = listOf(Auth(AppRole.ADMIN.name)).toMutableSet()

        val user = User(1, "admin", "admin", authSet)

        val jwtUser = DomainUserDetail(user)

        val userToken = UsernamePasswordAuthenticationToken(jwtUser, null, jwtUser.authorities)

        SecurityContextHolder.getContext().authentication = userToken
    }

    @BeforeEach
    fun `셋업`() {
        MockitoAnnotations.openMocks(this)
        setUpUser()
    }

    @Test
    fun `하나의 밴드 조회`() {

        // given
        val bandOne: BandDTO.Grouping = BandDTO.Grouping(DEFAULT_ID, DEFAULT_TITLE)

        every {
            bandRepository.selectBandOne(1L)
        } returns bandOne

        // when
        val whenData = bandService.getBandOne(1L)

        // then
        assertThat(whenData).isEqualTo(bandOne)

        verify {
            bandRepository.selectBandOne(DEFAULT_ID)
        }
        confirmVerified(bandRepository)

    }

    @Test
    fun `전체 밴드 조회`() {

        // given
        val list = listOf(BandUserCount(1L, DEFAULT_TITLE, 1))

        every {
            bandRepository.findBy(IBandUserCount::class.java)
        } returns list

        // when
        val whenData = bandService.getAllByBands()

        // then
        assertThat(list).isEqualTo(whenData)

        verify {
            bandRepository.findBy(IBandUserCount::class.java)
        }
        confirmVerified(bandRepository)

    }

    @Test
    fun `내가 소속 되어있는 밴드 조회`() {

        // given
        val list = listOf(BandUserCount(1L, DEFAULT_TITLE, 1))

        every {
            bandRepository.findByMemberSet_User_Id(DEFAULT_ID, IBandUserCount::class.java)
        } returns list

        // when
        val whenData = bandService.getMyBands()

        // then
        assertThat(whenData).isEqualTo(list)

        verify {
            bandRepository.findByMemberSet_User_Id(1L, IBandUserCount::class.java)
        }
        confirmVerified(bandRepository)

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
