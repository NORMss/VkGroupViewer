package com.norm.vkgroupviewer.presentation.friendlist

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.norm.vkgroupviewer.presentation.Dimens.largeSpacer
import com.norm.vkgroupviewer.presentation.Dimens.mediumSpacer
import com.norm.vkgroupviewer.presentation.componets.FriendCard

@Composable
fun FriendListScreen(
    modifier: Modifier = Modifier,
    state: FriendsState,
    showOrHideMoreInfo: (Int) -> Unit,
    onCopyId: (String) -> Unit,
    onClick: (String) -> Unit,
) {
    Column(modifier = modifier) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = largeSpacer)
                .scrollable(rememberScrollableState {
                    it
                }, Orientation.Vertical),
            verticalArrangement = Arrangement.spacedBy(mediumSpacer),
        ) {
            state.friendsInfo?.let { friendsInfo ->
                items(
                    items = friendsInfo.response.items,
                    key = { user ->
                        user.id
                    }
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
        }
    }
}