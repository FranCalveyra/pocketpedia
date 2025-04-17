package org.austral.pocketpedia.ui.shared.pokemon.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import org.austral.pocketpedia.R
import org.austral.pocketpedia.domain.models.pokemon.Pokemon
import org.austral.pocketpedia.domain.models.pokemon.PokemonType
import org.austral.pocketpedia.ui.shared.navigation.PocketPediaRoutes
import org.austral.pocketpedia.ui.shared.pokemon.type.PokemonTypeTag
import org.austral.pocketpedia.ui.theme.getPokemonColor
import org.austral.pocketpedia.ui.theme.transformToTitle

@Composable
// TODO: use current pokemon's real data
fun PokemonCard(pokemon: Pokemon?, navController: NavHostController) {
    var firstType: PokemonType
    var secondType: PokemonType? = null
    if (pokemon == null) {
        firstType = PokemonType.NORMAL
    } else {
        firstType = pokemon.types.first()
        secondType = if (pokemon.types.size > 1) pokemon.types[1] else null
    }


    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, Color.Transparent, shape = RoundedCornerShape(18.dp))
            .background(color = getPokemonColor(pokemon), shape = RoundedCornerShape(18.dp))
            .padding(10.dp)
            .clickable(onClick = {
                val route = "${PocketPediaRoutes.Pokemon.name}/${pokemon?.name}"
                navController.navigate(route)
            })

    ) {
        CardTitle(pokemon?.name ?: "Charizard")

        if (pokemon == null) {
            Image(
                painter = painterResource(R.drawable.missigno),
                contentDescription = "",
                modifier = Modifier
                    .height(LocalContext.current.resources.displayMetrics.heightPixels.dp * 0.15f / LocalContext.current.resources.displayMetrics.density)
                    .aspectRatio(1f)
            )

        } else {
            AsyncImage(
                model = pokemon.sprites.frontDefault,
                contentDescription = "",
                modifier = Modifier
                    .height(LocalContext.current.resources.displayMetrics.heightPixels.dp * 0.15f / LocalContext.current.resources.displayMetrics.density)
                    .aspectRatio(1f)
            )

        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(10.dp)
        ) {
            PokemonTypeTag(firstType)
            secondType?.let { it -> PokemonTypeTag(secondType) }
            Text("#${pokemon?.id ?: 0}")
        }

    }
}

@Preview
@Composable
fun PokemonCardPreview() {
    PokemonCard(null, NavHostController(LocalContext.current))
}


@Composable
private fun CardTitle(title: String) {
    // TODO: enhance this
    val curedTitle = transformToTitle(title)
    Text(
        curedTitle, style = TextStyle(
            fontWeight = FontWeight.W800
        )
    )
}
