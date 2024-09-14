package com.norm.vkgroupviewer.data.repository

import com.norm.vkgroupviewer.data.remote.HttpClient
import com.norm.vkgroupviewer.domain.remote.TokenInfo
import com.norm.vkgroupviewer.domain.repository.VkRepository
import com.norm.vkgroupviewer.usecases.token_info.TokenInfoUseCases
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.http.parameters
import kotlinx.coroutines.flow.Flow

class VkRepositoryImpl(
    private val httpClient: HttpClient,
) : VkRepository {
    override suspend fun getProfileInfo() {
    }

    override fun getGroups(): Flow<List<String>> {
        TODO()
    }
}