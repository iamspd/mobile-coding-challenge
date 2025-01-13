package com.example.audiopodcasts.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.audiopodcasts.domain.Podcast
import com.example.audiopodcasts.navigation.NavigationEvent
import com.example.audiopodcasts.repository.PodcastRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PodcastDetailsViewModel @Inject constructor(
    private val podcastRepository: PodcastRepository
) : ViewModel() {

    private val _podcast = mutableStateOf<Podcast?>(null)
    val podcast: State<Podcast?> get() = _podcast

    private val _isFavourite = mutableStateOf(false)
    val isFavourite: State<Boolean> get() = _isFavourite

    private val _navigationEvent = mutableStateOf<NavigationEvent?>(null)
    val navigationEvent: State<NavigationEvent?> get() = _navigationEvent

    fun getPodcastDetails(id: String) {
        viewModelScope.launch {
            podcastRepository.getPodcastById(id).collect {
                _podcast.value = it
                _isFavourite.value = it.isFavourite?.not() ?: false
            }
        }
    }

    fun updateFavouritePodcast(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            podcastRepository.updatePodcast(id)
            _isFavourite.value = _podcast.value?.isFavourite?.not() ?: false
        }
    }

    fun navigateBackToHomeScreen() {
        _navigationEvent.value = _podcast.value?.let { NavigationEvent.NavigationToHomeScreen(it) }
    }

    fun removeNavigationEvent() {
        _navigationEvent.value = null
    }
}