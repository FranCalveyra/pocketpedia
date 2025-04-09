package org.austral.pocketpedia.ui.shared.navigation


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
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


@Composable
fun BottomNavbar(
    onNavigate: (String) -> Unit
) {
    val homeTab = NavItem(
        pocketPediaRoute = PocketPediaRoutes.Home,
        icon = Icons.Default.Home, // TODO: add custom icons
        label = getRouteName(PocketPediaRoutes.Home)
    )

    val searchTab = NavItem(
        pocketPediaRoute = PocketPediaRoutes.Pokedex,
        icon = Icons.Default.Search,
        label = getRouteName(PocketPediaRoutes.Pokedex)
    )

    val pokemonTeamTab = NavItem(
        pocketPediaRoute = PocketPediaRoutes.PokemonTeam,
        icon = Icons.Default.List,
        label = "My Team"
    )

    val profileTab = NavItem(
        pocketPediaRoute = PocketPediaRoutes.Profile,
        icon = Icons.Default.Person
    )

    val navBarItems = listOf(homeTab, searchTab, pokemonTeamTab, profileTab)

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
                icon = { Icon(navItem.icon, contentDescription = "") },
                label = {
                    Text(
                        navItem.label,
                        fontFamily = FontFamily(Font(R.font.jetbrains_mono_regular))
                    )
                }

            )
        }
    }
}

fun getRouteName(route: PocketPediaRoutes): String {
    val name = route.name
    return name.substring(0, 1).toUpperCase() + name.substring(1)
}


data class NavItem(
    val pocketPediaRoute: PocketPediaRoutes,
    val route: String = pocketPediaRoute.name.toString().toLowerCase(),
    val icon: ImageVector,
    val label: String = pocketPediaRoute.name
)