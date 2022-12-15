package com.otaman.users_list.networking

import com.otaman.users_list.MainCoroutineRule
import com.otaman.users_list.domain.models.UserData
import com.otaman.users_list.domain.models.UserId
import com.otaman.users_list.domain.models.UserProfile
import com.otaman.users_list.domain.repository.UsersRepository
import com.otaman.users_list.domain.util.Resource
import com.otaman.users_list.networking.responses.UserProfileResponse
import com.otaman.users_list.networking.responses.UserProfileResponse.User
import com.otaman.users_list.networking.responses.UsersIdsResponse
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class UsersRepositoryTest {

    @get:Rule
    var mainDispatcherRule = MainCoroutineRule()

    @MockK
    private lateinit var usersService: UsersService
    @MockK
    private lateinit var jwtManager: JWTManager

    private lateinit var repository: UsersRepository
    private lateinit var token: String
    private lateinit var ids: List<String>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        token = "test_token"

        ids = listOf("test_id1", "test_id2")
        val idsResponse = UsersIdsResponse(
            status = "success",
            ids = ids
        )

        val profileResponse = UserProfileResponse(
            status = "success",
            data = User(
                id = "test_id1",
                firstName = "Name1",
                lastName = "LastName1",
                age = 20,
                gender = "Male",
                country = "USA"
            )
        )

        every { jwtManager.getToken() } returns token
        coEvery { usersService.getUsersIds("Bearer $token") } returns idsResponse
        coEvery { usersService.getUserProfileById(ids[0], "Bearer $token") } returns profileResponse

        repository = UsersRepositoryImpl(usersService, jwtManager)
    }

    @Test
    fun getUserIds_getsUserIds() = runTest {
        val expectedIds = Resource.Success(data = UserId(status = "success", ids))

        assertThat(repository.getUsersIds(), equalTo(expectedIds))
    }

    @Test
    fun getUserProfile_getsUserProfile() = runTest {
        val userData = UserData(
            id = "test_id1",
            firstName = "Name1",
            lastName = "LastName1",
            age = 20,
            gender = "Male",
            country = "USA"
        )
        val expectedProfile = Resource.Success(data = UserProfile(status = "success", profileData = userData))

        assertThat(repository.getUserProfileById(ids[0]), equalTo(expectedProfile))
    }
}