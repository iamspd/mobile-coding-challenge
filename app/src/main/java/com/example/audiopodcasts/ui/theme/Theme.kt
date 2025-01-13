package com.example.audiopodcasts.ui.theme
import androidx.compose.material.MaterialTheme

import androidx.compose.runtime.Composable


@Composable
fun AudioPodcastsTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = MaterialTheme.colors,
        typography = Typography,
        content = content
    )
}