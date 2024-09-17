package com.norm.vkgroupviewer.usecases.vk

import com.norm.vkgroupviewer.domain.repository.VkRepository

class GetProfileInfo(
    private val vkRepository: VkRepository,
) {
    suspend operator fun invoke() {
        vkRepository.getProfileInfo()
    }
}