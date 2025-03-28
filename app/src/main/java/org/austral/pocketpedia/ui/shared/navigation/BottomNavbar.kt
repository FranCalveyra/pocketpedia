package org.austral.pocketpedia.ui.shared.navigation



import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import org.austral.pocketpedia.R


// TODO: add navigation functionality
@Composable
fun BottomNavbar(
    onNavigate: (String) -> Unit
) {
    val homeTab = NavItem(
        route = PocketPediaRoutes.Home.name,
        icon = Icons.Default.Home, // TODO: add custom icons
        label = getRouteName(PocketPediaRoutes.Home)
    )

    val searchTab = NavItem(
        route = PocketPediaRoutes.Pokedex.name,
        icon = Icons.Default.Search, // TODO: add custom icons
        label = getRouteName(PocketPediaRoutes.Pokedex)
    )

    val profileTab = NavItem(
        route = PocketPediaRoutes.Profile.name,
        icon = Icons.Default.Person, // TODO: add custom icons
        label = getRouteName(PocketPediaRoutes.Profile) //TODO: create this automatically based on route
    )

    val navBarItems = listOf(homeTab, searchTab, profileTab)

    NavbarView(navBarItems, onNavigate)


}

@Composable
fun NavbarView(navItems: List<NavItem>, onNavigate: (String) -> Unit) {
    var selectedIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar {
        navItems.forEachIndexed { index, navItem ->
            NavigationBarItem(
                selected = index == selectedIndex,
                onClick = {
                    selectedIndex = index
                    onNavigate(navItem.route)
                },
                icon = { Icon(navItem.icon, contentDescription="") },
                label = { Text(navItem.label, fontFamily = FontFamily(Font(R.font.jetbrains_mono_regular))) }

            )
        }
    }
}

fun getRouteName(route: PocketPediaRoutes): String {
    val name = route.name
    return name.substring(0,1).toUpperCase() + name.substring(1)
}


data class NavItem(val route: String, val icon: ImageVector, val label: String)