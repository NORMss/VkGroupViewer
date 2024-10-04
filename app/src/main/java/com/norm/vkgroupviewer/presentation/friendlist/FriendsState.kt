package com.norm.vkgroupviewer.presentation.friendlist

import com.norm.vkgroupviewer.domain.remote.dto.friends_info.FriendsInfo

data class FriendsState(
    val friendsInfo: FriendsInfo? = null,
    val userId: Int? = null,
    val fields: String? = null,
    val errorMessage: String? = null,
)
