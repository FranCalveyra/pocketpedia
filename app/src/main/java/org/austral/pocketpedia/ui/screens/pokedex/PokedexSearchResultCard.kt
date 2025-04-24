package org.austral.pocketpedia.ui.screens.pokedex

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil3.compose.AsyncImage
import org.austral.pocketpedia.domain.models.pokemon.Pokemon
import org.austral.pocketpedia.ui.screens.pokemon.TypeRow
import org.austral.pocketpedia.ui.theme.Typography
import org.austral.pocketpedia.ui.theme.pokemonSearchResultCardCornerSize
import org.austral.pocketpedia.ui.theme.pokemonSearchResultCardElevation
import org.austral.pocketpedia.ui.theme.resultCardImageMaxSize
import org.austral.pocketpedia.ui.theme.resultCardInnerPadding
import org.austral.pocketpedia.ui.theme.resultCardOuterPadding
import org.austral.pocketpedia.ui.theme.resultCardSpacing

@Composable
fun PokedexSearchResultCard(pokemon: Pokemon, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(pokemonSearchResultCardCornerSize),
        modifier = Modifier
            .padding(resultCardOuterPadding)
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = pokemonSearchResultCardElevation
    ) {
        Row(
            modifier = Modifier.padding(resultCardInnerPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = pokemon.sprites.frontDefault,
                contentDescription = pokemon.name,
                modifier = Modifier.size(resultCardImageMaxSize)
            )
            Spacer(modifier = Modifier.padding(resultCardSpacing))
            Column {
                Text(
                    text = pokemon.name.replaceFirstChar { it.uppercase() },
                    style = Typography.titleMedium
                )
                TypeRow(pokemon.types.first(), pokemon.types.getOrNull(1))
            }
        }
    }
}
