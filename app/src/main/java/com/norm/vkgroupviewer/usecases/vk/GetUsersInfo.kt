package com.norm.vkgroupviewer.usecases.vk

import com.norm.vkgroupviewer.domain.remote.dto.users_info.UsersInfo
import com.norm.vkgroupviewer.domain.repository.VkRepository
import com.norm.vkgroupviewer.util.NetworkError
import com.norm.vkgroupviewer.util.Result

class GetUsersInfo(
    private val vkRepository: VkRepository,
) {
    suspend operator fun invoke(ids: String): Result<UsersInfo, NetworkError> {
        return vkRepository.getUsersInfoByIds(ids)
    }
}