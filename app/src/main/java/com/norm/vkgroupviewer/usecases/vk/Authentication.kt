package com.norm.vkgroupviewer.usecases.vk

import com.norm.vkgroupviewer.domain.repository.VkRepository

class Authentication(
    private val vkRepository: VkRepository,
) {
    suspend operator fun invoke() {
        vkRepository.getProfileInfo()
    }
}