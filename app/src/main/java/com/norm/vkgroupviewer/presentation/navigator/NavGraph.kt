package com.norm.vkgroupviewer.presentation.navigator

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.norm.vkgroupviewer.presentation.auth.AuthScreen
import com.norm.vkgroupviewer.presentation.auth.AuthViewModel
import com.norm.vkgroupviewer.presentation.groups.GroupsScreen
import com.norm.vkgroupviewer.presentation.groups.GroupsViewModel

@Composable
fun NavGraph(
    startDestination: Route,
) {
    val context = LocalContext.current

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable<Route.AuthScreen> {
            val viewModel = hiltViewModel<AuthViewModel>()
            val state = viewModel.state.collectAsState().value
            AuthScreen(
                state = state,
                onAuth = {
                    viewModel.authUser()
                },
                onLogOut = {
                    viewModel.logOut()
                },
                setToken = { token ->
                    viewModel.setToken(token)
                },
                clearError = {
                    viewModel.clearError()
                },
                setUserIdForGroups = { userId ->
                    viewModel.setUserIdForGroups(userId)
                },
                setUserScreenNameForGroups = { screenName ->
                    viewModel.setUserScreenNameForGroups(screenName)
                },
                onOpenGroupsScreen = { userId ->
                    navController.navigate(
                        Route.GroupsScreen(userId)
                    )
                },
                convertToId = { ->
                    viewModel.getIdIfScreenName()
                }
            )
        }
        composable<Route.GroupsScreen> { backStackEntry ->
            val viewModel = hiltViewModel<GroupsViewModel>()
            val state = viewModel.state.collectAsState().value
            backStackEntry.toRoute<Route.GroupsScreen>().let { authScreen ->
                viewModel.setUserId(
                    authScreen.userId
                )
            }
            GroupsScreen(
                state = state,
                onClick = {
                    val webPage = Intent(Intent.ACTION_VIEW, Uri.parse("https://vk.com/$it"))
                    context.startActivity(webPage)
                },
                onRefresh = {
                    viewModel.refreshVkGroups()
                }
            )
        }
    }
}