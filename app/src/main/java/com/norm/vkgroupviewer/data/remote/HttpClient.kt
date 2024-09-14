package com.norm.vkgroupviewer.data.remote

import com.norm.vkgroupviewer.domain.remote.TokenInfo
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
import kotlinx.serialization.json.Json

object HttpClient {
    fun provideHttpClient(tokenInfo: TokenInfo): HttpClient {
        val client by lazy {
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
                install(Auth) {
                    bearer {
                        tokenInfo.accessToken?.let {
                            BearerTokens(tokenInfo.accessToken, tokenInfo.accessToken)
                        }
                    }
                }
            }
        }
        return client
    }
}