package com.example.audiopodcasts.presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.audiopodcasts.domain.Podcast
import com.example.audiopodcasts.ui.theme.AudioPodcastsTheme

@Composable
fun PodcastItem(
    modifier: Modifier = Modifier,
    podcast: Podcast,
    bestPodcastViewModel: BestPodcastViewModel = hiltViewModel()
) {
    Column(
        modifier = modifier
            .height(IntrinsicSize.Max)
            .fillMaxWidth()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ){
                bestPodcastViewModel.podcastListItemSelected(podcast = podcast)
                Log.v("Main", podcast.id)
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            AsyncImage(
                model = podcast.thumbnailUrl,
                contentDescription = podcast.title,
                modifier = Modifier
                    .width(94.dp)
                    .height(94.dp)
                    .clip(RoundedCornerShape(14.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(24.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, end = 24.dp)
            ) {
                Text(
                    text = podcast.title,
                    style = MaterialTheme.typography.body1,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = podcast.publisher,
                    style = MaterialTheme.typography.body2,
                    fontStyle = FontStyle.Italic,
                    color = Color.Gray,
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        Divider(
            modifier = Modifier
                .height(1.dp)
                .background(Color.LightGray)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PodcastItemPreview() {
    AudioPodcastsTheme {
        PodcastItem(
            podcast = Podcast(
                id = "abc",
                title = "Podcast",
                publisher = "XYZ Corp",
                description = "This is a podcast description. \nWhich is very cool.",
                imageUrl = null,
                thumbnailUrl = null,
                isFavourite = false
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}