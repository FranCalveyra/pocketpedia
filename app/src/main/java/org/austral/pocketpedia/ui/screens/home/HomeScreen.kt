package org.austral.pocketpedia.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import org.austral.pocketpedia.R
import org.austral.pocketpedia.ui.shared.pokemon.card.PokemonCarousel
import org.austral.pocketpedia.ui.shared.text.TypingText

@Composable
fun HomeScreen(navController: NavHostController) {
    val text = LocalContext.current.getString(R.string.welcome_title)
    val spacing = 120.dp

    val viewModel = hiltViewModel<HomeViewModel>()
    val pokemonLists by viewModel.pokemonLists.collectAsStateWithLifecycle()
    val loading by viewModel.isLoading.collectAsStateWithLifecycle()
    val regions = viewModel.regions

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        if (loading) {
            CircularProgressIndicator()
        } else {
            TypingText(text = text, repeatTyping = true)
            Spacer(modifier = Modifier.height(spacing))
            pokemonLists.mapIndexed { index, list ->
                println("List index: $index ")
                println("Retrieved pokemons: $list")

                PokemonCarousel(
                    title = regions[index],
                    pokemonList = list,
                    navController
                )
            }
        }
    }
}
