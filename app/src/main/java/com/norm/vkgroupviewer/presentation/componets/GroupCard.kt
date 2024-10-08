package com.norm.vkgroupviewer.presentation.componets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
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
fun GroupCard(
    image: String? = null,
    name: String,
    domain: String? = null,
    type: String,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(roundedSmale))
            .clickable {
                onClick()
            },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(mediumSpacer),
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
                domain?.let {
                    Text(
                        text = "@$domain",
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
            Text(
                text = type,
                modifier = Modifier
                    .align(Alignment.Bottom),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            )
        }
    }
}

@Preview
@Composable
private fun PreviewGroupCardFullInfo(

) {
    GroupCard(
        name = "Test Group",
        domain = "testgroup",
        type = "group",
        image = "https://upload.wikimedia.org/wikipedia/commons/d/de/Color-Green.JPG",
        onClick = {

        }
    )
}

@Preview
@Composable
private fun PreviewGroupCardNoImage(

) {
    GroupCard(
        name = "Test Group",
        domain = "testgroup",
        type = "group",
        onClick = {

        }
    )
}

@Preview
@Composable
private fun PreviewGroupCardNoImageNoScreeName(

) {
    GroupCard(
        name = "Test Group",
        type = "group",
        onClick = {

        }
    )
}