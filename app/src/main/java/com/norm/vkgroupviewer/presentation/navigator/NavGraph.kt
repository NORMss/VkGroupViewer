package com.norm.vkgroupviewer.presentation.navigator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.norm.vkgroupviewer.presentation.auth.AuthScreen
import com.norm.vkgroupviewer.presentation.auth.AuthViewModel
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
                }
            )
        }
        composable<Route.GroupsScreen> {

        }
    }
}