@file:OptIn(ExperimentalMaterial3Api::class)

package com.norm.vkgroupviewer.presentation.auth

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import coil.compose.AsyncImage
import com.norm.vkgroupviewer.presentation.Dimens.photoLarge
import com.norm.vkgroupviewer.presentation.Dimens.smallSpacer

@Composable
fun AuthScreen(
    modifier: Modifier = Modifier,
    state: AuthState,
    onAuth: () -> Unit,
    onLogOut: () -> Unit,
    clearError: () -> Unit,
    setToken: (String) -> Unit,
    setUserIdForGroups: (String) -> Unit,
    onOpenGroupsScreen: (Int) -> Unit,
) {
    val context = LocalContext.current
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LaunchedEffect(key1 = state.errorMessage) {
            Log.d("errorMessage: ", state.errorMessage ?: "null")
            state.errorMessage?.let {
                Toast.makeText(
                    context,
                    it,
                    Toast.LENGTH_SHORT
                ).show()
                clearError()
            }
        }
        if (state.profileInfo == null) {
            TextField(
                value = state.token ?: "",
                onValueChange = { token ->
                    setToken(token)
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                placeholder = {
                    Text(
                        text = "Token"
                    )
                },
                singleLine = true,
                keyboardActions = KeyboardActions(
                    onDone = {
                        onAuth()
                    }
                ),
            )
            Spacer(
                modifier = Modifier
                    .height(smallSpacer)
            )
            Button(
                onClick = {
                    onAuth()
                }
            ) {
                Text(
                    text = "Auth"
                )
            }
        } else {
            Log.d("MyLog", "AsyncImage")
            AsyncImage(
                model = state.profileInfo.response.photo_200,
                contentDescription = "photo_200",
                modifier = Modifier
                    .size(photoLarge)
                    .clip(CircleShape),
            )
            Spacer(
                modifier = Modifier
                    .height(smallSpacer)
            )
            Text(
                text = "${state.profileInfo.response.first_name}\n" +
                        "${state.profileInfo.response.last_name}",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium,
            )
            if (state.profileInfo.response.screen_name.isNotBlank()) {
                Text(
                    text = state.profileInfo.response.screen_name,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                )
            }
            Spacer(
                modifier = Modifier
                    .height(smallSpacer)
            )
            Button(
                onClick = {
                    onLogOut()
                }
            ) {
                Text(
                    text = "Log out"
                )
            }
            Spacer(
                modifier = Modifier
                    .height(smallSpacer)
            )
            TextField(
                value = state.userIdForGroups?.toString() ?: "",
                onValueChange = { userId ->
                    setUserIdForGroups(userId)
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                placeholder = {
                    Text(
                        text = "User ID"
                    )
                },
                singleLine = true,
                keyboardActions = KeyboardActions(
                    onDone = {
                        onAuth()
                    },
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                )
            )
            Spacer(
                modifier = Modifier
                    .height(smallSpacer)
            )
            Button(
                onClick = {
                    state.userIdForGroups?.let {
                        onOpenGroupsScreen(it)
                    }
                }
            ) {
                Text(
                    text = "Groups"
                )
            }
        }
    }
}