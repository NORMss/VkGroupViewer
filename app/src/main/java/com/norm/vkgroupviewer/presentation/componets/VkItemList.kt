@file:OptIn(ExperimentalMaterial3Api::class)

package com.norm.vkgroupviewer.presentation.componets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import com.norm.vkgroupviewer.presentation.Dimens.largeSpacer
import com.norm.vkgroupviewer.presentation.Dimens.mediumSpacer
import com.norm.vkgroupviewer.presentation.Dimens.roundedSmale

@Composable
fun VkItemList(
    isRefresh: Boolean,
    onRefresh: () -> Unit,
    count: Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val density = LocalDensity.current

    val refreshState = rememberPullToRefreshState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = largeSpacer)
            .pullToRefresh(
                state = refreshState,
                isRefreshing = isRefresh,
                onRefresh = onRefresh,
            )
            .graphicsLayer(
                translationY = with(density) { (androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.PositionalThreshold + com.norm.vkgroupviewer.presentation.Dimens.mediumSpacer).toPx() * refreshState.distanceFraction }
            ),
        verticalArrangement = Arrangement.spacedBy(mediumSpacer),
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
                        text = "Number of groups: ${count}",
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }
        }
    }
}