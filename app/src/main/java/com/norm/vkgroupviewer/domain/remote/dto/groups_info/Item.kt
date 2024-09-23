package com.norm.vkgroupviewer.domain.remote.dto.groups_info

import kotlinx.serialization.Serializable

@Serializable
data class Item(
    val admin_level: Int? = null,
    val id: Int,
    val is_admin: Int? = null,
    val is_advertiser: Int? = null,
    val is_closed: Int,
    val is_member: Int? = null,
    val name: String,
    val photo_100: String,
    val photo_200: String,
    val photo_50: String,
    val screen_name: String? = null,
    val type: String,
)