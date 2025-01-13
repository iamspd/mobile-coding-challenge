package com.example.audiopodcasts.navigation

import com.example.audiopodcasts.domain.Podcast

sealed class NavigationEvent {

    data class NavigationToDetailsScreen(val podcastId: String) : NavigationEvent()

    data class NavigationToHomeScreen(val podcast: Podcast) : NavigationEvent()
}