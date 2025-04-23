package org.austral.pocketpedia.ui.shared.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.austral.pocketpedia.ui.screens.home.HomeScreen
import org.austral.pocketpedia.ui.screens.pokedex.PokedexScreen
import org.austral.pocketpedia.ui.screens.pokemon.PokemonScreen
import org.austral.pocketpedia.ui.screens.profile.ProfileScreen
import org.austral.pocketpedia.ui.screens.team.PokemonTeamScreen

@Composable
fun NavHostComposable(innerPadding: PaddingValues, navController: NavHostController) {
    val modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .padding(20.dp)
    NavHost(
        navController = navController, startDestination = PocketPediaRoutes.Home.name,
        modifier = modifier
    ) {
        composable(route = PocketPediaRoutes.Home.name) { HomeScreen(navController) }
        composable(
            route = "${PocketPediaRoutes.Pokemon.name}/{pokemonName}",
            arguments = listOf(navArgument("pokemonName") { type = NavType.StringType })
        ) { backStackEntry ->
            val pokemonName = backStackEntry.arguments!!.getString("pokemonName")
            PokemonScreen(pokemonName ?: "Pikachu", navController)
        }

        composable(route = PocketPediaRoutes.Pokedex.name) {
            PokedexScreen(navController)
        }
        composable(route = PocketPediaRoutes.PokemonTeam.name) {
            PokemonTeamScreen(navController)
        }

        composable(route = PocketPediaRoutes.Profile.name) {
            ProfileScreen(navController)
        }
    }
}