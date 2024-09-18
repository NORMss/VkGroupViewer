package com.norm.vkgroupviewer.presentation.auth

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import coil.compose.AsyncImage
import com.norm.vkgroupviewer.presentation.Dimens.photo_200
import com.norm.vkgroupviewer.presentation.Dimens.smallSpacer

@Composable
fun AuthScreen(
    modifier: Modifier = Modifier,
    state: AuthState,
    onAuth: () -> Unit,
    onLogOut: () -> Unit,
    setToken: (String) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
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
                    .size(photo_200)
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
            Button(onClick = { /*TODO*/ }) {
                Button(
                    onClick = {
                        onLogOut()
                    }
                ) {
                    Text(
                        text = "Log out"
                    )
                }
            }
        }
    }
}