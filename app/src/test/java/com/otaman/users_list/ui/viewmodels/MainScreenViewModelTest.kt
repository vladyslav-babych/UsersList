package com.otaman.users_list.ui.viewmodels

import com.otaman.users_list.MainCoroutineRule
import com.otaman.users_list.domain.models.UserData
import com.otaman.users_list.domain.models.UserId
import com.otaman.users_list.domain.models.UserProfile
import com.otaman.users_list.domain.repository.UsersRepository
import com.otaman.users_list.domain.util.Resource
import com.otaman.users_list.ui.states.UserProfileState
import com.otaman.users_list.ui.states.UsersIdsState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
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
    private lateinit var profileData: UserData
    private lateinit var profileResult: Resource<UserProfile>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        idModel = UserId("success", listOf())
        idResult = Resource.Success(idModel)

        profileData = mockk()
        profileModel = UserProfile("success", profileData)
        profileResult = Resource.Success(profileModel)

        coEvery { repository.getUsersIds() } returns idResult
        coEvery { repository.getUserProfileById("") } returns profileResult

        viewModel = MainScreenViewModel(repository)
    }

    @Test
    fun getUsersIds_success() = runTest {

        advanceUntilIdle()
        val expectedState = UsersIdsState.UsersIdsData(idModel)

        assertThat(viewModel.usersIdsState.value, equalTo(expectedState))
    }

    @Test
    fun getUsersIds_error() = runTest {
        idResult = Resource.Error("Error")

        coEvery { repository.getUsersIds() } returns idResult
        advanceUntilIdle()
        val expectedState = UsersIdsState.Error("Error")

        assertThat(viewModel.usersIdsState.value, equalTo(expectedState))
    }

    @Test
    fun getUsersIds_loading() = runTest {
        val expectedState = UsersIdsState.Loading

        assertThat(viewModel.usersIdsState.value, equalTo(expectedState))
    }

    @Test
    fun getUsersProfile_loading() = runTest {
        val expectedState = UserProfileState.Loading

        assertThat(viewModel.userProfileState.value, equalTo(expectedState))
    }
}