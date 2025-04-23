package org.austral.pocketpedia.ui.shared.pokemon.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import org.austral.pocketpedia.R
import org.austral.pocketpedia.domain.models.pokemon.Pokemon
import org.austral.pocketpedia.ui.theme.carouselCardMaxWidth
import org.austral.pocketpedia.ui.theme.carouselHorizontalPadding
import org.austral.pocketpedia.ui.theme.carouselSpaceBetween
import org.austral.pocketpedia.ui.theme.carouselVerticalPadding

@Composable
fun PokemonCarousel(
    title: String,
    pokemonList: List<Pokemon?>, // This null safe is only for the preview
    navController: NavHostController,
) {
    val itemsToShow = pokemonList.filterNotNull()
    if (itemsToShow.isEmpty()) {
        return Text(stringResource(R.string.no_pokemon_found))
    }

    val listState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = carouselHorizontalPadding),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.jetbrains_mono_regular)),
                textAlign = TextAlign.Start
            )
        )

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
                    PokemonCard(pokemon, navController)
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