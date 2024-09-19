package com.norm.vkgroupviewer.usecases.vk

import com.norm.vkgroupviewer.util.Result
import com.norm.vkgroupviewer.domain.remote.dto.groups_info.GroupsInfo
import com.norm.vkgroupviewer.domain.repository.VkRepository
import com.norm.vkgroupviewer.util.NetworkError

class GetGroups(
    private val vkRepository: VkRepository,
) {
    suspend operator fun invoke(id: Int): Result<GroupsInfo, NetworkError> {
        return vkRepository.getGroups(id)
    }
}