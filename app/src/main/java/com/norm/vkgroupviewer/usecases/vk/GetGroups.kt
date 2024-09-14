package com.norm.vkgroupviewer.usecases.vk

import com.norm.vkgroupviewer.domain.repository.VkRepository
import kotlinx.coroutines.flow.Flow

class GetGroups(
    private val vkRepository: VkRepository,
) {
    operator fun invoke(): Flow<List<String>> {
        return vkRepository.getGroups()
    }
}