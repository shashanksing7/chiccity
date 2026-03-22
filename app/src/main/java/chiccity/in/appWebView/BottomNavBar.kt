package chiccity.`in`.appWebView

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val label: String,
    val destination: NavDestination,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem("Home", NavDestination.Home, Icons.Filled.Home, Icons.Outlined.Home),
    BottomNavItem("Cart", NavDestination.Cart, Icons.Filled.ShoppingCart, Icons.Outlined.ShoppingCart),
    BottomNavItem("Orders", NavDestination.Orders, Icons.Filled.List, Icons.Outlined.List),
    BottomNavItem("Account", NavDestination.Account, Icons.Filled.Person, Icons.Outlined.Person),
)

@Composable
fun ChicCityBottomBar(
    currentDestination: NavDestination,
    onDestinationSelected: (NavDestination) -> Unit
) {
    NavigationBar {
        bottomNavItems.forEach { item ->
            // Logic: Compare the sealed class object instances
            val selected = currentDestination == item.destination
            
            NavigationBarItem(
                selected = selected,
                onClick = { onDestinationSelected(item.destination) },
                icon = {
                    Icon(
                        imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.label
                    )
                },
                label = { Text(item.label) }
            )
        }
    }
}
