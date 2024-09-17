package com.norm.vkgroupviewer.presentation.auth

import com.norm.vkgroupviewer.domain.remote.dto.profile_info.ProfileInfo

data class AuthState(
    val token: String = "",
    val profileInfo: ProfileInfo? = null,
    val errorMessage: String? = null,
)
