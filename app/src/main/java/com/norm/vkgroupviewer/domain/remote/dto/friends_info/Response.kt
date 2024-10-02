package com.norm.vkgroupviewer.domain.remote.dto.friends_info

import kotlinx.serialization.Serializable

@Serializable
data class Response(
    val count: Int,
    val items: List<Item>
)