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

@Composable
fun FriendListScreen(
    modifier: Modifier = Modifier,
    state: FriendsState,
) {
    Column(modifier = modifier) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(largeSpacer),
            verticalArrangement = Arrangement.spacedBy(mediumSpacer),
        ) {
            state.friendsInfo?.let { friendsInfo ->
                items(
                    items = friendsInfo.response.items,
                    key = { user ->
                        user.id
                    }
                ) { user ->
                    Text(
                        text = user.first_name,
                    )
                }
            }
        }
    }
}