package org.austral.pocketpedia.ui.screens.team

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import org.austral.pocketpedia.domain.models.team.PokemonTeam
import org.austral.pocketpedia.ui.shared.pokemon.card.PokemonCarousel

@Composable
//TODO: create a component for the team
fun PokemonTeamScreen(pokemonTeams: List<PokemonTeam>, navController: NavHostController){
    LazyVerticalGrid(
        columns = GridCells.Fixed(2)
    ) {
        itemsIndexed(pokemonTeams) { _, team->
            PokemonCarousel(title = team.teamName, pokemons = team.team, navController)
        }
    }
}