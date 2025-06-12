package org.austral.pocketpedia.ui.screens.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.austral.pocketpedia.R
import org.austral.pocketpedia.domain.repository.Trainer
import org.austral.pocketpedia.ui.shared.image.GifImage
import org.austral.pocketpedia.ui.theme.gifImageSize
import org.austral.pocketpedia.ui.theme.gifImageSpacing
import org.austral.pocketpedia.ui.theme.googleButtonBorderStroke
import org.austral.pocketpedia.ui.theme.googleButtonCornerShape
import org.austral.pocketpedia.ui.theme.googleButtonPadding
import org.austral.pocketpedia.ui.theme.googleButtonSize
import org.austral.pocketpedia.ui.theme.googleButtonSpacerWidth
import org.austral.pocketpedia.ui.theme.profileScreenPadding
import org.austral.pocketpedia.ui.theme.profileSpacerHeight

@Composable
fun ProfileScreen() {
    val viewModel = hiltViewModel<ProfileViewModel>()
    val user by viewModel.userData.collectAsStateWithLifecycle()
    val selectedTrainer by viewModel.selectedTrainer.collectAsStateWithLifecycle()

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(profileScreenPadding)
    ) {
        if (user == null) {
            GoogleLoginButton(
                modifier = Modifier,
                onClick = viewModel::login,
                label = stringResource(R.string.continue_with_google)
            )
        } else {
            Column(modifier = Modifier.align(Alignment.Center)) {
                Text(
                    stringResource(R.string.welcome_trainer, user?.displayName ?: ""),
                    style = typography.titleLarge,
                    overflow = TextOverflow.Clip,
                    maxLines = 2
                )
                ProfileBody(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    selectedTrainer = selectedTrainer,
                    trainers = viewModel.trainers,
                    onTrainerSelected = viewModel::selectTrainer
                )
                Spacer(modifier = Modifier.height(profileSpacerHeight))
                GoogleLoginButton(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = viewModel::logout,
                    label = stringResource(R.string.sign_out)
                )
            }
        }
    }
}

@Composable
private fun GoogleLoginButton(
    onClick: () -> Unit, modifier: Modifier, label: String
) {
    GoogleButtonUI(
        modifier = modifier,
        onClick = onClick,
        label = label,
    )
}

@Composable
private fun GoogleButtonUI(
    onClick: () -> Unit, modifier: Modifier = Modifier, label: String
) {
    val googleLogo = R.drawable.google_logo
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White,
            contentColor = Color.Black,
        ),
        shape = RoundedCornerShape(googleButtonCornerShape),
        border = BorderStroke(googleButtonBorderStroke, Color.LightGray),
        contentPadding = PaddingValues(googleButtonPadding)
    ) {
        Image(
            modifier = Modifier.size(googleButtonSize),
            painter = painterResource(googleLogo),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(googleButtonSpacerWidth))
        Text(label)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileBody(
    modifier: Modifier = Modifier,
    selectedTrainer: Trainer,
    trainers: List<Trainer>,
    onTrainerSelected: (Trainer) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        GifImage(resource = selectedTrainer.drawable, modifier = Modifier.size(gifImageSize))
        Spacer(modifier = Modifier.height(gifImageSpacing))
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                readOnly = true,
                value = selectedTrainer.name,
                onValueChange = {},
                label = { Text(stringResource(R.string.select_your_trainer_avatar)) },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                trainers.forEach { trainer ->
                    DropdownMenuItem(onClick = {
                        onTrainerSelected(trainer)
                        expanded = false
                    }) {
                        Text(text = trainer.name)
                    }
                }
            }
        }
    }
}