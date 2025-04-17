package org.austral.pocketpedia.ui.screens.pokemon

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import org.austral.pocketpedia.R
import org.austral.pocketpedia.domain.models.pokemon.PokemonType
import org.austral.pocketpedia.domain.models.response.Ability
import org.austral.pocketpedia.ui.shared.pokemon.type.PokemonTypeTag
import org.austral.pocketpedia.ui.theme.clearHyphens
import org.austral.pocketpedia.ui.theme.getPokemonColor
import org.austral.pocketpedia.ui.theme.transformToTitle

@Composable
// TODO: get the Pokemon model as parameter
fun PokemonScreen(pokemonName: String, navController: NavHostController) {
    val viewModel = hiltViewModel<PokemonViewModel>()
    val pokemon by viewModel.pokemon.collectAsStateWithLifecycle()
    val loading by viewModel.isLoading.collectAsStateWithLifecycle()
    val retry by viewModel.retry.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .background(Color(0xFFF8F8F8))
    ) {

        if (loading) {
            CircularProgressIndicator(
                color = Color.Gray,
                modifier = Modifier.size(48.dp)
            )
        } else if (retry || pokemon == null) {
            Text("There was an error")
            Button(
                onClick = { viewModel.retryApiCall(pokemonName) }
            ) {
                Text("Retry")
            }
        } else {

            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        getPokemonColor(pokemon),
                        shape = RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp)
                    )
                    .padding(16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Back Button
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.align(Alignment.Start)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = ""
                        )
                    }

                    Text(
                        text = transformToTitle(pokemon!!.name),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(text = "#${pokemon!!.id}", fontSize = 18.sp, color = Color.White)

                    AsyncImage(
                        model = pokemon!!.sprites.frontDefault,
                        contentDescription = pokemon!!.name,
                        modifier = Modifier
                            .size(180.dp)
                            .padding(top = 8.dp)
                    )
                }
            }

            val types = pokemon!!.types
            // About Section
            CardSection(title = stringResource(R.string.about)) {
                RowInfo(label = stringResource(R.string.height), value = "${pokemon!!.height} m")
                RowInfo(label = stringResource(R.string.weight), value = "${pokemon!!.weight} kg")
                TypeRow(
                    firstType = types.first(),
                    secondType = if (types.size > 1) types[1] else null
                )
            }

            // Abilities Section
            CardSection(title = stringResource(R.string.abilities)) {
                pokemon!!.abilities.map {
                    AbilityTag(it)
                }
            }

            // Base Stats Section
            val stats = pokemon!!.stats
            CardSection(title = stringResource(R.string.base_stats)) {
                stats.map { StatBar(it.stat.name, it.baseStat.toInt()) }
            }
        }
    }
}

// Reusable section for cards
@Composable
fun CardSection(title: String, content: @Composable () -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = 2.dp,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.height(8.dp))
            content()
        }
    }
}

// Row for displaying basic info like height, weight, type
@Composable
fun RowInfo(label: String, value: String, isBold: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontSize = 16.sp, color = Color.Gray)
        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal,
            color = if (isBold) Color.Black else Color.Gray
        )
    }
    Spacer(modifier = Modifier.height(4.dp))
}

@Composable
fun TypeRow(firstType: PokemonType, secondType: PokemonType?) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = stringResource(R.string.type), fontSize = 16.sp, color = Color.Gray)
        Row {
            PokemonTypeTag(firstType)
            secondType?.let { it -> PokemonTypeTag(secondType) }
        }
    }
    Spacer(modifier = Modifier.height(4.dp))
}

// Ability Tag UI
@Composable
fun AbilityTag(ability: Ability) {
    val abilityName = ability.ability.name
    val abilityText = if (ability.isHidden) stringResource(
        R.string.hidden,
        abilityName
    ) else abilityName

    Box(
        modifier = Modifier
            .padding(end = 8.dp, top = 4.dp)
            .background(Color(0xFFE0E0E0), shape = RoundedCornerShape(50))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = transformToTitle(abilityText),
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )
    }
}

// Base Stats Bar UI
@Composable
fun StatBar(label: String, value: Int, maxStat: Int = 150) {
    val curedLabel = if (label.contains("-")) clearHyphens(label) else transformToTitle(label)

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = curedLabel, fontSize = 16.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
                .background(Color.LightGray, shape = CircleShape)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(value.toFloat() / maxStat)
                    .height(12.dp)
                    .background(Color(0xFFFF5A5F), shape = CircleShape)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value.toString(),
            fontSize = 14.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
fun PokemonScreenPreview() {
    PokemonScreen("Pikachu", navController = NavHostController(context = LocalContext.current))
}