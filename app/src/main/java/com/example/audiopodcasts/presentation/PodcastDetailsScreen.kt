package com.example.audiopodcasts.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.audiopodcasts.R
import com.example.audiopodcasts.domain.Podcast
import com.example.audiopodcasts.ui.theme.AudioPodcastsTheme
import com.example.audiopodcasts.ui.theme.ButtonPink
import com.example.audiopodcasts.ui.theme.WhiteText

@Composable
fun PodcastDetailsScreen(
    modifier: Modifier = Modifier,
    podcast: Podcast,
    podcastDetailsViewModel: PodcastDetailsViewModel
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 24.dp, bottom = 12.dp),
    ) {
        TopAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .padding(start = 12.dp)

                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    podcastDetailsViewModel.navigateBackToHomeScreen()
                },
            imageVector = Icons.Default.KeyboardArrowLeft,
            displayText = R.string.back_text,
            style = MaterialTheme.typography.h6
        )

        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = podcast.title,
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold
            )

            Text(
                modifier = Modifier.padding(top = 8.dp, bottom = 24.dp),
                text = podcast.publisher,
                style = MaterialTheme.typography.h6,
                fontStyle = FontStyle.Italic,
                color = Color.LightGray
            )

            AsyncImage(
                model = podcast.imageUrl,
                contentDescription = podcast.title,
                modifier = Modifier
                    .clip(RoundedCornerShape(14.dp))
                    .fillMaxWidth(fraction = 0.7f)
                    .fillMaxHeight(fraction = 0.7f),
                contentScale = ContentScale.Crop
            )

            val isFavourite = podcastDetailsViewModel.isFavourite.value

            Button(
                modifier = Modifier
                    .padding(top = 28.dp)
                    .height(54.dp)
                    .width(134.dp)
                    .align(Alignment.CenterHorizontally),
                onClick = {
                    podcastDetailsViewModel.updateFavouritePodcast(podcast.id)
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = ButtonPink,
                    contentColor = WhiteText
                ),
                shape = RoundedCornerShape(10.dp),
                elevation = ButtonDefaults.elevation(defaultElevation = 12.dp),
            ) {
                Text(
                    text = if (isFavourite) stringResource(R.string.favourited_text) else stringResource(
                        R.string.favourite_text
                    ),
                    style = MaterialTheme.typography.h6
                )
            }

            Text(
                modifier = Modifier.padding(top = 24.dp),
                text = podcast.description,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1,
                color = Color.Gray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPodcastDetailsScreen() {
    AudioPodcastsTheme {

        PodcastDetailsScreen(
            modifier = Modifier.fillMaxWidth(),
            podcast = Podcast(
                id = "1",
                title = "Podcast Title",
                publisher = "Publisher",
                imageUrl = "https://www.example.com/image.jpg",
                thumbnailUrl = null,
                description = "This is a podcast description. \nThis is very cool.",
                isFavourite = false
            ),
            podcastDetailsViewModel = hiltViewModel()
        )
    }
}