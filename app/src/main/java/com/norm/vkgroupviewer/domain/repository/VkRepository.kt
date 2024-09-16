package com.norm.vkgroupviewer.domain.repository

import com.norm.vkgroupviewer.domain.remote.ProfileInfo
import com.norm.vkgroupviewer.util.NetworkError

interface VkRepository {
    suspend fun getProfileInfo(): com.norm.vkgroupviewer.util.Result<ProfileInfo, NetworkError>
    suspend fun getGroups(id: String): com.norm.vkgroupviewer.util.Result<String, NetworkError>
}