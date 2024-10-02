package com.norm.vkgroupviewer.domain.remote.dto.friends_info

import kotlinx.serialization.Serializable

@Serializable
data class RelationPartner(
    val first_name: String,
    val id: Int,
    val last_name: String
)