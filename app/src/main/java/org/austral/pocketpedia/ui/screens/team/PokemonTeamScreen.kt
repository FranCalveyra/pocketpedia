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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import org.austral.pocketpedia.R
import org.austral.pocketpedia.domain.models.team.PokemonTeam
import org.austral.pocketpedia.ui.screens.pokedex.PokedexViewModel
import org.austral.pocketpedia.ui.shared.auth.BiometricAuthViewModel
import org.austral.pocketpedia.ui.shared.auth.BiometricAuthedComposable
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
    val pokedexViewModel: PokedexViewModel = hiltViewModel()
    val searchResults by pokedexViewModel.searchResults.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var query by remember { mutableStateOf("") }
    var selectedTeam by remember { mutableStateOf(teams.firstOrNull()?.teamName ?: "") }
    var newTeamName by remember { mutableStateOf("") }


    val authViewModel = hiltViewModel<BiometricAuthViewModel>()
    val isAuthenticated by authViewModel.isAuthenticated.collectAsState()

    BiometricAuthedComposable(
        onAuthenticate = { activity -> authViewModel.authenticate(activity) },
        isAuthenticated = isAuthenticated
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            PokemonTeamScreenBody(teams, navController)
            FloatingActionButton(
                backgroundColor = Color.Gray,
                onClick = { showDialog = true },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(top = FABTopPadding)
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = stringResource(R.string.create_team)
                )
            }
            if (showDialog) {
                AddPokemonDialog(
                    teams = teams,
                    searchResults = searchResults,
                    query = query,
                    onQueryChange = {
                        query = it
                        pokedexViewModel.onQueryChanged(it)
                    },
                    selectedTeam = selectedTeam,
                    onTeamSelected = { selectedTeam = it },
                    newTeamName = newTeamName,
                    onNewTeamNameChange = { newTeamName = it },
                    onCreateTeam = {
                        viewModel.createTeam(newTeamName)
                        selectedTeam = newTeamName
                        newTeamName = ""
                    },
                    onPokemonClick = { pokemon ->
                        viewModel.addPokemonToTeam(selectedTeam, pokemon.name)
                        showDialog = false
                    },
                    onDismiss = { showDialog = false }
                )
            }
        }
    }
}

@Composable
private fun PokemonTeamScreenBody(teams: List<PokemonTeam>, navController: NavHostController) {
    val viewModel = hiltViewModel<PokemonTeamViewModel>()
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
                    onRemovePokemonClick = { pokemon ->
                        viewModel.removePokemonFromTeam(team.teamName, pokemon!!.name)
                    }
                )
                Spacer(Modifier.height(pokemonTeamSpaceBetween))
            }
        }
    }
}



