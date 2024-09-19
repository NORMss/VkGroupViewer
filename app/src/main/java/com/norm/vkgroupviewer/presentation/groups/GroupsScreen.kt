package com.norm.vkgroupviewer.presentation.groups

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.norm.vkgroupviewer.domain.remote.dto.groups_info.GroupsInfo
import com.norm.vkgroupviewer.presentation.componets.GroupCard

@Composable
fun GroupsScreen(
    modifier: Modifier = Modifier,
    state: GroupsState,
    onClick: (Int) -> Unit,
) {
    if (state.groupsInfo != null) {
        Column(
            modifier = modifier,
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                items(
                    items = state.groupsInfo.response.items,
                    key = { group ->
                        group.id
                    },
                ) { group ->
                    GroupCard(
                        name = group.name,
                        type = group.type,
                        screenName = group.screen_name,
                        image = group.photo_50,
                        onClick = {
                            onClick(group.id)
                        }
                    )
                }
            }
        }
    } else {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "Groups entity"
            )
        }
    }
}