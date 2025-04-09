package org.austral.pocketpedia.ui.shared.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.austral.pocketpedia.domain.models.team.PokemonTeam
import org.austral.pocketpedia.ui.screens.home.HomeScreen
import org.austral.pocketpedia.ui.screens.pokedex.PokedexScreen
import org.austral.pocketpedia.ui.screens.pokemon.PokemonScreen
import org.austral.pocketpedia.ui.screens.team.PokemonTeamScreen

@Composable
fun NavHostComposable(innerPadding: PaddingValues, navController: NavHostController) {
    val searchQuery = remember { mutableStateOf<String>("") }
    val pokemonTeams = remember { mutableStateOf(arrayOfNulls<PokemonTeam>(3).toList()) }

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
            route = "${PocketPediaRoutes.Pokemon.name}/{pokemon_name}",
            arguments = listOf(navArgument("pokemon_name") { type = NavType.StringType })
        ) { backStackEntry ->
            val pokemonName = backStackEntry.arguments!!.getString("pokemon_name")
            PokemonScreen(pokemonName?.let { "Charizard" }.toString(), navController)
        }

        composable(route = PocketPediaRoutes.Pokedex.name) {
            PokedexScreen(searchQuery)
        }
        composable(route = PocketPediaRoutes.PokemonTeam.name) {
            PokemonTeamScreen(pokemonTeams.value as List<PokemonTeam>, navController)
        }
    }
}