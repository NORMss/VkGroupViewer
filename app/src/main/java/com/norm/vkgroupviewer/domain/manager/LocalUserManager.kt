package com.norm.vkgroupviewer.domain.manager

import com.norm.vkgroupviewer.domain.remote.TokenInfo
import kotlinx.coroutines.flow.Flow

interface LocalUserManager {
    suspend fun saveToken(tokenInfo: TokenInfo)
    fun readToken(): Flow<TokenInfo>
}