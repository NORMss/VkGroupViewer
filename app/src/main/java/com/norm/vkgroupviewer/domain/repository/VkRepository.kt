package com.norm.vkgroupviewer.domain.repository

import com.norm.vkgroupviewer.domain.remote.ProfileInfo
import com.norm.vkgroupviewer.domain.remote.TokenInfo
import kotlinx.coroutines.flow.Flow

interface VkRepository {
    suspend fun getProfileInfo(): Result<ProfileInfo>
    suspend fun getGroups(id: String): Result<List<String>>
}