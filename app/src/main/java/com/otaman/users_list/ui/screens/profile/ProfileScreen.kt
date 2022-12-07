package com.otaman.users_list.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.otaman.users_list.ui.theme.LightGray
import com.otaman.users_list.ui.theme.UsersListTheme
import com.otaman.users_list.R

@Composable
fun ProfileScreen(
    firstName: String,
    lastName: String,
    age: String,
    gender: String,
    country: String,
    onBackClick: () -> Unit
) {
    ProfileScreenContent(
        firstName = firstName,
        lastName = lastName,
        age = age,
        gender = gender,
        country = country,
        onBackClick = onBackClick
    )
}

@Composable
private fun AppBar(
    firstName: String,
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = LightGray),
        contentAlignment = Alignment.CenterStart
    ) {
        Image(
            painter = painterResource(id = R.drawable.back),
            contentDescription = stringResource(id = R.string.back),
            modifier = Modifier
                .customButtonClick(onClick = onBackClick)
                .padding(8.dp)
        )
        Text(
            text = stringResource(id = R.string.profile_name, firstName),
            modifier = Modifier
                .align(alignment = Alignment.Center)
                .padding(vertical = 8.dp),
            style = MaterialTheme.typography.h1
        )
    }
}

@Composable
private fun ProfileScreenContent(
    firstName: String,
    lastName: String,
    age: String,
    gender: String,
    country: String,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            AppBar(
                firstName = firstName,
                onBackClick = onBackClick
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .padding(padding)
                .background(color = LightGray, shape = RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                Modifier.padding(vertical = 16.dp)
            ) {
                ProfileField(
                    fieldName = stringResource(id = R.string.first_name),
                    fieldValue = firstName
                )
                ProfileField(
                    fieldName = stringResource(id = R.string.last_name),
                    fieldValue = lastName
                )
                ProfileField(
                    fieldName = stringResource(id = R.string.age),
                    fieldValue = age
                )
                ProfileField(
                    fieldName = stringResource(id = R.string.gender),
                    fieldValue = gender
                )
                ProfileField(
                    fieldName = stringResource(id = R.string.country),
                    fieldValue = country
                )
            }
        }
    }
}

private fun Modifier.customButtonClick(onClick: () -> Unit): Modifier = composed {
    clickable (
        onClick = onClick,
        interactionSource = remember { MutableInteractionSource() },
        indication = rememberRipple(bounded = false)
    )
}

@Composable
private fun ProfileField(
    fieldName: String,
    fieldValue: String
) {
    Row {
        Text(
            text = fieldName,
            textAlign = TextAlign.End,
            modifier = Modifier.width(160.dp),
        )
        Text(text = " : ")
        Text(
            text = fieldValue,
            modifier = Modifier.width(160.dp),
        )
    }
}

@Preview
@Composable
private fun ProfileScreenPreview() {
    UsersListTheme {
        ProfileScreen(
            firstName = "firstName",
            lastName = "lastName",
            age = "age",
            gender = "gender",
            country = "country",
            onBackClick = {}
        )
    }
}