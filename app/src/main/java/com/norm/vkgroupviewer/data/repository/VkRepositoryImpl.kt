package com.norm.vkgroupviewer.data.repository

import com.norm.vkgroupviewer.domain.remote.HttpClientProvider
import com.norm.vkgroupviewer.domain.remote.dto.groups_info.GroupsInfo
import com.norm.vkgroupviewer.domain.remote.dto.profile_info.ProfileInfo
import com.norm.vkgroupviewer.domain.repository.VkRepository
import com.norm.vkgroupviewer.util.NetworkError
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException
import com.norm.vkgroupviewer.util.Result
import io.ktor.client.HttpClient

class VkRepositoryImpl(
    private val httpClientProvider: HttpClientProvider,
) : VkRepository {

    override suspend fun getProfileInfo(): Result<ProfileInfo, NetworkError> {
        val response = try {
            httpClientProvider.client.get(
                "https://api.vk.com/method/account.getProfileInfo"
            ) {
                parameter("v", "5.199")
            }
        } catch (e: UnresolvedAddressException) {
            return Result.Error(NetworkError.NO_INTERNET)
        } catch (e: SerializationException) {
            return Result.Error(NetworkError.SERIALIZATION)
        }
        return when (response.status.value) {
            in 200..299 -> {
                val profileInfo = response.body<ProfileInfo>()
                Result.Success(profileInfo)
            }

            401 -> Result.Error(NetworkError.UNAUTHORIZED)
            409 -> Result.Error(NetworkError.CONFLICT)
            413 -> Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)

            in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
            else -> Result.Error(NetworkError.UNKNOWN)
        }
    }

    override suspend fun getGroups(id: Int): Result<GroupsInfo, NetworkError> {
        val response = httpClientProvider.client.get(
            "https://api.vk.com/method/groups.get"
        ) {
            parameter("v", "5.199")
            parameter("user_id", id)
            parameter("extended", "1")
        }
        return when (response.status.value) {
            in 200..299 -> {
                val groupsInfo = response.body<GroupsInfo>()
                Result.Success(groupsInfo)
            }

            401 -> Result.Error(NetworkError.UNAUTHORIZED)
            409 -> Result.Error(NetworkError.CONFLICT)
            413 -> Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)

            in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
            else -> Result.Error(NetworkError.UNKNOWN)
        }
    }
}