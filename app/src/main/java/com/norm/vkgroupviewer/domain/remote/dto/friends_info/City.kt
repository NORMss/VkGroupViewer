package com.norm.vkgroupviewer.domain.remote.dto.friends_info

import kotlinx.serialization.Serializable

@Serializable
data class City(
    val id: Int,
    val title: String
)