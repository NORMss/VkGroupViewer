package com.norm.vkgroupviewer.usecases.token_info

import com.norm.vkgroupviewer.domain.manager.LocalUserManager
import com.norm.vkgroupviewer.domain.remote.TokenInfo

class SaveTokenInfo(
    private val localUserManager: LocalUserManager,
) {
    suspend operator fun invoke(tokenInfo: TokenInfo) {
        localUserManager.saveToken(tokenInfo)
    }
}