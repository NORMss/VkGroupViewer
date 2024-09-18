package com.norm.vkgroupviewer.presentation.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.norm.vkgroupviewer.domain.remote.HttpClientProvider
import com.norm.vkgroupviewer.domain.remote.TokenInfo
import com.norm.vkgroupviewer.domain.remote.dto.profile_info.ProfileInfo
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
            getInfoProfile()
        }.launchIn(viewModelScope)
    }

    fun setToken(token: String) {
        _state.update {
            it.copy(
                token = token
            )
        }
    }


    private fun saveToken() {
        viewModelScope.launch {
            tokenInfoUseCases.saveTokenInfo(
                TokenInfo(
                    accessToken = _state.value.token
                )
            )
        }
    }

    fun authUser(){
        saveToken()
        getInfoProfile()
    }

    private fun readToken(): Flow<TokenInfo> {
        return tokenInfoUseCases.readTokenInfo()
    }

    private fun getInfoProfile() {
        viewModelScope.launch {
            vkUseCases.getProfileInfo()

                .onSuccess { profileInfo ->
                    Log.d("MyLog", "profileInfo: ${profileInfo.response.first_name}")
                    _state.update {
                        it.copy(
                            profileInfo = profileInfo
                        )
                    }
                }
                .onError {
                    Log.d("MyLog", "errorMessage: ${it}")
                }
        }
    }

    fun logOut() {
        _state.update {
            it.copy(
                profileInfo = null,
            )
        }
    }
}