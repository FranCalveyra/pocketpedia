package org.austral.pocketpedia.ui.shared.pokemon.card

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import org.austral.pocketpedia.domain.models.pokemon.Pokemon
import org.austral.pocketpedia.ui.theme.Typography
import org.austral.pocketpedia.ui.theme.carouselCardMaxWidth
import org.austral.pocketpedia.ui.theme.carouselHorizontalPadding
import org.austral.pocketpedia.ui.theme.carouselSpaceBetween
import org.austral.pocketpedia.ui.theme.carouselVerticalPadding

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun PokemonCarousel(
    title: String,
    pokemonList: List<Pokemon?>,
    navController: NavHostController,
) {
    val itemsToShow = pokemonList.filterNotNull()
    if (itemsToShow.isEmpty()) {
        return Spacer(Modifier)
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
            style = Typography.bodyLarge.copy(
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


@RequiresApi(Build.VERSION_CODES.Q)
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