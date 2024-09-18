package com.norm.vkgroupviewer.presentation.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.norm.vkgroupviewer.domain.remote.HttpClientProvider
import com.norm.vkgroupviewer.domain.remote.TokenInfo
import com.norm.vkgroupviewer.usecases.token_info.TokenInfoUseCases
import com.norm.vkgroupviewer.usecases.vk.GetProfileInfo
import com.norm.vkgroupviewer.usecases.vk.VkUseCases
import com.norm.vkgroupviewer.util.onError
import com.norm.vkgroupviewer.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val tokenInfoUseCases: TokenInfoUseCases,
    private val vkUseCases: VkUseCases,
    private val httpClientProvider: HttpClientProvider,
) : ViewModel() {
    private val _state = MutableStateFlow(AuthState())
    val state = _state.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _state.value)

    init {
        readToken().onEach { tokenInfo ->
            _state.update {
                it.copy(
                    token = tokenInfo.accessToken
                )
            }
            httpClientProvider.authClient(tokenInfo)
            vkUseCases.getProfileInfo()
                .onSuccess {
                    Log.d("MyLog", "profileInfo: ${it.response.first_name}")
                }
                .onError {
                    Log.d("MyLog", "errorMessage: ${it}")
                }
        }.launchIn(viewModelScope)
    }

    fun setToken(token: String) {
        _state.update {
            it.copy(
                token = token
            )
        }
    }


    fun saveToken() {
        viewModelScope.launch {
            tokenInfoUseCases.saveTokenInfo(
                TokenInfo(
                    accessToken = _state.value.token
                )
            )
        }
    }

    private fun readToken(): Flow<TokenInfo> {
        return tokenInfoUseCases.readTokenInfo()
    }

    private fun getInfoProfile(): GetProfileInfo {
        return vkUseCases.getProfileInfo
    }
}