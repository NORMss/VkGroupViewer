package com.norm.vkgroupviewer.presentation.componets

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.norm.vkgroupviewer.presentation.Dimens.mediumSpacer
import com.norm.vkgroupviewer.presentation.Dimens.photoMedium
import com.norm.vkgroupviewer.presentation.Dimens.roundedExtraSmale
import com.norm.vkgroupviewer.presentation.Dimens.roundedSmale
import com.norm.vkgroupviewer.presentation.Dimens.smallSpacer

@Composable
fun FriendCard(
    name: String,
    userId: String,
    isMoreInfo: Boolean,
    image: String? = null,
    screenName: String? = null,
    showOrHideMoreInfo: () -> Unit,
    onClick: () -> Unit,
    onCopyId: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(roundedSmale))
            .clickable {
                onClick()
            },
    ) {
        Column(
            modifier = Modifier
                .padding(mediumSpacer)
                .animateContentSize(),
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
            ) {
                AsyncImage(
                    model = image,
                    contentDescription = "image_group",
                    modifier = Modifier
                        .size(
                            photoMedium
                        )
                        .clip(
                            CircleShape
                        )
                        .align(Alignment.CenterVertically),
                )
                Spacer(
                    modifier = Modifier
                        .width(mediumSpacer)
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.bodyLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    screenName?.let {
                        Text(
                            text = "@$screenName",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier
                                .background(
                                    MaterialTheme.colorScheme.surfaceContainer,
                                    shape = RoundedCornerShape(roundedExtraSmale)
                                )
                                .padding(smallSpacer),
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                }
                IconButton(
                    onClick = {
                        showOrHideMoreInfo()
                    },
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                ) {
                    Icon(
                        imageVector = if (isMoreInfo) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                        contentDescription = null,
                    )
                }

            }
            if (isMoreInfo) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                ) {
                    Text(
                        text = "ID: $userId",
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Spacer(
                        modifier = Modifier
                            .width(smallSpacer)
                    )
                    IconButton(
                        onClick = {
                            onCopyId()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = null,
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewFriendCard() {
    FriendCard(
        name = "Test User",
        userId = "123456",
        screenName = "test",
        isMoreInfo = false,
        showOrHideMoreInfo = { /*TODO*/ },
        onClick = { /*TODO*/ }) {
    }
}

@Preview
@Composable
private fun PreviewFriendCardMoreInfo() {
    FriendCard(
        name = "Test User",
        userId = "123456",
        screenName = "test",
        isMoreInfo = true,
        showOrHideMoreInfo = { /*TODO*/ },
        onClick = { /*TODO*/ }) {
    }
}