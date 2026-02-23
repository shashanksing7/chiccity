package chiccity.`in`.appWebView


import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController



@Composable
fun MainScreen() {

    val navController = rememberNavController()
    val webViewHolder = remember { WebViewHolder() }

    var isLoggedIn by remember { mutableStateOf(false) }
    Scaffold(
        bottomBar = {
            if (isLoggedIn) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                val currentDestination: NavDestination = when {
                    currentRoute?.contains("Cart") == true -> NavDestination.Cart
                    currentRoute?.contains("Orders") == true -> NavDestination.Orders
                    currentRoute?.contains("Account") == true -> NavDestination.Account
                    else -> NavDestination.Home
                }

                ChicCityBottomBar(
                    currentDestination = currentDestination,
                    onDestinationSelected = { destination ->
                        navController.navigate(destination) {
                            popUpTo(NavDestination.Home) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    )
    { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = "auth",
            modifier = Modifier.padding(innerPadding)
        ) {

            // 🔐 AUTH SCREEN FIRST
            composable("auth") {
                WebViewScreen(
                    url = "https://chiccity.in/my-account/",
                    webViewHolder = webViewHolder,
                    onLoginSuccess = {
                        isLoggedIn = true

                        navController.navigate(NavDestination.Home) {
                            popUpTo("auth") { inclusive = true }
                        }
                    }
                )
            }

            composable<NavDestination.Home> {
                WebViewScreen(
                    url = NavUrls.HOME,
                    webViewHolder = webViewHolder
                )
            }

            composable<NavDestination.Cart> {
                WebViewScreen(
                    url = NavUrls.CART,
                    webViewHolder = webViewHolder
                )
            }

            composable<NavDestination.Orders> {
                WebViewScreen(
                    url = NavUrls.ORDERS,
                    webViewHolder = webViewHolder
                )
            }

            composable<NavDestination.Account> {
                WebViewScreen(
                    url = NavUrls.ACCOUNT,
                    webViewHolder = webViewHolder
                )
            }
        }
    }
}
