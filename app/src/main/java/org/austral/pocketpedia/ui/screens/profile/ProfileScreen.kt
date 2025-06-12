package org.austral.pocketpedia.ui.screens.profile

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import org.austral.pocketpedia.R
import org.austral.pocketpedia.domain.repository.Trainer
import org.austral.pocketpedia.ui.theme.gifImageSize
import org.austral.pocketpedia.ui.theme.gifImageSpacing
import org.austral.pocketpedia.ui.theme.googleButtonBorderStroke
import org.austral.pocketpedia.ui.theme.googleButtonCornerShape
import org.austral.pocketpedia.ui.theme.googleButtonPadding
import org.austral.pocketpedia.ui.theme.googleButtonSize
import org.austral.pocketpedia.ui.theme.googleButtonSpacerWidth
import org.austral.pocketpedia.ui.theme.notificationTextBottomPadding
import org.austral.pocketpedia.ui.theme.profileScreenPadding
import org.austral.pocketpedia.ui.theme.profileSpacerHeight
import org.austral.pocketpedia.ui.theme.teamModalSpacing

@OptIn(ExperimentalPermissionsApi::class)
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun ProfileScreen() {
    val viewModel = hiltViewModel<ProfileViewModel>()
    val user by viewModel.userData.collectAsStateWithLifecycle()
    val selectedTrainer by viewModel.selectedTrainer.collectAsStateWithLifecycle()
    val notificationViewModel = hiltViewModel<ScheduleNotificationViewModel>()
    var showNotificationDialog by remember { mutableStateOf(false) }

    val postNotificationPermission =
        rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)

    LaunchedEffect(key1 = true) {
        if (!postNotificationPermission.status.isGranted) {
            postNotificationPermission.launchPermissionRequest()
        }
    }

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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = { showNotificationDialog = true }) {
                        Icon(
                            imageVector = Icons.Outlined.Notifications,
                            contentDescription = stringResource(R.string.schedule_notification)
                        )
                    }
                }
                Text(
                    stringResource(R.string.welcome_trainer, user?.displayName ?: ""),
                    style = typography.titleLarge,
                    overflow = TextOverflow.Clip,
                    textAlign = TextAlign.Center,
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

    if (showNotificationDialog) {
        NotificationTimeDialog(
            onDismiss = { showNotificationDialog = false },
            onTimeSelected = { minutes ->
                notificationViewModel.scheduleNotification(minutes)
                showNotificationDialog = false
            }
        )
    }
}

@Composable
private fun NotificationTimeDialog(
    onDismiss: () -> Unit,
    onTimeSelected: (Int) -> Unit
) {
    // In minutes
    val timeOptions = listOf(
        1,
        5,
        10,
        30,
        60,
        60 * 2, // 2 hours
        60 * 6, // 6 hours
        60 * 12, // 12 hours
        60 * 24 // 24 hours
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.schedule_notification)) },
        text = {
            Column {
                Text(
                    text = stringResource(R.string.schedule_notification_description),
                    modifier = Modifier.padding(bottom = notificationTextBottomPadding)
                )
                timeOptions.forEach { minutes ->
                    Button(
                        onClick = { onTimeSelected(minutes) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.White,
                            contentColor = Color.Black
                        )
                    ) {
                        Text(formatTimeOption(minutes))
                    }
                    Spacer(modifier = Modifier.height(teamModalSpacing))
                }
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}

private fun formatTimeOption(minutes: Int): String {
    return when {
        minutes < 60 -> "$minutes minute${if (minutes > 1) "s" else ""}"
        minutes == 60 -> "1 hour"
        minutes < 60 * 24 -> "${minutes / 60} hours"
        else -> "24 hours"
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
        AsyncImage(
            model = selectedTrainer.drawable,
            contentDescription = null,
            modifier = Modifier.size(gifImageSize)
        )
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