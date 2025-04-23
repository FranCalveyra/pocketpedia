package org.austral.pocketpedia.ui.screens.pokedex

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import org.austral.pocketpedia.R
import org.austral.pocketpedia.ui.shared.navigation.PocketPediaRoutes
import org.austral.pocketpedia.ui.theme.pokedexLoadingHeaderHeight
import org.austral.pocketpedia.ui.theme.pokedexOverallPadding
import org.austral.pocketpedia.ui.theme.pokedexTextFieldPadding

@Composable
fun PokedexScreen(
    navController: NavHostController,
    viewModel: PokedexViewModel = hiltViewModel()
) {
    val query by viewModel.query.collectAsState()
    val results by viewModel.searchResults.collectAsState()
    val loading by viewModel.loading.collectAsState()

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
//            .background(pokemonTeamPreviewBackground())
            .padding(pokedexOverallPadding)
    ) {
        OutlinedTextField(
            textStyle = TextStyle(color = Color.Black),
            value = query,
            onValueChange = {
                viewModel.onQueryChanged(it)
            },
            placeholder = { Text(stringResource(R.string.search_pokemon), color = Color.Black) },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = pokedexTextFieldPadding),
            keyboardOptions = KeyboardOptions.Default.copy(),
            keyboardActions = KeyboardActions(
                onSearch = {
                    viewModel.onSearch()
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }
            )
        )

        if (loading) {
            Spacer(Modifier.height(pokedexLoadingHeaderHeight))
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
        }

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(results) { pokemon ->
                PokemonSearchResultCard(pokemon) {
                    navController.navigate(
                        "${PocketPediaRoutes.Pokemon.name}/${pokemon.name.lowercase()}"
                    )
                }
            }
        }
    }
}


@Composable
fun pokemonTeamPreviewBackground(): Brush {
    // Liked this gradient
    // Target Gradient: Color(0xFF1D1D1D), Color(0xFF2E2E2E)
    return Brush.linearGradient(
        colors = listOf(Color.White, Color.White),
        start = Offset.Zero,
        end = Offset.Infinite
    )
}


