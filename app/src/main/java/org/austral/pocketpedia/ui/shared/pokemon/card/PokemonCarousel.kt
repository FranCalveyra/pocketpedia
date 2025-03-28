package org.austral.pocketpedia.ui.shared.pokemon.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PokemonCarousel() {
    val listState = rememberLazyListState()
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        LazyRow(
            state = listState,
            contentPadding = PaddingValues(horizontal = 40.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(10) { index ->
                Box(
                    modifier = Modifier
                        .width(200.dp)
                        .padding(horizontal = 16.dp)

                ) {
                    PokemonCard(null)
                }
            }
        }
    }
}