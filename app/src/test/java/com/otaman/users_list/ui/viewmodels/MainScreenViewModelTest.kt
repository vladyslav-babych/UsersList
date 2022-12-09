package com.otaman.users_list.ui.viewmodels

import com.otaman.users_list.MainCoroutineRule
import com.otaman.users_list.domain.models.UserData
import com.otaman.users_list.domain.models.UserId
import com.otaman.users_list.domain.models.UserProfile
import com.otaman.users_list.domain.repository.UsersRepository
import com.otaman.users_list.domain.util.Resource
import com.otaman.users_list.ui.states.UsersState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainScreenViewModelTest {
    @MockK
    private lateinit var repository: UsersRepository

    private lateinit var viewModel: MainScreenViewModel

    @get:Rule
    var mainDispatcherRule = MainCoroutineRule()

    private lateinit var idModel: UserId
    private lateinit var idResult: Resource<UserId>

    private lateinit var profileModel: UserProfile
    private lateinit var profileData: List<UserData>
    private lateinit var profileSuccessResult: Resource<UserProfile>
    private lateinit var profileErrorResult: Resource<UserProfile>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        idModel = UserId("success", listOf("test_id1", "test_idle_id"))
        idResult = Resource.Success(idModel)

        profileData = listOf(
            UserData(
                id = "test_id1",
                firstName = "TestFirstName",
                lastName = "TestLastName",
                age = 19,
                gender = "Male",
                country = "Canada"
            )
        )
        profileModel = UserProfile("success", profileData[0])
        profileSuccessResult = Resource.Success(profileModel)
        profileErrorResult = Resource.Error("Error")

        coEvery { repository.getUsersIds() } returns idResult
        coEvery { repository.getUserProfileById("test_id1") } returns profileSuccessResult
        coEvery { repository.getUserProfileById("test_idle_id") } returns profileErrorResult

        viewModel = MainScreenViewModel(repository)
    }

    @Test
    fun getUsersIdsAndUserProfile_success() = runTest {

        advanceUntilIdle()

        coVerify { repository.getUsersIds() }
        coVerify { repository.getUserProfileById("test_id1") }
    }

    @Test
    fun getUsersIds_error() = runTest {
        idResult = Resource.Error("Error")

        coEvery { repository.getUsersIds() } returns idResult
        advanceUntilIdle()
        val expectedState = UsersState.Error("Error")

        assertThat(viewModel.usersState.value, equalTo(expectedState))
    }

    @Test
    fun getUsersIds_loading() = runTest {
        val expectedState = UsersState.Loading

        assertThat(viewModel.usersState.value, equalTo(expectedState))
    }

    @Test
    fun getUserProfile_success() = runTest {

        advanceUntilIdle()
        val expectedState = UsersState.UsersProfilesData(listOf(profileModel), 1)

        assertThat(viewModel.usersState.value, equalTo(expectedState))
    }
}