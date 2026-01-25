package com.mm.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {

    @Serializable
    data object ScreenMain : Route

    @Serializable
    data object ScreenSearch : Route

    @Serializable
    data object ScreenFavourites : Route

    @Serializable
    data class ScreenDetail(
        val tmdbId: Long,
        val mediaType: Int
    ) : Route
}