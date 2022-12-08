package com.otaman.users_list.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otaman.users_list.domain.models.UserProfile
import com.otaman.users_list.domain.repository.UsersRepository
import com.otaman.users_list.domain.util.Resource
import com.otaman.users_list.ui.states.UsersState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val repository: UsersRepository
): ViewModel() {

    private val _usersState: MutableState<UsersState> = mutableStateOf(UsersState.Loading)
    val usersState: State<UsersState> = _usersState

    init {
        getUsersIds()
    }

    fun getUsersIds() = viewModelScope.launch {
        _usersState.value = UsersState.Loading
        when(val result = repository.getUsersIds()) {
            is Resource.Success -> {
                getUsersProfilesByIds(result.data.ids)
            }
            is Resource.Error -> {
                _usersState.value = UsersState.Error(message = result.message)
            }
        }
    }

    private fun getUsersProfilesByIds(usersIds: List<String>) = viewModelScope.launch {

        val profiles: MutableList<UserProfile> = mutableListOf()
        var profileLoadFailCount = 0

        usersIds.forEach { id ->
            when(val result = repository.getUserProfileById(id)) {
                is Resource.Success -> {
                    profiles.add(result.data)
                }
                is Resource.Error -> {
                    profileLoadFailCount++
                }
            }
        }

        _usersState.value = UsersState.UsersProfilesData(profiles, profileLoadFailCount)
    }
}