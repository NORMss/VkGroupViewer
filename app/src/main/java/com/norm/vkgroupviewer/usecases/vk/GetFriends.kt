package com.norm.vkgroupviewer.usecases.vk

import com.norm.vkgroupviewer.domain.remote.dto.friends_info.FriendsInfo
import com.norm.vkgroupviewer.util.Result
import com.norm.vkgroupviewer.domain.repository.VkRepository
import com.norm.vkgroupviewer.util.NetworkError

class GetFriends(
    private val vkRepository: VkRepository,
) {
    suspend operator fun invoke(id: Int, fields: String): Result<FriendsInfo, NetworkError> {
        return vkRepository.getFriends(id, fields)
    }
}