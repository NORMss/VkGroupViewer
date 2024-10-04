package com.norm.vkgroupviewer.domain.repository

import com.norm.vkgroupviewer.domain.remote.dto.friends_info.FriendsInfo
import com.norm.vkgroupviewer.domain.remote.dto.groups_info.GroupsInfo
import com.norm.vkgroupviewer.domain.remote.dto.profile_info.ProfileInfo
import com.norm.vkgroupviewer.domain.remote.dto.user_screen_name.UserScreenName
import com.norm.vkgroupviewer.domain.remote.dto.users_info.UsersInfo
import com.norm.vkgroupviewer.util.NetworkError
import com.norm.vkgroupviewer.util.Result

interface VkRepository {
    suspend fun getProfileInfo(): Result<ProfileInfo, NetworkError>
    suspend fun getGroups(id: Int): Result<GroupsInfo, NetworkError>
    suspend fun getUsersInfoByIds(ids: String): Result<UsersInfo, NetworkError>
    suspend fun resolveScreenName(screenName: String): Result<UserScreenName, NetworkError>
    suspend fun getFriends(id: Int, fields: String?): Result<FriendsInfo, NetworkError>
}