package org.austral.pocketpedia.ui.screens.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import org.austral.pocketpedia.R
import org.austral.pocketpedia.ui.theme.profileScreenPadding

@Composable
fun ProfileScreen(navController: NavHostController) {
    val viewModel = hiltViewModel<ProfileViewModel>()
    val user by viewModel.userData.collectAsStateWithLifecycle()

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(profileScreenPadding)
    ) {
        if (user == null) {
            GoogleLoginButton(
                modifier = Modifier,
                onClick = viewModel::launchCredentialManager
            )
        } else {
            ProfileBody(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
private fun GoogleLoginButton(
    onClick: () -> Unit,
    modifier: Modifier
) {
    GoogleButtonUI(
        modifier = modifier,
        onClick = onClick,
    )
}

@Composable
private fun GoogleButtonUI(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White,
            contentColor = Color.Black,

        ),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Image(
            modifier = Modifier.size(24.dp),
            painter = painterResource(R.drawable.missigno),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text("Continue with Google")
    }
}

@Composable
fun ProfileBody(modifier: Modifier) {
    Text(
        stringResource(R.string.work_in_progress),
        style = typography.bodyMedium.copy(textAlign = TextAlign.Center),
        modifier = modifier
    )
}