package com.norm.vkgroupviewer.presentation.navigator

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.norm.vkgroupviewer.presentation.Dimens.largeSpacer
import com.norm.vkgroupviewer.presentation.auth.AuthScreen
import com.norm.vkgroupviewer.presentation.auth.AuthViewModel
import com.norm.vkgroupviewer.presentation.friendlist.FriendListScreen
import com.norm.vkgroupviewer.presentation.friendlist.FriendsViewModel
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
                onOpenFriendList = { userId ->
                    navController.navigate(
                        Route.FriendListScreen(userId)
                    )
                },
                onOpenGroupsScreen = { userId ->
                    navController.navigate(
                        Route.GroupsScreen(userId)
                    )
                },
                convertToId = { ->
                    viewModel.getIdIfScreenName()
                },
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
        composable<Route.FriendListScreen> { backStackEntry ->
            val viewModel = hiltViewModel<FriendsViewModel>()
            val state = viewModel.state.collectAsState().value
            backStackEntry.toRoute<Route.GroupsScreen>().let { authScreen ->
                viewModel.setUserId(
                    authScreen.userId
                )
            }
            FriendListScreen(
                state = state,
                showOrHideMoreInfo = { id ->
                    viewModel.showOrHideMoreInfo(id)
                },
                onRefresh = {
                    viewModel.refreshVkFriends()
                },
                onCopyId = { id ->
                    val clipboard =
                        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip: ClipData = ClipData.newPlainText("simple text", id)
                    clipboard.setPrimaryClip(clip);
                },
                onClick = {
                    val webPage = Intent(Intent.ACTION_VIEW, Uri.parse("https://vk.com/$it"))
                    context.startActivity(webPage)
                }
            )
        }
    }
}