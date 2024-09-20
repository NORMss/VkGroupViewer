package com.norm.vkgroupviewer.domain.remote.dto.groups_info

import kotlinx.serialization.Serializable

@Serializable
data class Response(
    val count: Int,
    val items: List<Item>,
    val last_updated_time: Int
)