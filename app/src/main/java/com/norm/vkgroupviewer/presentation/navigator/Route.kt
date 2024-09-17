package com.norm.vkgroupviewer.presentation.navigator

import kotlinx.serialization.Serializable

@Serializable
sealed class Route {
    @Serializable
    data object AuthScreen: Route()
    @Serializable
    data object GroupsScreen: Route()
}