@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class, FlowPreview::class)

package com.norm.vkgroupviewer.presentation.componets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import com.norm.vkgroupviewer.presentation.Dimens.largeSpacer
import com.norm.vkgroupviewer.presentation.Dimens.mediumSpacer
import com.norm.vkgroupviewer.presentation.Dimens.roundedSmale
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch

@Composable
fun <T> VkItemList(
    isRefresh: Boolean,
    onRefresh: () -> Unit,
    count: Int? = null,
    collection: List<T>? = null,
    keySelector: (T) -> Int,
    modifier: Modifier = Modifier,
    content: @Composable (T) -> Unit,
) {
    val density = LocalDensity.current

    val refreshState = rememberPullToRefreshState()

    val scope = rememberCoroutineScope()

    val lazyListState = rememberLazyListState()

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        floatingActionButton = {
            if (remember { derivedStateOf { lazyListState.firstVisibleItemIndex } }.value > 5) {
                FloatingActionButton(
                    onClick = {
                        scope.launch {
                            lazyListState.scrollToItem(0)
                        }
                    },
                    shape = FloatingActionButtonDefaults.smallShape
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowUpward,
                        contentDescription = null,
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            if (collection != null) {
                PullToRefreshBox(
                    state = refreshState,
                    isRefreshing = isRefresh,
                    onRefresh = {
                        onRefresh()
                    },
                ) {
                    LazyColumn(
                        state = lazyListState,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = largeSpacer)
                            .graphicsLayer(
                                translationY = with(density) { (PullToRefreshDefaults.PositionalThreshold + mediumSpacer).toPx() * refreshState.distanceFraction }
                            ),
                        verticalArrangement = Arrangement.spacedBy(mediumSpacer),
                    ) {
                        count?.let {
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
                                            text = "Number of groups: $count",
                                            style = MaterialTheme.typography.bodyLarge,
                                        )
                                    }
                                }
                            }
                        }
                        items(
                            items = collection,
                            key = { item ->
                                keySelector(item)
                            }
                        ) { item ->
                            content(item)
                        }

                    }
                }
            } else if (isRefresh) {
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
    }
}