package com.example.audiopodcasts.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.audiopodcasts.R
import com.example.audiopodcasts.domain.Podcast

@Composable
fun BestPodcastScreen(
    modifier: Modifier = Modifier,
    podcasts: LazyPagingItems<Podcast>,
    bestPodcastViewModel: BestPodcastViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = podcasts.loadState) {
        if (podcasts.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context, "Error: " + (podcasts.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 24.dp, top = 24.dp)
    ) {

        TopAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max),
            displayText = R.string.app_name,
            style = MaterialTheme.typography.h5
        )

        Box(
            modifier = modifier
                .fillMaxSize()
        ) {
            if (podcasts.loadState.refresh is LoadState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(podcasts.itemCount) { item ->
                        podcasts[item]?.let { podcast ->
                            PodcastItem(
                                podcast = podcast,
                                modifier = Modifier.fillMaxWidth(),
                                bestPodcastViewModel = bestPodcastViewModel
                            )
                        }

                    }
                    item {
                        if (podcasts.loadState.append is LoadState.Loading) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}