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
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import org.austral.pocketpedia.R
import org.austral.pocketpedia.domain.models.pokemon.PokemonType
import org.austral.pocketpedia.domain.models.response.Ability
import org.austral.pocketpedia.ui.shared.pokemon.type.PokemonTypeTag
import org.austral.pocketpedia.ui.theme.abilityTagCornerSize
import org.austral.pocketpedia.ui.theme.abilityTagEndPadding
import org.austral.pocketpedia.ui.theme.abilityTagHorizontalPadding
import org.austral.pocketpedia.ui.theme.abilityTagTopPadding
import org.austral.pocketpedia.ui.theme.abilityTagVerticalPadding
import org.austral.pocketpedia.ui.theme.cardSectionCornerSize
import org.austral.pocketpedia.ui.theme.cardSectionElevation
import org.austral.pocketpedia.ui.theme.cardSectionSpacing
import org.austral.pocketpedia.ui.theme.clearHyphens
import org.austral.pocketpedia.ui.theme.getPokemonColor
import org.austral.pocketpedia.ui.theme.pokemonBottomCornerSize
import org.austral.pocketpedia.ui.theme.pokemonCardSectionHorizontalPadding
import org.austral.pocketpedia.ui.theme.pokemonCardSectionInnerPadding
import org.austral.pocketpedia.ui.theme.pokemonCardSectionVerticalPadding
import org.austral.pocketpedia.ui.theme.pokemonHeaderPadding
import org.austral.pocketpedia.ui.theme.pokemonImageMaxSize
import org.austral.pocketpedia.ui.theme.pokemonImageTopPadding
import org.austral.pocketpedia.ui.theme.pokemonScreenBottomPadding
import org.austral.pocketpedia.ui.theme.pokemonScreenLoadingPadding
import org.austral.pocketpedia.ui.theme.rowInfoSpacing
import org.austral.pocketpedia.ui.theme.statBarMaxHeight
import org.austral.pocketpedia.ui.theme.statBarSpacing
import org.austral.pocketpedia.ui.theme.tidyStat
import org.austral.pocketpedia.ui.theme.transformToTitle
import org.austral.pocketpedia.ui.theme.typeRowSpacing

@Composable
fun PokemonScreen(
    pokemonName: String,
    navController: NavHostController
) {
    val viewModel = hiltViewModel<PokemonViewModel>()

    val pokemon by viewModel.pokemon.collectAsStateWithLifecycle()
    val loading by viewModel.isLoading.collectAsStateWithLifecycle()
    val retry by viewModel.retry.collectAsStateWithLifecycle()


    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(Color(0xFFF8F8F8))
                .padding(bottom = pokemonScreenBottomPadding)
        ) {
            when {
                loading -> {
                    CircularProgressIndicator(
                        color = Color.Gray,
                        modifier = Modifier
                            .size(pokemonScreenLoadingPadding)
                            .align(Alignment.CenterHorizontally)
                    )
                }

                retry || pokemon == null -> {
                    Text(stringResource(R.string.error_present))
                    Button(onClick = { viewModel.retryApiCall(pokemonName) }) {
                        Text(stringResource(R.string.retry))
                    }
                }

                else -> {
                    val p = pokemon!!
                    val pokemonColor = getPokemonColor(p)

                    // Header
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                pokemonColor,
                                shape = RoundedCornerShape(
                                    bottomStart = pokemonBottomCornerSize,
                                    bottomEnd = pokemonBottomCornerSize
                                )
                            )
                            .padding(pokemonHeaderPadding)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            IconButton(
                                onClick = { navController.popBackStack() },
                                modifier = Modifier.align(Alignment.Start)
                            ) {
                                Icon(
                                    Icons.AutoMirrored.Default.ArrowBack,
                                    contentDescription = null
                                )
                            }

                            Text(
                                text = transformToTitle(p.name),
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(text = "#${p.id}", fontSize = 18.sp, color = Color.White)

                            AsyncImage(
                                model = p.sprites.frontDefault,
                                contentDescription = p.name,
                                modifier = Modifier
                                    .size(pokemonImageMaxSize)
                                    .padding(top = pokemonImageTopPadding)
                            )
                        }
                    }

                    // About
                    CardSection(title = stringResource(R.string.about)) {
                        RowInfo(stringResource(R.string.height), "${tidyStat(p.height)} m")
                        RowInfo(stringResource(R.string.weight), "${tidyStat(p.weight)} kg")
                        TypeRow(p.types.first(), p.types.getOrNull(1))
                    }

                    // Abilities
                    CardSection(title = stringResource(R.string.abilities)) {
                        p.abilities.forEach { AbilityTag(it) }
                    }

                    // Base Stats
                    CardSection(title = stringResource(R.string.base_stats)) {
                        p.stats.forEach {
                            StatBar(it.stat.name, it.baseStat.toInt(), barColor = pokemonColor)
                        }
                    }
                }
            }
        }
    }
}


// Reusable section for cards
@Composable
fun CardSection(title: String, content: @Composable () -> Unit) {
    Card(
        shape = RoundedCornerShape(cardSectionCornerSize),
        elevation = cardSectionElevation,
        modifier = Modifier
            .padding(
                horizontal = pokemonCardSectionHorizontalPadding,
                vertical = pokemonCardSectionVerticalPadding
            )
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(pokemonCardSectionInnerPadding)) {
            Text(text = title, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.height(cardSectionSpacing))
            content()
        }
    }
}

// Row for displaying basic info like height, weight, type
@Composable
fun RowInfo(label: String, value: String, isBold: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontSize = 16.sp, color = Color.Gray)
        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal,
            color = if (isBold) Color.Black else Color.Gray
        )
    }
    Spacer(modifier = Modifier.height(rowInfoSpacing))
}

@Composable
fun TypeRow(firstType: PokemonType, secondType: PokemonType?) {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(R.string.type), fontSize = 16.sp, color = Color.Gray)
        Row {
            PokemonTypeTag(firstType)
            Spacer(modifier = Modifier.width(typeRowSpacing))
            secondType?.let { it -> PokemonTypeTag(secondType) }
        }
    }
    Spacer(modifier = Modifier.height(typeRowSpacing))
}

// Ability Tag UI
@Composable
fun AbilityTag(ability: Ability) {
    val abilityName = ability.ability.name
    val abilityText = if (ability.isHidden) stringResource(
        R.string.hidden, abilityName
    ) else abilityName

    Box(
        modifier = Modifier
            .padding(end = abilityTagEndPadding, top = abilityTagTopPadding)
            .background(Color(0xFFE0E0E0), shape = RoundedCornerShape(abilityTagCornerSize))
            .padding(horizontal = abilityTagHorizontalPadding, vertical = abilityTagVerticalPadding)
    ) {
        Text(
            text = clearHyphens(transformToTitle(abilityText)),
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )
    }
}

// Base Stats Bar UI
@Composable
fun StatBar(label: String, value: Int, maxStat: Int = 150, barColor: Color) {
    val curedLabel = if (label.contains("-")) clearHyphens(label) else transformToTitle(label)

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = curedLabel, fontSize = 16.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(statBarSpacing))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(statBarMaxHeight)
                .background(Color.LightGray, shape = CircleShape)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(value.toFloat() / maxStat)
                    .height(statBarMaxHeight)
                    .background(barColor, shape = CircleShape)
            )
        }
        Spacer(modifier = Modifier.height(statBarSpacing))
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