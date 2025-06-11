package org.austral.pocketpedia.ui.shared.pokemon.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import org.austral.pocketpedia.domain.models.pokemon.Pokemon
import org.austral.pocketpedia.ui.theme.carouselCardMaxWidth
import org.austral.pocketpedia.ui.theme.carouselHorizontalPadding
import org.austral.pocketpedia.ui.theme.carouselSpaceBetween
import org.austral.pocketpedia.ui.theme.carouselVerticalPadding
import org.austral.pocketpedia.ui.theme.sectionSpacing

@Composable
fun PokemonCarousel(
    title: String,
    pokemonList: List<Pokemon?>,
    navController: NavHostController,
    onRemovePokemonClick: ((Pokemon?) -> Unit)? = null
) {
    val itemsToShow = pokemonList.filterNotNull()
    if (itemsToShow.isEmpty()) {
        return Spacer(Modifier)
    }

    val listState = rememberLazyListState()

    Column(
        modifier = Modifier.padding(
            horizontal = carouselHorizontalPadding,
            vertical = carouselVerticalPadding
        )
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = carouselVerticalPadding)
        )
        Spacer(modifier = Modifier.height(sectionSpacing))
        LazyRow(
            state = listState,
            horizontalArrangement = Arrangement.spacedBy(carouselSpaceBetween),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(pokemonList) { pokemon ->
                Box(
                    modifier = Modifier
                        .width(carouselCardMaxWidth)
                        .padding(vertical = carouselVerticalPadding)
                ) {
                    PokemonCard(
                        pokemon = pokemon,
                        navController = navController,
                        onRemoveClick = onRemovePokemonClick?.let { onClick -> { onClick(pokemon) } }
                    )
                }
            }
        }
    }
}

@Preview(
    backgroundColor = 0xFFFFFFFF,
    showBackground = true
)
@Composable
fun PokemonCarouselPreview() {
    PokemonCarousel(
        title = "Kanto",
        arrayOfNulls<Pokemon>(10).toList(),
        NavHostController(LocalContext.current)
    )
}