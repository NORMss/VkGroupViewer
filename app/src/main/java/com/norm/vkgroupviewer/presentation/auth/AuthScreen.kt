package com.norm.vkgroupviewer.presentation.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.norm.vkgroupviewer.presentation.Dimens.smallSpacer

@Composable
fun AuthScreen(
    modifier: Modifier = Modifier,
    state: AuthState,
    onAuth: () -> Unit,
    setToken: (String) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextField(
            value = state.token,
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
    }
}