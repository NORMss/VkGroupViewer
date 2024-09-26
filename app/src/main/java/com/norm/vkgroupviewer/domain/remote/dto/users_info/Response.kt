package com.norm.vkgroupviewer.domain.remote.dto.users_info

import kotlinx.serialization.Serializable

@Serializable
data class Response(
    val can_access_closed: Boolean,
    val first_name: String,
    val id: Int,
    val is_closed: Boolean,
    val last_name: String
)