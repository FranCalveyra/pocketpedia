package org.austral.pocketpedia.ui.screens.profile

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import org.austral.pocketpedia.R
import org.austral.pocketpedia.ui.theme.Typography
import org.austral.pocketpedia.ui.theme.profileScreenPadding

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun ProfileScreen(navController: NavHostController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(profileScreenPadding)
    ) {
        Text(
            stringResource(R.string.work_in_progress),
            style = Typography.bodyMedium.copy(textAlign = TextAlign.Center)
        )
    }
}