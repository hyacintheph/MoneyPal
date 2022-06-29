package com.example.moneypal.ui.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.moneypal.R
import com.example.moneypal.ui.home.chat.ChatEmpty
import com.example.moneypal.ui.home.profile.accountGraph
import com.example.moneypal.ui.home.tontine.tontineNavGraph
import com.example.moneypal.ui.theme.MoneyPalTheme
import com.example.moneypal.utils.NavRoutes
import com.example.moneypal.viewmodels.TontineViewModel
import com.example.moneypal.viewmodels.UserViewModel

sealed class Screen(val route: String, @StringRes val resourceId: Int,
                    val icon:@Composable () -> Unit) {
    object HomeScreen: Screen("${NavRoutes.HomeScreen}", R.string.home,
        icon = { Icon(painter = painterResource(R.drawable.moneypal_icon_home),
            contentDescription = "HomeIcon") })
    object ChatScreen: Screen("${NavRoutes.ChatScreen}", R.string.chat,
        icon = { Icon(painter = painterResource(R.drawable.moneypal_icon_message),
            contentDescription = "ChatIcon") })
    object ProfileScreen: Screen("${NavRoutes.ProfileScreen}", R.string.profile,
        icon = { Image(painter = painterResource(id = R.drawable.placeholder_image),
            contentDescription = stringResource(id = R.string.image_placeholder),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .border(
                    width = 2.dp, color = MaterialTheme.colors.primary,
                    shape = CircleShape
                )
                .size(20.dp)
                .clip(CircleShape)

        )
        })
}

val items = listOf(
    Screen.HomeScreen,
    Screen.ChatScreen,
    Screen.ProfileScreen
)
@Composable
fun Home(
    modifier: Modifier = Modifier,
    tontineViewModel: TontineViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel()
){
    var active by rememberSaveable() {
        mutableStateOf(false)
    }
    val authUser = userViewModel.authUser.observeAsState()
    MoneyPalTheme {
        val navController = rememberNavController()
        Scaffold(
            bottomBar = {
                BottomNavigation(backgroundColor = if (isSystemInDarkTheme())
                    MaterialTheme.colors.primary
                else Color.White) {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
                    items.forEach { screen ->
                        BottomNavigationItem(
                            icon = screen.icon,
                            label = { Text(stringResource(screen.resourceId)) },
                            selected = currentDestination?.hierarchy?.any
                            { it.route == screen.route } == true,
                            onClick = {
                                navController.navigate(screen.route) {
                                    // Pop up to the start destination of the graph to
                                    // avoid building up a large stack of destinations
                                    // on the back stack as users select items
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    // Avoid multiple copies of the same destination when
                                    // reselecting the same item
                                    launchSingleTop = true
                                    // Restore state when reselecting a previously selected item
                                    restoreState = true
                                }
                            },
                            selectedContentColor = MaterialTheme.colors.primary
                        )
                    }
                }
            }
        ) { innerPadding ->
            NavHost(navController,
                startDestination = Screen.HomeScreen.route,
                modifier = Modifier.padding(innerPadding)) {

                tontineNavGraph(navController = navController,
                    tontineViewModel = tontineViewModel,
                    userViewModel = userViewModel)
                composable(Screen.ChatScreen.route) { ChatEmpty(navController = navController) }
                accountGraph(navController = navController)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeLightPreview(){
    Home()
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun HomeDarkPreview(){
    Home()
}