package com.norm.vkgroupviewer.domain.remote.dto.users_info

import kotlinx.serialization.Serializable

@Serializable
data class UsersInfo(
    val response: List<Response>
)