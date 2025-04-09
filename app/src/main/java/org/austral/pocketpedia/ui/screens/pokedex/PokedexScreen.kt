package org.austral.pocketpedia.ui.screens.pokedex

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults.InputField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokedexScreen(query: MutableState<String>) {
    val modifier = Modifier
        .padding(horizontal = 8.dp, vertical = 4.dp)
        .clickable(onClick = {})


    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        SearchBar(
            inputField = {
                InputField(
                    query = query.value,
                    onQueryChange = { newValue -> query.value = newValue },
                    onSearch = { value -> searchPokemon(value) },
                    expanded = false,
                    onExpandedChange = {},
                )
            },
            expanded = false,
            onExpandedChange = {},
            content = {},
        )
    }
}

private fun searchPokemon(pokemonName: String) {

}
