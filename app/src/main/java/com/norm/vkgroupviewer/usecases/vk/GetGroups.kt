package com.norm.vkgroupviewer.usecases.vk

import com.norm.vkgroupviewer.domain.repository.VkRepository
import com.norm.vkgroupviewer.util.NetworkError

class GetGroups(
    private val vkRepository: VkRepository,
) {
    suspend operator fun invoke(id: String): com.norm.vkgroupviewer.util.Result<String, NetworkError> {
        return vkRepository.getGroups(id)
    }
}