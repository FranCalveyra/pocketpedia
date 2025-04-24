package org.austral.pocketpedia.ui.shared.pokemon.type

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import org.austral.pocketpedia.domain.models.pokemon.PokemonType
import org.austral.pocketpedia.ui.theme.Typography
import org.austral.pocketpedia.ui.theme.pokemonTypeCornerSize
import org.austral.pocketpedia.ui.theme.pokemonTypeHorizontalPadding
import org.austral.pocketpedia.ui.theme.pokemonTypeVerticalPadding

@Composable
fun PokemonTypeTag(type: PokemonType) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(pokemonTypeCornerSize))
            .background(type.color)
            .padding(
                horizontal = pokemonTypeHorizontalPadding,
                vertical = pokemonTypeVerticalPadding
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = type.displayName,
            color = Color.White,
            style = Typography.bodySmall.copy(
                fontWeight = FontWeight.Bold
            )
        )
    }
}
