package org.austral.pocketpedia.ui.screens.team

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import org.austral.pocketpedia.R
import org.austral.pocketpedia.ui.shared.pokemon.card.PokemonCarousel
import org.austral.pocketpedia.ui.shared.text.TypingText

@Composable
//TODO: create a component for the team
fun PokemonTeamScreen(navController: NavHostController) {
    val viewModel = hiltViewModel<PokemonTeamViewModel>()
    val pokemonTeams by viewModel.teams.collectAsState()
    Column {
        TypingText(text = stringResource(R.string.your_teams), repeatTyping = true)

        pokemonTeams.forEach { team ->
            PokemonCarousel(
                title = team.teamName,
                pokemonList = team.team,
                navController
            )
        }

    }
}