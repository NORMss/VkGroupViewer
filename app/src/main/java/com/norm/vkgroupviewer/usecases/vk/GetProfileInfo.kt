package com.norm.vkgroupviewer.usecases.vk

import com.norm.vkgroupviewer.domain.remote.dto.profile_info.ProfileInfo
import com.norm.vkgroupviewer.domain.repository.VkRepository
import com.norm.vkgroupviewer.util.NetworkError
import com.norm.vkgroupviewer.util.Result

class GetProfileInfo(
    private val vkRepository: VkRepository,
) {
    suspend operator fun invoke(): Result<ProfileInfo, NetworkError> {
        return vkRepository.getProfileInfo()
    }
}