package com.norm.vkgroupviewer.presentation.groups

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.norm.vkgroupviewer.presentation.Dimens.largeSpacer
import com.norm.vkgroupviewer.presentation.Dimens.mediumSpacer
import com.norm.vkgroupviewer.presentation.componets.GroupCard

@Composable
fun GroupsScreen(
    modifier: Modifier = Modifier,
    state: GroupsState,
    onClick: (String) -> Unit,
) {
    if (state.groupsInfo != null) {
        Column(
            modifier = modifier,
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = largeSpacer),
                verticalArrangement = Arrangement.spacedBy(mediumSpacer)
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
                            onClick(group.screen_name ?: group.id.toString())
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