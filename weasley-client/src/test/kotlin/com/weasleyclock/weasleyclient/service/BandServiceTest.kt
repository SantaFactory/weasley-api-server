package com.weasleyclock.weasleyclient.service

import com.weasleyclock.weasleyclient.config.security.DomainUserDetail
import com.weasleyclock.weasleyclient.domain.*
import com.weasleyclock.weasleyclient.dto.BandDTO
import com.weasleyclock.weasleyclient.dto.IBandUserCount
import com.weasleyclock.weasleyclient.dto.IOnlyBandUser
import com.weasleyclock.weasleyclient.enmus.AppRole
import com.weasleyclock.weasleyclient.enmus.RoleName
import com.weasleyclock.weasleyclient.mock.BandUserCount
import com.weasleyclock.weasleyclient.mock.OnlyBandUser
import com.weasleyclock.weasleyclient.mock.UserAndBandRole
import com.weasleyclock.weasleyclient.repository.BandRepository
import com.weasleyclock.weasleyclient.repository.MemberRepository
import com.weasleyclock.weasleyclient.repository.UserRepository
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.MockitoAnnotations
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.transaction.annotation.Transactional
import java.util.*

@DisplayName("밴드 서비스 테스트 코드")
@ExtendWith(MockKExtension::class)
internal open class BandServiceTest {

    @InjectMockKs
    lateinit var bandService: BandService

    @MockK
    private lateinit var bandRepository: BandRepository

    @MockK
    private lateinit var userRepository: UserRepository

    @MockK
    private lateinit var memberRepository: MemberRepository

    private val DEFAULT_ID = 1L

    private val DEFAULT_TITLE = "test"

    private val authoritySet = listOf(Authority(AppRole.ADMIN.name)).toMutableSet()

    private val user = User(1, "admin", "admin", authoritySet)

    private val members = listOf(
        Member(user.getId(), DEFAULT_ID, BandRole(RoleName.LEADER)),
        Member(2, DEFAULT_ID, BandRole(RoleName.SUB_LEADER)),
    ).toMutableSet()

    private val bandOptional = Optional.of(Band(DEFAULT_TITLE).id(DEFAULT_ID).memberSet(members))

    private fun setUpUser() {

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
    fun `하나의 밴드 안에 유저 리스트 출력`() {

        val userBandRoleSet = listOf(
            UserAndBandRole(
                user.getId(), user.getEmail().toString(), AppRole.ADMIN.name
            ) as IOnlyBandUser.UserAndBandRole
        ).toMutableSet()

        val givenData = OnlyBandUser(userBandRoleSet)

        every {
            bandRepository.findById(DEFAULT_ID, IOnlyBandUser::class.java)
        } returns givenData

        val whenData = bandService.getBandByUsers(DEFAULT_ID)

        assertThat(whenData).isEqualTo(givenData.getMembers())

        verify {
            bandRepository.findById(DEFAULT_ID, IOnlyBandUser::class.java)
        }
        confirmVerified(bandRepository)

    }

    @Test
    fun createByBand() {
    }

    @Test
    fun `밴드 업데이트`() {

        every {
            bandRepository.findById(DEFAULT_ID)
        } returns bandOptional

        val dto = BandDTO.Updated(DEFAULT_TITLE + "_1")

        val value = bandService.updateBand(DEFAULT_ID, dto)

        assertThat(value).isEqualTo(bandOptional.orElseThrow())

        Assertions.assertEquals(value!!.getTitle(), bandOptional.get().getTitle())

        verify {
            bandRepository.findById(DEFAULT_ID)
        }
        confirmVerified(bandRepository)

    }

    @Test
    @Transactional
    open fun `밴드 삭제`() {

        val removeId = DEFAULT_ID

        val bandRole = BandRole(RoleName.LEADER)

        val member = Member(user.getId(), removeId, bandRole)

        every {
            memberRepository.findByBand_IdAndUser_IdAndBandRole_Title(removeId, user.getId(), RoleName.LEADER)
        } returns member

        every {
            bandRepository.deleteById(removeId)
        } returns Unit

        val mock = bandService.removeBand(removeId)

        assertThat(mock).isEqualTo(removeId)

        verify {
            memberRepository.findByBand_IdAndUser_IdAndBandRole_Title(removeId, user.getId(), RoleName.LEADER)
        }
        confirmVerified(memberRepository)

        verify {
            bandRepository.deleteById(removeId)
        }
        confirmVerified(bandRepository)

    }

    @Test
    fun `밴드 나가기`() {

        every {
            bandRepository.findById(DEFAULT_ID)
        } returns bandOptional

        val whenData = bandService.exitBand(DEFAULT_ID)

        assertThat(bandOptional.get()).isEqualTo(whenData)

        verify { bandRepository.findById(DEFAULT_ID) }
        confirmVerified(bandRepository)

    }

    @Test
    fun `밴드에서 추방하기`() {

        val removeUserId = 2L

        every {
            bandRepository.findById(DEFAULT_ID)
        } returns bandOptional

        val whenData = bandService.exileBandMember(removeUserId, DEFAULT_ID)

        assertThat(whenData).isEqualTo(bandOptional.get())

        Assertions.assertEquals(whenData!!.getMemberSet().size, bandOptional.get().getMemberSet().size)

        verify {
            bandRepository.findById(DEFAULT_ID)
        }
        confirmVerified(bandRepository)

    }

    @Test
    fun saveByMember() {
    }
}
