package com.norm.vkgroupviewer.usecases.vk

import com.norm.vkgroupviewer.domain.remote.dto.user_screen_name.UserScreenName
import com.norm.vkgroupviewer.domain.repository.VkRepository
import com.norm.vkgroupviewer.util.NetworkError
import com.norm.vkgroupviewer.util.Result

class ResolveScreenName(
    private val vkRepository: VkRepository,
) {
    suspend operator fun invoke(screenName: String): Result<UserScreenName, NetworkError> {
        return vkRepository.resolveScreenName(screenName)
    }
}