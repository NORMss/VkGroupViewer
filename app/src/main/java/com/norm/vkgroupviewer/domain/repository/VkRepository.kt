package com.norm.vkgroupviewer.domain.repository

import kotlinx.coroutines.flow.Flow

interface VkRepository {
    suspend fun auth(accessToken: String)
    fun getGroups(): Flow<List<String>>
}