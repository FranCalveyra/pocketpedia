package org.austral.pocketpedia.ui.shared.pokemon.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun PokemonCarousel(title: String, navController: NavHostController) {
    val listState = rememberLazyListState()

    val carouselTitle = Text(
        title,
        fontSize = 18.sp,
        style = TextStyle(
            textAlign = TextAlign.Start,
            fontFamily = FontFamily(Font(org.austral.pocketpedia.R.font.jetbrains_mono_regular)),
        ),
    )

    val carousel = LazyRow(
        state = listState,
        contentPadding = PaddingValues(horizontal = 40.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(10) { index ->
            Box(
                modifier = Modifier
                    .width(200.dp)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                PokemonCard(null, navController)
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        carouselTitle
        Spacer(modifier = Modifier.height(40.dp))
        carousel
    }
}