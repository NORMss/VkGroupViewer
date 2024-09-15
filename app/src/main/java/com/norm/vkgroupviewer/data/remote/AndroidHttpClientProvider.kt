package com.norm.vkgroupviewer.data.remote

import androidx.compose.runtime.MutableState
import com.norm.vkgroupviewer.domain.remote.HttpClientProvider
import com.norm.vkgroupviewer.domain.remote.TokenInfo
import com.norm.vkgroupviewer.usecases.token_info.TokenInfoUseCases
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.json.Json

class AndroidHttpClientProvider(
    private val tokenInfoUseCases: TokenInfoUseCases,
) : HttpClientProvider {

    init {
        getTokenInfo().onEach { tokenInfo ->
            authClient(tokenInfo)
        }
    }

    override val client by lazy {
        HttpClient(CIO) {
            install(Logging) {
                level = LogLevel.ALL
                logger = Logger.ANDROID
            }
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
        }
    }

    private fun authClient(tokenInfo: TokenInfo) {
        client.config {
            install(Auth) {
                tokenInfo.accessToken?.let {
                    BearerTokens(tokenInfo.accessToken, tokenInfo.accessToken)
                }
            }
        }
    }

    private fun getTokenInfo(): Flow<TokenInfo> {
        return tokenInfoUseCases.readTokenInfo()
    }
}