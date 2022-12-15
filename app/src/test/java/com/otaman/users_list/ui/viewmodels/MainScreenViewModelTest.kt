package com.otaman.users_list.ui.viewmodels

import com.otaman.users_list.MainCoroutineRule
import com.otaman.users_list.domain.models.UserProfile
import com.otaman.users_list.fakes.FakeUsersRepository
import com.otaman.users_list.ui.states.UsersState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainScreenViewModelTest {

    private lateinit var fakeRepository: FakeUsersRepository
    private lateinit var viewModel: MainScreenViewModel

    @get:Rule
    var mainDispatcherRule = MainCoroutineRule()

    @Before
    fun setUp() {
        fakeRepository = FakeUsersRepository()
        viewModel = MainScreenViewModel(fakeRepository)
    }

    @Test
    fun getUsersIds_error() = runTest {
        fakeRepository.getUserIdsResult = false
        viewModel.getUsersIds()
        advanceUntilIdle()
        val expectedState = UsersState.Error::class.java

        assertThat(viewModel.usersState.value, instanceOf(expectedState))
    }

    @Test
    fun getUsersIds_loading() = runTest {
        val expectedState = UsersState.Loading

        assertThat(viewModel.usersState.value, equalTo(expectedState))
    }

    @Test
    fun getUserProfile_success() = runTest {
        viewModel.getUsersIds()
        advanceUntilIdle()
        val userProfiles =  fakeRepository.profiles.values.map { userData ->
            UserProfile("success", userData)
        }
        val expectedResult = UsersState.UsersProfilesData(userProfiles, 0)

        assertThat(viewModel.usersState.value, equalTo(expectedResult))
    }
}