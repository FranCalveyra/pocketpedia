package org.austral.pocketpedia.ui.screens.team


import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import org.austral.pocketpedia.R
import org.austral.pocketpedia.domain.models.team.PokemonTeam
import org.austral.pocketpedia.ui.shared.pokemon.card.PokemonCarousel
import org.austral.pocketpedia.ui.shared.text.FixedTypingText
import org.austral.pocketpedia.ui.theme.FABTopPadding
import org.austral.pocketpedia.ui.theme.pokemonTeamPadding
import org.austral.pocketpedia.ui.theme.pokemonTeamSpaceBetween
import org.austral.pocketpedia.ui.theme.pokemonTeamSpacing

@Composable
fun PokemonTeamScreen(
    navController: NavHostController,
) {
    val context = LocalContext.current
    val viewModel = hiltViewModel<PokemonTeamViewModel>()
    val teams: List<PokemonTeam> by viewModel.teams.collectAsState()
    val isAuthenticated by viewModel.isAuthenticated.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadTeams()
        viewModel.authenticate(context)
    }

    val biometricAuthManager = remember { BiometricManager.from(context) }
    val isBiometricAvailable =
        remember { biometricAuthManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL) }


    when (isBiometricAvailable) {
        BiometricManager.BIOMETRIC_SUCCESS -> {
            Box(modifier = Modifier.fillMaxSize()) {
                if (isAuthenticated) {
                    PokemonTeamScreenBody(teams, navController)
                    FloatingActionButton(
                        backgroundColor = Color.Gray,
                        onClick = { },
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(top = FABTopPadding)
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = stringResource(R.string.create_team)
                        )
                    }
                } else {
                    Text(stringResource(R.string.auth_needed))
                }
            }
        }

        BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
            // No biometric features available on this device
            Text(text = stringResource(R.string.phone_not_prepared))
        }

        BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
            // Biometric features are currently unavailable.
            Text(text = stringResource(R.string.unavailable_biometric))
        }

        BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED -> {
            // Biometric features available but a security vulnerability has been discovered
            Text(text = stringResource(R.string.biometric_security_update_needed))
        }

        BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED -> {
            // Biometric features are currently unavailable because the specified options are incompatible with the current Android version..
            Text(text = stringResource(R.string.android_software_update_needed))
        }

        BiometricManager.BIOMETRIC_STATUS_UNKNOWN -> {
            // Unable to determine whether the user can authenticate using biometrics
            Text(text = stringResource(R.string.cant_use_biometric))
        }

        BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
            // The user can't authenticate because no biometric or device credential is enrolled.
            Text(text = stringResource(R.string.cant_use_biometric))
        }
    }
}

@Composable
private fun PokemonTeamScreenBody(teams: List<PokemonTeam>, navController: NavHostController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(pokemonTeamPadding)
            .verticalScroll(rememberScrollState())
    ) {
        FixedTypingText(
            text = stringResource(R.string.your_teams),
            repeatTyping = true,
            style = typography.titleLarge
        )

        Spacer(Modifier.height(pokemonTeamSpacing))

        Text(
            stringResource(R.string.work_in_progress),
            style = typography.bodyMedium,
        )

        if (teams.isEmpty()) {
            Text(
                stringResource(R.string.no_teams_yet),
                style = typography.bodyMedium,
                color = colorScheme.onSurface,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(Modifier.height(pokemonTeamSpaceBetween))
        } else {
            teams.forEach { team ->
                PokemonCarousel(
                    title = team.teamName,
                    pokemonList = team.team,
                    navController = navController,
                )
                Spacer(Modifier.height(pokemonTeamSpaceBetween))
            }
        }
    }
}



