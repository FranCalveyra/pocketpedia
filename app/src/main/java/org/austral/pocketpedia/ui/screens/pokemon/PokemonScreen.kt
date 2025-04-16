package org.austral.pocketpedia.ui.screens.pokemon

import androidx.compose.foundation.Image
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
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import org.austral.pocketpedia.R
import org.austral.pocketpedia.domain.mappers.PokemonTypeMapper
import org.austral.pocketpedia.ui.shared.pokemon.type.PokemonTypeTag

@Composable
// TODO: get the Pokemon model as parameter
fun PokemonScreen(pokemonName: String = "Charizard", navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .background(Color(0xFFF8F8F8))
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color(0xFFFF5A5F),
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
                    text = pokemonName,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(text = "#006", fontSize = 18.sp, color = Color.White)

                Image(
                    painter = painterResource(id = R.drawable.charizard),
                    contentDescription = pokemonName,
                    modifier = Modifier
                        .size(180.dp)
                        .padding(top = 8.dp)
                )
            }
        }

        // About Section
        CardSection(title = stringResource(R.string.about)) {
            RowInfo(label = stringResource(R.string.height), value = "1.7 m")
            RowInfo(label = stringResource(R.string.weight), value = "90.5 kg")
            TypeRow(firstType = "fire", secondType = "flying")
        }

        // Abilities Section
        CardSection(title = stringResource(R.string.abilities)) {
            AbilityTag("Blaze")
            AbilityTag("Solar Power (Hidden)")
        }

        // Base Stats Section
        CardSection(title = stringResource(R.string.base_stats)) {
            StatBar(label = stringResource(R.string.hp), value = 78)
            StatBar(label = stringResource(R.string.attack), value = 84)
            StatBar(label = stringResource(R.string.defense), value = 78)
            StatBar(label = stringResource(R.string.sp_atk), value = 109)
            StatBar(label = stringResource(R.string.sp_def), value = 85)
            StatBar(label = stringResource(R.string.speed), value = 100)
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
fun TypeRow(firstType: String, secondType: String?) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = stringResource(R.string.type), fontSize = 16.sp, color = Color.Gray)
        Row {
            PokemonTypeTag(PokemonTypeMapper.fromString(firstType))
            secondType?.let { it -> PokemonTypeTag(PokemonTypeMapper.fromString(it)) }
        }
    }
    Spacer(modifier = Modifier.height(4.dp))
}

// Ability Tag UI
@Composable
fun AbilityTag(ability: String) {
    Box(
        modifier = Modifier
            .padding(end = 8.dp, top = 4.dp)
            .background(Color(0xFFE0E0E0), shape = RoundedCornerShape(50))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = ability,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )
    }
}

// Base Stats Bar UI
@Composable
fun StatBar(label: String, value: Int, maxStat: Int = 150) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, fontSize = 16.sp, color = Color.Gray)
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
    PokemonScreen(navController = NavHostController(context = LocalContext.current))
}