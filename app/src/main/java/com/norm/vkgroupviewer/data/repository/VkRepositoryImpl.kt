package com.norm.vkgroupviewer.data.repository

import com.norm.vkgroupviewer.data.remote.AndroidHttpClientProvider
import com.norm.vkgroupviewer.domain.remote.ProfileInfo
import com.norm.vkgroupviewer.domain.repository.VkRepository
import com.norm.vkgroupviewer.util.NetworkError
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException

class VkRepositoryImpl(
    private val httpClientProvider: AndroidHttpClientProvider,
) : VkRepository {

    override suspend fun getProfileInfo(): com.norm.vkgroupviewer.util.Result<ProfileInfo, NetworkError> {
        val response = try {
            httpClientProvider.client.get(
                "https://api.vk.com/method/account.getProfileInfo"
            ) {
                parameter("v", "5.199")
            }
        } catch (e: UnresolvedAddressException) {
            return com.norm.vkgroupviewer.util.Result.Error(NetworkError.NO_INTERNET)
        } catch (e: SerializationException) {
            return com.norm.vkgroupviewer.util.Result.Error(NetworkError.SERIALIZATION)
        }
        return when (response.status.value) {
            in 200..299 -> {
                val profileInfo = response.body<ProfileInfo>()
                com.norm.vkgroupviewer.util.Result.Success(profileInfo)
            }

            401 -> com.norm.vkgroupviewer.util.Result.Error(NetworkError.UNAUTHORIZED)
            409 -> com.norm.vkgroupviewer.util.Result.Error(NetworkError.CONFLICT)
            413 -> com.norm.vkgroupviewer.util.Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            408 -> com.norm.vkgroupviewer.util.Result.Error(NetworkError.REQUEST_TIMEOUT)

            in 500..599 -> com.norm.vkgroupviewer.util.Result.Error(NetworkError.SERVER_ERROR)
            else -> com.norm.vkgroupviewer.util.Result.Error(NetworkError.UNKNOWN)

        }

    }

    override suspend fun getGroups(id: String): com.norm.vkgroupviewer.util.Result<String, NetworkError> {
        val response = httpClientProvider.client.get(
            "https://api.vk.com/method/groups.get"
        ) {
            parameter("v", "5.199")
            parameter("user_id", id)
        }
        return response.body()
    }
}