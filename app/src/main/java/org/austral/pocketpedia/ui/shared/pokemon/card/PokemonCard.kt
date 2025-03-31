package org.austral.pocketpedia.ui.shared.pokemon.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.austral.pocketpedia.R
import org.austral.pocketpedia.domain.mappers.PokemonTypeMapper
import org.austral.pocketpedia.domain.models.pokemon.Pokemon
import org.austral.pocketpedia.ui.shared.navigation.PocketPediaRoutes
import org.austral.pocketpedia.ui.shared.pokemon.type.PokemonTypeTag

@Composable
// TODO: use current pokemon's real data
fun PokemonCard(pokemon: Pokemon?, navController: NavHostController) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, Color.Transparent, shape = RoundedCornerShape(18.dp))
            .background(color = getPokemonColor(pokemon), shape = RoundedCornerShape(18.dp))
            .padding(10.dp)
            .clickable(onClick = {
                val route: String = "${PocketPediaRoutes.Pokemon.name}/charizard"
                navController.navigate(route)
            })

    ) {
//        CardTitle(pokemon?.name?.let { "" }.toString())

        CardTitle("Charizard")

        Image(painter = painterResource(R.drawable.charizard), contentDescription = "")

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(10.dp)
        ) {
            PokemonTypeTag(PokemonTypeMapper.fromString("fire"))
            Spacer(modifier = Modifier.width(8.dp))
//            Text("#${pokemon?.gameIndices?.first()?.gameIndex}")
            Text("#006")
        }

    }
}

@Composable
private fun CardTitle(title: String) {
    // TODO: enhance this
    Text(title, style = TextStyle(
        fontWeight = FontWeight.W800
    ))
}


private fun getPokemonColor(pokemon: Pokemon?): Color {
    // TODO
    return Color.Red
}
