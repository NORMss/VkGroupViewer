package com.norm.vkgroupviewer.presentation.auth

import android.util.Log
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.norm.vkgroupviewer.domain.remote.HttpClientProvider
import com.norm.vkgroupviewer.domain.remote.TokenInfo
import com.norm.vkgroupviewer.domain.remote.dto.profile_info.ProfileInfo
import com.norm.vkgroupviewer.usecases.token_info.TokenInfoUseCases
import com.norm.vkgroupviewer.usecases.vk.GetProfileInfo
import com.norm.vkgroupviewer.usecases.vk.VkUseCases
import com.norm.vkgroupviewer.util.NetworkError
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
            tokenInfo.accessToken?.let {
                setToken(it)
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


    private suspend fun saveToken() {
        tokenInfoUseCases.saveTokenInfo(
            TokenInfo(
                accessToken = _state.value.token
            )
        )
    }

    private fun getInfoProfile() {
        viewModelScope.launch {
            vkUseCases.getProfileInfo()
                .onSuccess { profileInfo ->
                    _state.update {
                        it.copy(
                            profileInfo = profileInfo,
                            userIdForGroups = profileInfo.response.id,
                        )
                    }
                }
                .onError { errorMessage ->
                    _state.update {
                        it.copy(
                            errorMessage = setErrorMessage(errorMessage)
                        )
                    }
                }
        }
    }

    fun clearError() {
        _state.update {
            it.copy(
                errorMessage = null,
            )
        }
    }

    fun authUser() {
        viewModelScope.launch {
            saveToken()
            getInfoProfile()
        }
    }

    private fun readToken(): Flow<TokenInfo> {
        return tokenInfoUseCases.readTokenInfo()
    }

    private fun setErrorMessage(networkError: NetworkError): String {
        return when (networkError) {
            NetworkError.REQUEST_TIMEOUT -> "Request timed out. Try again."
            NetworkError.UNAUTHORIZED -> "Check your credentials."
            NetworkError.CONFLICT -> "Resource conflict occurred."
            NetworkError.TOO_MANY_REQUESTS -> "Too many requests. Wait and retry."
            NetworkError.API_ERROR -> "API error. Try again."
            NetworkError.NO_INTERNET -> "No internet connection."
            NetworkError.PAYLOAD_TOO_LARGE -> "Payload is too large."
            NetworkError.SERVER_ERROR -> "Server error. Try later."
            NetworkError.SERIALIZATION -> "Data error. Report this issue."
            NetworkError.UNKNOWN -> "Unknown error. Try again."
        }
    }


    fun logOut() {
        _state.update {
            it.copy(
                profileInfo = null,
            )
        }
    }

    fun setUserIdForGroups(userId: String) {
        if (userId.isDigitsOnly()) {
            _state.update {
                it.copy(
                    userIdForGroups = userId.toInt(),
                )
            }
        }
    }
}