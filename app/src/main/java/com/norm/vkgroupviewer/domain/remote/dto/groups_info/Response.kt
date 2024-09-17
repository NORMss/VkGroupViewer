package com.norm.vkgroupviewer.domain.remote.dto.groups_info

data class Response(
    val count: Int,
    val items: List<Item>,
    val last_updated_time: Int
)