package com.norm.vkgroupviewer.domain.repository

import com.norm.vkgroupviewer.domain.remote.dto.groups_info.GroupsInfo
import com.norm.vkgroupviewer.domain.remote.dto.profile_info.ProfileInfo
import com.norm.vkgroupviewer.util.NetworkError
import com.norm.vkgroupviewer.util.Result

interface VkRepository {
    suspend fun getProfileInfo(): Result<ProfileInfo, NetworkError>
    suspend fun getGroups(id: Int): Result<GroupsInfo, NetworkError>
}