package com.norm.vkgroupviewer.data.repository

import com.norm.vkgroupviewer.data.remote.HttpClient
import com.norm.vkgroupviewer.domain.repository.VkRepository
import io.ktor.http.parameters
import kotlinx.coroutines.flow.Flow

class VkRepositoryImpl(
    private val httpClient: HttpClient,
) : VkRepository {
    override suspend fun auth(accessToken: String) {
        val authorizationUrlQuery = parameters {

        }
    }

    override fun getGroups(): Flow<List<String>> {
        TODO()
    }
}