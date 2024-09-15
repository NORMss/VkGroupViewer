package com.norm.vkgroupviewer.data.repository

import com.norm.vkgroupviewer.data.remote.AndroidHttpClientProvider
import com.norm.vkgroupviewer.domain.remote.ProfileInfo
import com.norm.vkgroupviewer.domain.repository.VkRepository
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class VkRepositoryImpl(
    private val httpClientProvider: AndroidHttpClientProvider,
) : VkRepository {

    override suspend fun getProfileInfo(): Result<ProfileInfo> {
        val response = httpClientProvider.client.get(
            "https://api.vk.com/method/account.getProfileInfo"
        ) {
            parameter("v", "5.199")
        }
        return response.body()
    }

    override suspend fun getGroups(id: String): Result<List<String>> {
        val response = httpClientProvider.client.get(
            "https://api.vk.com/method/groups.get"
        ) {
            parameter("v", "5.199")
            parameter("user_id", id)
        }
        return response.body()
    }
}