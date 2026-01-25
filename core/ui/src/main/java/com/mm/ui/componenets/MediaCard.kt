package com.mm.ui.componenets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.mm.domain.model.MediaDto
import com.mm.domain.model.MediaType
import com.mm.ui.R

@Composable
fun MediaCard(
    item: MediaDto,
    modifier: Modifier = Modifier,
    onDeleteClick: (() -> Unit)? = null,
    onClick: (tmdbId: Long, mediaType: MediaType) -> Unit
) {
    Card(
        modifier = modifier
            .clickable {
                onClick(item.mediaId, item.mediaType)
            },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Box {
                if (item.mediaPosterPath != null) {
                    AsyncImage(
                        model = item.mediaPosterPath,
                        contentDescription = item.mediaTitle,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(Color.Gray)
                    )
                }
                if (onDeleteClick != null) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_delete),
                        contentDescription = "Sil",
                        colorFilter = ColorFilter.tint(Color.White),
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(8.dp)
                            .size(32.dp)
                            .background(
                                color = Color.Black.copy(alpha = 0.6f),
                                shape = CircleShape
                            )
                            .padding(6.dp)
                            .clickable { onDeleteClick() }
                    )
                }


                item.mediaVotAvarage?.let { vote ->
                    VoteBadge(
                        vote = vote,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(6.dp)
                    )
                }
            }

            Text(
                text = item.mediaTitle,
                modifier = Modifier
                    .padding(6.dp)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun VoteBadge(
    vote: Double,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(
                color = Color.Black.copy(alpha = 0.7f),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_star),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Color.Yellow)
        )

        Spacer(Modifier.width(4.dp))

        Text(
            text = String.format("%.1f", vote),
            color = Color.White,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

