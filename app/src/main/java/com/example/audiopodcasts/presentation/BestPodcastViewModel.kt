package com.example.audiopodcasts.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.audiopodcasts.data.mappers.toPodcast
import com.example.audiopodcasts.domain.Podcast
import com.example.audiopodcasts.local.PodcastEntity
import com.example.audiopodcasts.navigation.NavigationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class BestPodcastViewModel @Inject constructor(
    pager: Pager<Int, PodcastEntity>
) : ViewModel() {

    val podcastPagingFlow = pager
        .flow
        .map { pagingData ->
            pagingData.map { it.toPodcast() }
        }.cachedIn(viewModelScope)

    private val _navigationEvent = mutableStateOf<NavigationEvent?>(null)
    val navigationEvent: State<NavigationEvent?> get() = _navigationEvent

    fun podcastListItemSelected(podcast: Podcast) {
        _navigationEvent.value = NavigationEvent.NavigationToDetailsScreen(podcastId = podcast.id)
    }

    fun removeNavigationEvent() {
        _navigationEvent.value = null
    }
}