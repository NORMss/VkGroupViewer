package com.norm.vkgroupviewer.domain.remote.dto.profile_info

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileInfo(
    @SerialName("response") val response: Response
)