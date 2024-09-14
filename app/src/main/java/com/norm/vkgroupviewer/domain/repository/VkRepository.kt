package com.norm.vkgroupviewer.domain.repository

import com.norm.vkgroupviewer.domain.remote.TokenInfo
import kotlinx.coroutines.flow.Flow

interface VkRepository {
    suspend fun getProfileInfo()
    fun getGroups(): Flow<List<String>>
}