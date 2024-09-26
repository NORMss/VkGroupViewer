package com.norm.vkgroupviewer.usecases.vk

data class VkUseCases(
    val getProfileInfo: GetProfileInfo,
    val getGroups: GetGroups,
    val getUsersInfo: GetUsersInfo,
    val resolveScreenName: ResolveScreenName,
)
