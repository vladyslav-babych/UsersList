package com.otaman.users_list.ui.screens.main

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import com.otaman.users_list.R
import com.otaman.users_list.ui.states.UsersState
import com.otaman.users_list.ui.theme.LightGray
import com.otaman.users_list.ui.theme.UsersListTheme
import com.otaman.users_list.ui.viewmodels.MainScreenViewModel

@Composable
fun MainScreen(
    viewModel: MainScreenViewModel,
    onNameClick: (
        firstName: String,
        lastName: String,
        age: String,
        gender: String,
        country: String
    ) -> Unit
) {
    val usersState by remember {
        viewModel.usersState
    }

    MainScreenContent(
        usersState = usersState,
        onRetryClick = { viewModel.getUsersIds() },
        onNameClick = onNameClick
    )
}

@Composable
private fun MainScreenContent(
    usersState: UsersState,
    onRetryClick: () -> Unit,
    onNameClick: (
        firstName: String,
        lastName: String,
        age: String,
        gender: String,
        country: String
    ) -> Unit
) {
    val context = LocalContext.current
    val resources = LocalContext.current.resources
    var count by rememberSaveable {
        mutableStateOf(0)
    }

    Scaffold(
        topBar = {
            AppBar()
        }
    ) { padding ->
        when (usersState) {
            is UsersState.UsersProfilesData -> {
                when {
                    usersState.usersProfilesData.isNotEmpty() -> {
                        LazyColumn(
                            modifier = Modifier.padding(padding)
                        ) {
                            items(usersState.usersProfilesData) { userProfileData ->
                                val data = userProfileData.profileData
                                UserListItem(
                                    userName = data.firstName,
                                    onNameClick = {
                                        onNameClick(
                                            data.firstName,
                                            data.lastName,
                                            data.age.toString(),
                                            data.gender,
                                            data.country
                                        )
                                    }
                                )
                            }
                        }
                    }
                    else -> {
                        ErrorView(
                            errorMessage = stringResource(id = R.string.get_profiles_error),
                            onRetryClick = onRetryClick
                        )
                    }
                }
                LaunchedEffect(key1 = count) {
                    if (count == 0) {
                        Toast.makeText(
                            context,
                            resources.getQuantityString(
                                R.plurals.failed_get_profiles_count,
                                usersState.failCount,
                                usersState.failCount
                            ),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    count = 1
                }
            }
            is UsersState.Error -> {
                ErrorView(
                    errorMessage = stringResource(id = R.string.get_ids_error, usersState.message),
                    onRetryClick = onRetryClick
                )
            }
            is UsersState.Loading -> {
                LoadingView()
            }
        }
    }
}

@Composable
private fun ErrorView(
    errorMessage: String,
    onRetryClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = errorMessage,
            textAlign = TextAlign.Center,
            color = Color.Red
        )
        Text(
            text = stringResource(id = R.string.try_again),
            modifier = Modifier
                .clickable(onClick = onRetryClick)
                .padding(top = 8.dp)
        )
    }
}

@Composable
private fun LoadingView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun AppBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = LightGray)
    ) {
        Text(
            text = stringResource(id = R.string.users),
            modifier = Modifier
                .align(alignment = Alignment.Center)
                .padding(vertical = 8.dp),
            style = MaterialTheme.typography.h1
        )
    }
}

@Composable
private fun UserListItem(
    userName: String,
    onNameClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp
            )
            .clip(shape = RoundedCornerShape(16.dp))
            .background(color = LightGray)
            .clickable(onClick = onNameClick)
    ) {
        Text(
            text = userName,
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    top = 8.dp,
                    bottom = 8.dp
                )
        )
    }
}

@Preview
@Composable
private fun UserListItemPreview() {
    UsersListTheme {
        UserListItem(
            userName = "Name",
            onNameClick = {}
        )
    }
}