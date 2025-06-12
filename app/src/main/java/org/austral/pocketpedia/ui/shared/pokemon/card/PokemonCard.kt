package org.austral.pocketpedia.ui.shared.pokemon.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import org.austral.pocketpedia.R
import org.austral.pocketpedia.domain.models.pokemon.Pokemon
import org.austral.pocketpedia.domain.models.pokemon.PokemonType
import org.austral.pocketpedia.ui.shared.navigation.PocketPediaRoutes
import org.austral.pocketpedia.ui.shared.pokemon.type.PokemonTypeTag
import org.austral.pocketpedia.ui.theme.cardBorderWidth
import org.austral.pocketpedia.ui.theme.cardCornerSize
import org.austral.pocketpedia.ui.theme.cardPadding
import org.austral.pocketpedia.ui.theme.getContrastColor
import org.austral.pocketpedia.ui.theme.getPokemonColor
import org.austral.pocketpedia.ui.theme.removeButtonOffset
import org.austral.pocketpedia.ui.theme.removeButtonSize
import org.austral.pocketpedia.ui.theme.transformToTitle

@Composable
fun PokemonCard(pokemon: Pokemon?, navController: NavHostController, onRemoveClick: (() -> Unit)?) {
    val firstType = pokemon?.types?.firstOrNull() ?: PokemonType.NORMAL
    val secondType = if ((pokemon?.types?.size ?: 0) > 1) pokemon?.types?.get(1) else null

    val bgColor =
        getPokemonColor(pokemon)
    val textColor = getContrastColor(bgColor)

    Box {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    cardBorderWidth,
                    Color.Transparent,
                    shape = RoundedCornerShape(cardCornerSize)
                )
                .background(color = bgColor, shape = RoundedCornerShape(cardCornerSize))
                .padding(cardPadding)
                .clickable {
                    val route = "${PocketPediaRoutes.Pokemon.name}/${pokemon?.name}"
                    navController.navigate(route)
                }
        ) {
            CardTitle(pokemon?.name ?: "Charizard", textColor)

            val imageModifier = Modifier
                .height(LocalContext.current.resources.displayMetrics.heightPixels.dp * 0.15f / LocalContext.current.resources.displayMetrics.density)
                .aspectRatio(1f)

            if (pokemon == null) {
                Image(
                    painter = painterResource(R.drawable.missigno),
                    contentDescription = "",
                    modifier = imageModifier
                )
            } else {
                AsyncImage(
                    model = pokemon.sprites.frontDefault,
                    contentDescription = "",
                    modifier = imageModifier
                )
            }

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = cardPadding)
            ) {
                PokemonTypeTag(firstType)
                secondType?.let { PokemonTypeTag(it) }
                Text("#${pokemon?.id ?: 0}", style = typography.bodySmall, color = textColor)
            }
        }
        RemoveButton(
            onRemoveClick = onRemoveClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = removeButtonOffset, y = removeButtonOffset)
        )
    }
}

@Composable
private fun CardTitle(title: String, textColor: Color) {
    val curedTitle = transformToTitle(title)
    Text(
        curedTitle,
        fontWeight = FontWeight.W800, color = textColor
    )
}

@Composable
private fun RemoveButton(onRemoveClick: (() -> Unit)?, modifier: Modifier = Modifier) {
    if (onRemoveClick != null) {
        FloatingActionButton(
            onClick = onRemoveClick,
            modifier = modifier
                .size(removeButtonSize),
            backgroundColor = Color.Red
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = stringResource(R.string.remove_pokemon),
                tint = Color.White
            )
        }
    }
}