package com.norm.vkgroupviewer.presentation.navigator

import kotlinx.serialization.Serializable

@Serializable
sealed class Route {
    @Serializable
    data object AuthScreen : Route()

    @Serializable
    data class GroupsScreen(val userId: Int) : Route()
}