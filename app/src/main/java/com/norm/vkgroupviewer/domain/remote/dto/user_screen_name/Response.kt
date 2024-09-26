package com.norm.vkgroupviewer.domain.remote.dto.user_screen_name

import kotlinx.serialization.Serializable

@Serializable
data class Response(
    val object_id: Int,
    val type: String
)