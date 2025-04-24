package org.austral.pocketpedia.ui.screens.team

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import org.austral.pocketpedia.R
import org.austral.pocketpedia.domain.models.team.PokemonTeam
import org.austral.pocketpedia.ui.shared.pokemon.card.PokemonCarousel
import org.austral.pocketpedia.ui.shared.text.FixedTypingText
import org.austral.pocketpedia.ui.theme.FABTopPadding
import org.austral.pocketpedia.ui.theme.pokemonTeamPadding
import org.austral.pocketpedia.ui.theme.pokemonTeamSpaceBetween
import org.austral.pocketpedia.ui.theme.pokemonTeamSpacing

@Composable
fun PokemonTeamScreen(
    navController: NavHostController,
) {

    val viewModel = hiltViewModel<PokemonTeamViewModel>()
    val teams: List<PokemonTeam> by viewModel.teams.collectAsState()

    LaunchedEffect(Unit) { viewModel.loadTeams() }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(pokemonTeamPadding)
                .verticalScroll(rememberScrollState())
        ) {
            FixedTypingText(
                text = stringResource(R.string.your_teams),
                repeatTyping = true,
                style = typography.titleLarge
            )

            Spacer(Modifier.height(pokemonTeamSpacing))

            Text(
                stringResource(R.string.work_in_progress),
                style = typography.bodyMedium,
            )

            if (teams.isEmpty()) {
                Text(
                    stringResource(R.string.no_teams_yet),
                    style = typography.bodyMedium,
                    color = colorScheme.onSurface,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(Modifier.height(pokemonTeamSpaceBetween))
            } else {
                teams.forEach { team ->
                    PokemonCarousel(
                        title = team.teamName,
                        pokemonList = team.team,
                        navController = navController,
                    )
                    Spacer(Modifier.height(pokemonTeamSpaceBetween))
                }
            }

        }
        FloatingActionButton(
            backgroundColor = Color.Gray,
            onClick = { },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(top = FABTopPadding)
        ) {
            Icon(Icons.Default.Add, contentDescription = stringResource(R.string.create_team))
        }
    }
}



