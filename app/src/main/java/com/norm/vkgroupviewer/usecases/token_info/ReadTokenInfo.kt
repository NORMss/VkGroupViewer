package com.norm.vkgroupviewer.usecases.token_info

import com.norm.vkgroupviewer.domain.manager.LocalUserManager
import com.norm.vkgroupviewer.domain.remote.TokenInfo
import kotlinx.coroutines.flow.Flow

class ReadTokenInfo(
    private val localUserManager: LocalUserManager,
) {
    operator fun invoke(): Flow<TokenInfo> {
        return localUserManager.readToken()
    }
}