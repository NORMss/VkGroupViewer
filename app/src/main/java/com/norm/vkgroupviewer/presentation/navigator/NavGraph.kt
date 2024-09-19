package com.norm.vkgroupviewer.presentation.navigator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.norm.vkgroupviewer.presentation.auth.AuthScreen
import com.norm.vkgroupviewer.presentation.auth.AuthViewModel
import com.norm.vkgroupviewer.presentation.groups.GroupsScreen
import com.norm.vkgroupviewer.presentation.groups.GroupsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

@Composable
fun NavGraph(
    startDestination: Route,
) {
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
                setUserIdForGroups = { userId ->
                    viewModel.setUserIdForGroups(userId)
                },
                onOpenGroupsScreen = { userId ->
                    navController.navigate(
                        Route.GroupsScreen(userId)
                    )
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

                },
            )
        }
    }
}