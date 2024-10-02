package com.norm.vkgroupviewer.data.repository

import com.norm.vkgroupviewer.domain.remote.HttpClientProvider
import com.norm.vkgroupviewer.domain.remote.dto.friends_info.FriendsInfo
import com.norm.vkgroupviewer.domain.remote.dto.groups_info.GroupsInfo
import com.norm.vkgroupviewer.domain.remote.dto.profile_info.ProfileInfo
import com.norm.vkgroupviewer.domain.remote.dto.user_screen_name.UserScreenName
import com.norm.vkgroupviewer.domain.remote.dto.users_info.UsersInfo
import com.norm.vkgroupviewer.domain.repository.VkRepository
import com.norm.vkgroupviewer.util.Constants.VK_API_VERSION
import com.norm.vkgroupviewer.util.NetworkError
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException
import com.norm.vkgroupviewer.util.Result
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject

class VkRepositoryImpl(
    private val httpClientProvider: HttpClientProvider,
) : VkRepository {

    override suspend fun getProfileInfo(): Result<ProfileInfo, NetworkError> {
        val response = try {
            httpClientProvider.client.get(
                "https://api.vk.com/method/account.getProfileInfo"
            ) {
                parameter("v", VK_API_VERSION)
            }
        } catch (e: UnresolvedAddressException) {
            return Result.Error(NetworkError.NO_INTERNET)
        } catch (e: SerializationException) {
            return Result.Error(NetworkError.SERIALIZATION)
        }
        return when (response.status.value) {
            in 200..299 -> {
                val responseBody = response.body<JsonObject>()

                if (responseBody.containsKey("error")) {
                    val error = responseBody["error"]!!.jsonObject
                    val errorCode = error["error_code"]!!.toString().toInt()

                    return when (errorCode) {
                        5 -> Result.Error(NetworkError.UNAUTHORIZED)
                        else -> Result.Error(NetworkError.API_ERROR)
                    }
                }

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
            parameter("v", VK_API_VERSION)
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

    override suspend fun getUsersInfoByIds(ids: String): Result<UsersInfo, NetworkError> {
        val response = httpClientProvider.client.get(
            "https://api.vk.com/method/users.get"
        ) {
            parameter("v", VK_API_VERSION)
            parameter("user_ids", ids)
        }
        return when (response.status.value) {
            in 200..299 -> {
                val groupsInfo = response.body<UsersInfo>()
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

    override suspend fun resolveScreenName(screenName: String): Result<UserScreenName, NetworkError> {
        val response = httpClientProvider.client.get(
            "https://api.vk.com/method/utils.resolveScreenName"
        ) {
            parameter("v", VK_API_VERSION)
            parameter("screen_name", screenName)
        }
        return when (response.status.value) {
            in 200..299 -> {
                val groupsInfo = response.body<UserScreenName>()
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

    override suspend fun getFriends(id: Int, fields: String): Result<FriendsInfo, NetworkError> {
        val response = httpClientProvider.client.get(
            "https://api.vk.com/method/friends.get"
        ) {
            parameter("v", VK_API_VERSION)
            parameter("user_id", id)
            parameter("fields", fields)
        }
        return when (response.status.value) {
            in 200..299 -> {
                val friendsInfo = response.body<FriendsInfo>()
                Result.Success(friendsInfo)
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