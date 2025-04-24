package org.austral.pocketpedia.ui.screens.pokemon

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
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
import org.austral.pocketpedia.ui.theme.errorSpacing
import org.austral.pocketpedia.ui.theme.getPokemonColor
import org.austral.pocketpedia.ui.theme.pokemonBottomCornerSize
import org.austral.pocketpedia.ui.theme.pokemonCardSectionInnerPadding
import org.austral.pocketpedia.ui.theme.pokemonHeaderPadding
import org.austral.pocketpedia.ui.theme.pokemonImageMaxSize
import org.austral.pocketpedia.ui.theme.pokemonImageTopPadding
import org.austral.pocketpedia.ui.theme.pokemonScreenBottomPadding
import org.austral.pocketpedia.ui.theme.pokemonScreenLoadingPadding
import org.austral.pocketpedia.ui.theme.rowInfoSpacing
import org.austral.pocketpedia.ui.theme.sectionSpacing
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

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = pokemonScreenBottomPadding)
        ) {
            when {
                loading -> CircularProgressIndicator(
                    modifier = Modifier
                        .size(pokemonScreenLoadingPadding)
                        .align(Alignment.CenterHorizontally),
                    color = MaterialTheme.colorScheme.secondary
                )

                retry || pokemon == null -> {
                    Text(
                        text = stringResource(R.string.error_present),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(errorSpacing))
                    Button(
                        onClick = { viewModel.retryApiCall(pokemonName) },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text(
                            text = stringResource(R.string.retry),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }

                else -> {
                    val p = pokemon!!
                    val headerColor = getPokemonColor(p)

                    // Header
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = headerColor,
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
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onPrimary
                                )
                            }

                            Text(
                                text = transformToTitle(p.name),
                                style = MaterialTheme.typography.titleLarge,
                            )
                            Text(
                                text = "#${p.id}",
                                style = MaterialTheme.typography.bodyMedium,
                            )

                            AsyncImage(
                                model = p.sprites.frontDefault,
                                contentDescription = p.name,
                                modifier = Modifier
                                    .size(pokemonImageMaxSize)
                                    .padding(top = pokemonImageTopPadding)
                            )
                        }
                    }

                    // About Section
                    Spacer(modifier = Modifier.height(sectionSpacing))
                    CardSection(title = stringResource(R.string.about)) {
                        RowInfo(
                            label = stringResource(R.string.height),
                            value = "${tidyStat(p.height)} m"
                        )
                        RowInfo(
                            label = stringResource(R.string.weight),
                            value = "${tidyStat(p.weight)} kg"
                        )
                        TypeRow(
                            firstType = p.types.first(),
                            secondType = p.types.getOrNull(1)
                        )
                    }

                    // Abilities Section
                    Spacer(modifier = Modifier.height(sectionSpacing))
                    CardSection(title = stringResource(R.string.abilities)) {
                        p.abilities.forEach { AbilityTag(it) }
                    }

                    // Base Stats Section
                    Spacer(modifier = Modifier.height(sectionSpacing))
                    CardSection(title = stringResource(R.string.base_stats)) {
                        p.stats.forEach {
                            StatBar(
                                label = it.stat.name,
                                value = it.baseStat.toInt(),
                                barColor = headerColor
                            )
                        }
                    }
                }
            }
        }
    }
}

// ---- Reusable UI ----

@Composable
fun CardSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(cardSectionCornerSize),
        elevation = CardDefaults.cardElevation(defaultElevation = cardSectionElevation)
    ) {
        Column(
            modifier = Modifier
                .padding(pokemonCardSectionInnerPadding)
                .fillMaxWidth()
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(cardSectionSpacing))
            content()
        }
    }
}

@Composable
fun RowInfo(
    label: String,
    value: String,
    isBold: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal
            ),
            color = if (isBold)
                MaterialTheme.colorScheme.onSurface
            else
                MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
    Spacer(modifier = Modifier.height(rowInfoSpacing))
}

@Composable
fun TypeRow(
    firstType: PokemonType,
    secondType: PokemonType?
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.type),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Row {
            PokemonTypeTag(firstType)
            Spacer(modifier = Modifier.width(typeRowSpacing))
            secondType?.let { PokemonTypeTag(it) }
        }
    }
    Spacer(modifier = Modifier.height(typeRowSpacing))
}

@Composable
fun AbilityTag(ability: Ability) {
    val abilityName = ability.ability.name
    val abilityText = if (ability.isHidden)
        stringResource(R.string.hidden, abilityName)
    else abilityName

    Box(
        modifier = Modifier
            .padding(
                end = abilityTagEndPadding,
                top = abilityTagTopPadding
            )
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = RoundedCornerShape(abilityTagCornerSize)
            )
            .padding(
                horizontal = abilityTagHorizontalPadding,
                vertical = abilityTagVerticalPadding
            )
    ) {
        Text(
            text = clearHyphens(transformToTitle(abilityText)),
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}

@Composable
fun StatBar(
    label: String,
    value: Int,
    maxStat: Int = 150,
    barColor: Color
) {
    val curedLabel = if (label.contains("-")) clearHyphens(label)
    else transformToTitle(label)

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = curedLabel,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(statBarSpacing))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(statBarMaxHeight)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = CircleShape
                )
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
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PokemonScreenPreview() {
    PokemonScreen("Pikachu", NavHostController(LocalContext.current))
}