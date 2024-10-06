@file:OptIn(ExperimentalMaterial3Api::class)

package com.norm.vkgroupviewer.presentation.groups

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import com.norm.vkgroupviewer.domain.remote.dto.groups_info.GroupsInfo
import com.norm.vkgroupviewer.domain.remote.dto.groups_info.Item
import com.norm.vkgroupviewer.domain.remote.dto.groups_info.Response
import com.norm.vkgroupviewer.presentation.Dimens.largeSpacer
import com.norm.vkgroupviewer.presentation.Dimens.mediumSpacer
import com.norm.vkgroupviewer.presentation.Dimens.roundedSmale
import com.norm.vkgroupviewer.presentation.componets.GroupCard

@Composable
fun GroupsScreen(
    modifier: Modifier = Modifier,
    state: GroupsState,
    onClick: (String) -> Unit,
    onRefresh: () -> Unit,
) {
    val density = LocalDensity.current

    val refreshState = rememberPullToRefreshState()

    if (state.groupsInfo != null) {
        Column(
            modifier = modifier,
        ) {
            PullToRefreshBox(
                state = refreshState,
                isRefreshing = state.isLoadingGroups,
                onRefresh = {
                    onRefresh()
                },
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = largeSpacer)
                        .pullToRefresh(
                            state = refreshState,
                            isRefreshing = state.isLoadingGroups,
                            onRefresh = onRefresh,
                        )
                        .graphicsLayer(
                            translationY = with(density) { (PullToRefreshDefaults.PositionalThreshold + mediumSpacer).toPx() * refreshState.distanceFraction }
                        ),
                    verticalArrangement = Arrangement.spacedBy(mediumSpacer)
                ) {
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(roundedSmale)),
                            colors = CardColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                disabledContentColor = MaterialTheme.colorScheme.onSecondaryContainer.copy(
                                    alpha = 0.7f
                                ),
                                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer.copy(
                                    alpha = 0.7f
                                ),
                            ),
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        vertical = largeSpacer,
                                        horizontal = mediumSpacer,
                                    ),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start,
                            ) {
                                Text(
                                    text = "Number of groups: ${state.groupsInfo.response.count}",
                                    style = MaterialTheme.typography.bodyLarge,
                                )
                            }
                        }

                    }
                    items(
                        items = state.groupsInfo.response.items,
                        key = { group ->
                            group.id
                        },
                    ) { group ->
                        GroupCard(
                            name = group.name,
                            type = group.type,
                            domain = group.screen_name,
                            image = group.photo_50,
                            onClick = {
                                onClick(group.screen_name ?: group.id.toString())
                            }
                        )
                    }
                }
            }
        }
    } else if (state.isLoadingGroups && state.groupsInfo == null) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator()
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

@Preview
@Composable
private fun PreviewGroupsScreenGroups() {
    GroupsScreen(
        modifier = Modifier
            .fillMaxSize(),
        state = groupsStateGroupsPreview,
        onClick = {

        },
        onRefresh = {

        },
    )
}

@Preview
@Composable
private fun PreviewGroupsScreenNotGroups() {
    GroupsScreen(
        modifier = Modifier
            .fillMaxSize(),
        state = groupsStatePreview,
        onClick = {

        },
        onRefresh = {

        },
    )
}

@Preview
@Composable
private fun PreviewGroupsScreenNotGroupsIsLoading() {
    GroupsScreen(
        modifier = Modifier
            .fillMaxSize(),
        state = groupsStateNotGroupsIsLoadingPreview,
        onClick = {

        },
        onRefresh = {

        },
    )
}

@Preview
@Composable
private fun PreviewGroupsScreenGroupsIsLoading() {
    GroupsScreen(
        modifier = Modifier
            .fillMaxSize(),
        state = groupsStateGroupsIsLoadingPreview,
        onClick = {

        },
        onRefresh = {

        },
    )
}

val groupsInfoPreview = GroupsInfo(
    response = Response(
        count = 3,
        items = listOf(
            Item(
                admin_level = 1,
                id = 123,
                is_admin = 0,
                is_advertiser = 1,
                is_closed = 0,
                is_member = 2,
                name = "Test",
                photo_50 = "",
                photo_100 = "",
                photo_200 = "",
                type = "",
                screen_name = "test_group",
            ),
            Item(
                admin_level = 0,
                id = 234,
                is_admin = 0,
                is_advertiser = 1,
                is_closed = 0,
                is_member = 2,
                name = "TestTest TestTest TestTest TestTestTestTest TestTestTestTestTestTest TestTest",
                photo_50 = "",
                photo_100 = "",
                photo_200 = "",
                type = "",
                screen_name = "test_gr",
            ),
            Item(
                admin_level = 0,
                id = 345,
                is_admin = 0,
                is_advertiser = 1,
                is_closed = 0,
                is_member = 2,
                name = "TestTe",
                photo_50 = "",
                photo_100 = "",
                photo_200 = "",
                type = "",
                screen_name = "te_test",
            )
        ),
        last_updated_time = 1,
    )
)

val groupsStatePreview = GroupsState()

val groupsStateNotGroupsIsLoadingPreview = GroupsState(
    isLoadingGroups = true,
)

val groupsStateGroupsPreview = GroupsState(
    groupsInfo = groupsInfoPreview,
)

val groupsStateGroupsIsLoadingPreview = GroupsState(
    groupsInfo = groupsInfoPreview,
    isLoadingGroups = true,
)
