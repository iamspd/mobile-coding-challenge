package com.example.audiopodcasts

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.audiopodcasts.navigation.NavigationEvent
import com.example.audiopodcasts.presentation.BestPodcastScreen
import com.example.audiopodcasts.presentation.BestPodcastViewModel
import com.example.audiopodcasts.presentation.PodcastDetailsScreen
import com.example.audiopodcasts.presentation.PodcastDetailsViewModel
import com.example.audiopodcasts.ui.theme.AudioPodcastsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AudioPodcastsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    val navController = rememberNavController()
                    val bestPodcastViewModel = hiltViewModel<BestPodcastViewModel>()
                    val podcasts = bestPodcastViewModel.podcastPagingFlow.collectAsLazyPagingItems()
                    val podcastDetailsViewModel = hiltViewModel<PodcastDetailsViewModel>()


                    NavHost(
                        navController = navController,
                        startDestination = "BestPodcastScreen"
                    ) {

                        /**
                         * Root composable -
                         * Home screen that displays list of podcasts
                         */

                        composable(route = "BestPodcastScreen") {
                            BestPodcastScreen(
                                modifier = Modifier.padding(innerPadding),
                                podcasts = podcasts,
                                bestPodcastViewModel = bestPodcastViewModel
                            )
                        }

                        /**
                         * PodcastDetails screen that displays a podcast information
                         *      based on the given podcastId
                         */

                        composable(
                            route = "PodcastDetailsScreen?podcastId={podcastId}"
                        ) {
                            it.arguments?.getString("podcastId")
                                ?.let { id -> podcastDetailsViewModel.getPodcastDetails(id) }

                            podcastDetailsViewModel.podcast.value?.let { podcast ->
                                PodcastDetailsScreen(
                                    podcast = podcast,
                                    podcastDetailsViewModel = podcastDetailsViewModel
                                )
                            }
                        }
                    }

                    val homeScreenNavigationEvent = bestPodcastViewModel.navigationEvent.value
                    if (homeScreenNavigationEvent != null) {
                        when (homeScreenNavigationEvent) {
                            is NavigationEvent.NavigationToDetailsScreen -> {
                                navController.navigate(
                                    route = "PodcastDetailsScreen?podcastId=${homeScreenNavigationEvent.podcastId}"
                                )
                            }

                            else -> {
                                Log.e(
                                    stringResource(R.string.homescreen_navigationevent_tag),
                                    stringResource(R.string.something_went_wrong_msg)
                                )
                            }
                        }
                        bestPodcastViewModel.removeNavigationEvent()
                    }

                    val podcastDetailsNavigation = podcastDetailsViewModel.navigationEvent.value
                    if (podcastDetailsNavigation != null) {
                        when (podcastDetailsNavigation) {
                            is NavigationEvent.NavigationToHomeScreen -> {
                                navController.popBackStack()
                            }

                            else -> {
                                Log.e(
                                    stringResource(R.string.podcastdetailsscreen_navigationevent_tag),
                                    stringResource(R.string.something_went_wrong_msg)
                                )
                            }
                        }
                        podcastDetailsViewModel.removeNavigationEvent()
                    }
                }
            }
        }
    }
}