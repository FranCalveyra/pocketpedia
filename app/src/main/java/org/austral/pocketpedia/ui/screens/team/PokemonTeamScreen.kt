package org.austral.pocketpedia.ui.screens.team

import androidx.compose.foundation.clickable
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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import org.austral.pocketpedia.R
import org.austral.pocketpedia.domain.models.team.PokemonTeam
import org.austral.pocketpedia.ui.shared.pokemon.card.PokemonCarousel
import org.austral.pocketpedia.ui.shared.text.TypingText

@Composable
fun PokemonTeamScreen(
    navController: NavHostController,
) {

    val viewModel = hiltViewModel<PokemonTeamViewModel>()
    val teams:List<PokemonTeam> by viewModel.teams.collectAsState()

    var selectedTeam by remember { mutableStateOf<PokemonTeam?>(null) }

    LaunchedEffect(Unit) { viewModel.loadTeams() }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            TypingText(
                text = stringResource(R.string.your_teams),
                repeatTyping = true
            )
            Spacer(Modifier.height(16.dp))

            Text(stringResource(R.string.work_in_progress))

            if (teams.isEmpty()) {
                Text(
                    stringResource(R.string.no_teams_yet),
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(Modifier.height(24.dp))
            } else {
                teams.forEach { team ->
                    PokemonCarousel(
                        title = team.teamName,
                        pokemonList = team.team,
                        navController = navController,
                    )
                    Spacer(Modifier.height(24.dp))
                }
            }

        }
        FloatingActionButton(
            backgroundColor = Color.Gray,
            onClick = { },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(top = 16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = stringResource(R.string.create_team))
        }
    }
}



