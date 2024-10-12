package com.norm.vkgroupviewer.presentation.friendlist

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.norm.vkgroupviewer.presentation.componets.FriendCard
import com.norm.vkgroupviewer.presentation.componets.VkItemList

@Composable
fun FriendListScreen(
    state: FriendsState,
    onRefresh: () -> Unit,
    showOrHideMoreInfo: (Int) -> Unit,
    onCopyId: (String) -> Unit,
    onClick: (String) -> Unit,
) {
    VkItemList(
        isRefresh = state.isLoadingGroups,
        onRefresh = {
            onRefresh()
        },
        count = state.friendsInfo?.response?.count,
        collection = state.friendsInfo?.response?.items,
        keySelector = {
            it.id
        },
        modifier = Modifier
            .fillMaxSize(),
    ) { user ->
        FriendCard(
            userId = user.id.toString(),
            name = " ${user.first_name} ${user.last_name}",
            image = user.photo_100,
            screenName = user.domain,
            isMoreInfo = state.showMoreInfo[user.id]!!,
            showOrHideMoreInfo = {
                showOrHideMoreInfo(user.id)
            },
            onCopyId = {
                onCopyId(user.id.toString())
            },
            onClick = {
                onClick(user.domain ?: "")
            },
        )
    }
}