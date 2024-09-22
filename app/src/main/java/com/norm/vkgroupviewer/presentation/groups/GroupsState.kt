package com.norm.vkgroupviewer.presentation.groups

import com.norm.vkgroupviewer.domain.remote.dto.groups_info.GroupsInfo

data class GroupsState(
    val groupsInfo: GroupsInfo? = null,
    val userId: Int? = null,
    val errorMessage: String? = null,
    val isLoadingGroups: Boolean = false,
)