package com.norm.vkgroupviewer.presentation.friendlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.norm.vkgroupviewer.domain.remote.dto.friends_info.FriendsInfo
import com.norm.vkgroupviewer.presentation.Dimens.largeSpacer
import com.norm.vkgroupviewer.presentation.Dimens.mediumSpacer
import com.norm.vkgroupviewer.presentation.componets.FriendCard

@Composable
fun FriendListScreen(
    modifier: Modifier = Modifier,
    state: FriendsState,
    onClick: (String) -> Unit,
) {
    Column(modifier = modifier) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = largeSpacer),
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
                        name = " ${user.first_name} ${user.last_name}",
                        image = user.photo_100,
                        screenName = user.domain,
                        onClick = {
                            onClick(user.domain ?: "")
                        },
                    )
                }
            }
        }
    }
}