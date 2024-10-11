package com.norm.vkgroupviewer.presentation.friendlist

import com.norm.vkgroupviewer.domain.remote.dto.friends_info.FriendsInfo

data class FriendsState(
    val friendsInfo: FriendsInfo? = null,
    val showMoreInfo: Map<Int, Boolean> = emptyMap(),
    val userId: Int? = null,
    val fields: String? = null,
    val errorMessage: String? = null,
    val isLoadingGroups: Boolean = false,
)
