package com.otaman.users_list.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otaman.users_list.domain.models.UserProfile
import com.otaman.users_list.domain.repository.UsersRepository
import com.otaman.users_list.domain.util.Resource
import com.otaman.users_list.ui.states.UserProfileState
import com.otaman.users_list.ui.states.UsersIdsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val repository: UsersRepository
): ViewModel() {

    private val _usersIdsState: MutableState<UsersIdsState> = mutableStateOf(UsersIdsState.Loading)
    val usersIdsState: State<UsersIdsState> = _usersIdsState

    private val _userProfileState: MutableState<UserProfileState> = mutableStateOf(UserProfileState.Loading)
    val userProfileState: State<UserProfileState> = _userProfileState

    init {
        getUsersIds()
    }

    fun updateUsers() {
        getUsersIds()
    }

    private fun getUsersIds() = viewModelScope.launch {
        when(val result = repository.getUsersIds()) {
            is Resource.Success -> {
                _usersIdsState.value = result.data.let { data ->
                    getUserProfileById(data.data)
                    UsersIdsState.UsersIdsData(usersIdsData = data)
                }
            }
            is Resource.Error -> {
                _usersIdsState.value = result.message.let { msg ->
                    UsersIdsState.Error(message = msg)
                }
            }
        }
    }

    private fun getUserProfileById(usersIds: List<String>) = viewModelScope.launch {

        val profilesResource: MutableList<Resource<UserProfile>> = mutableListOf()
        val profiles: MutableList<UserProfile> = mutableListOf()
        val profileLoadFailCount = mutableStateOf(0)

        usersIds.forEach { id ->
            profilesResource.add(repository.getUserProfileById(id))
        }

        profilesResource.forEach { profileResource ->
            val textToShow =
                if(profileLoadFailCount.value == 1) "Failed to load ${profileLoadFailCount.value} profile"
                else "Failed to load ${profileLoadFailCount.value} profiles"

            when(profileResource) {
                is Resource.Success -> {
                    _userProfileState.value = profileResource.data.let { data ->
                        if(data.status == "success") profiles.add(data)
                        UserProfileState.UserProfileData(userProfileData = profiles, message = textToShow)
                    }
                }
                is Resource.Error -> {
                    profileResource.message.let {
                        profileLoadFailCount.value += 1
                    }
                }
            }
        }
    }
}