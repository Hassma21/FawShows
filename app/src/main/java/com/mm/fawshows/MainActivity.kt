package com.mm.fawshows

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mm.fawshows.ui.theme.FawshowsTheme
import com.mm.fawshows_detail.FawDetailScreen
import com.mm.fawshows_favourites.FawFavouritesScreen
import com.mm.fawshows_main.FawMainScreen
import com.mm.fawshows_search.FawSearchScreen
import com.mm.navigation.Route
import com.mm.ui.state.FabState
import com.mm.ui.state.TopAppBarState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FawshowsTheme {
                val navController = rememberNavController()

                var fabState by remember { mutableStateOf<FabState>(FabState.Hidden) }
                var topAppBarState by remember { mutableStateOf(TopAppBarState()) }
                val windowSizeClass = calculateWindowSizeClass(this)
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        if (topAppBarState.visible) {
                            TopAppBar(
                                title = { Text(topAppBarState.title) },
                                navigationIcon = {
                                    if (topAppBarState.showBack) {
                                        IconButton(onClick = { topAppBarState.onBackClick?.invoke() }) {
                                            Image(
                                                painter = painterResource(com.mm.ui.R.drawable.ic_arrow_back),
                                                contentDescription = "Back"
                                            )
                                        }
                                    }
                                }
                            )
                        }
                    },
                    bottomBar = {
                        if (shouldShowBottomBar(navController)) {
                            MainBottomBar(navController)
                        }
                    },
                    floatingActionButton = {
                        when (val fab = fabState) {
                            FabState.Hidden -> Unit
                            is FabState.Shown -> {
                                FloatingActionButton(onClick = fab.onClick) {
                                    Image(
                                        painter = painterResource(fab.iconRes),
                                        contentDescription = fab.contentDescription
                                    )
                                }
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Route.ScreenMain,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable<Route.ScreenMain> {
                            FawMainScreen(
                                onItemClick = { tmdbId, mediaType ->
                                    navController.navigate(
                                        Route.ScreenDetail(
                                            tmdbId,
                                            mediaType.ordinal
                                        )
                                    )
                                },
                                onAppBarChange = { topAppBarState = it }
                            )
                        }
                        composable<Route.ScreenSearch> {
                            FawSearchScreen(
                                onItemClick = { tmdbId, mediaType ->
                                    navController.navigate(
                                        Route.ScreenDetail(
                                            tmdbId,
                                            mediaType.ordinal
                                        )
                                    )
                                },
                                onBackPressed = { navController.popBackStack() },
                                onAppBarChange = { topAppBarState = it },
                            )
                        }
                        composable<Route.ScreenFavourites> {
                            FawFavouritesScreen(
                                onItemClick = { tmdbId, mediaType ->
                                    navController.navigate(
                                        Route.ScreenDetail(
                                            tmdbId,
                                            mediaType.ordinal
                                        )
                                    )
                                },
                                onBackPressed = { navController.popBackStack() },
                                onAppBarChange = { topAppBarState = it }
                            )
                        }
                        composable<Route.ScreenDetail> { backStackEntry ->
                            FawDetailScreen(
                                windowSizeClass = windowSizeClass,
                                onBackPressed = { navController.popBackStack() },
                                onFabChange = { fabState = it },
                                onAppBarChange = { topAppBarState = it }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun shouldShowBottomBar(navController: NavController): Boolean {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.hasRoute<Route.ScreenMain>() == true
}

@Composable
fun MainBottomBar(
    navController: NavController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        BottomBarItem(
            label = stringResource(R.string.home),
            selected = currentDestination?.hasRoute<Route.ScreenMain>() == true,
            onClick = {
                navController.navigate(Route.ScreenMain) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            iconRes = R.drawable.ic_home
        )

        BottomBarItem(
            label = stringResource(R.string.search),
            selected = currentDestination?.hasRoute<Route.ScreenSearch>() == true,
            onClick = {
                navController.navigate(Route.ScreenSearch) {
                    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            iconRes = R.drawable.ic_search,
        )

        BottomBarItem(
            label = stringResource(R.string.favourites),
            selected = currentDestination?.hasRoute<Route.ScreenFavourites>() == true,
            onClick = {
                navController.navigate(Route.ScreenFavourites) {
                    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            iconRes = R.drawable.ic_favorite
        )
    }
}

@Composable
private fun RowScope.BottomBarItem(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    iconRes: Int
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = {
            val tint = if (selected) Color.Red else Color.Gray
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = label,
                colorFilter = ColorFilter.tint(tint)
            )
        },
        label = { Text(label) }
    )
}

