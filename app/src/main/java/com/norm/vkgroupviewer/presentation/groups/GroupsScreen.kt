@file:OptIn(ExperimentalMaterial3Api::class)

package com.norm.vkgroupviewer.presentation.groups

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.norm.vkgroupviewer.domain.remote.dto.groups_info.GroupsInfo
import com.norm.vkgroupviewer.domain.remote.dto.groups_info.Item
import com.norm.vkgroupviewer.domain.remote.dto.groups_info.Response
import com.norm.vkgroupviewer.presentation.componets.GroupCard
import com.norm.vkgroupviewer.presentation.componets.VkItemList

@Composable
fun GroupsScreen(
    modifier: Modifier = Modifier,
    state: GroupsState,
    onClick: (String) -> Unit,
    onRefresh: () -> Unit,
) {
    VkItemList(
        isRefresh = state.isLoadingGroups,
        onRefresh = onRefresh,
        count = state.groupsInfo?.response?.count,
        collection = state.groupsInfo?.response?.items,
        keySelector = {
            it.id
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
