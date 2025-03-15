package org.austral.pocketpedia.ui.shared.navigation


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

// TODO: add navigation functionality
@Composable
fun BottomNavbar() {
    val buttonPaddingValue = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    val buttonModifier = Modifier.padding(buttonPaddingValue)
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier
            .fillMaxSize()
            .safeContentPadding()
    ) {
        IconButton(
            onClick = {}, modifier = buttonModifier
        ) { Icon(Icons.Filled.Home, contentDescription = "") }

        IconButton(
            onClick = {}, modifier = buttonModifier
        ) { Icon(Icons.Filled.Search, contentDescription = "") }

        IconButton(
            onClick = {}, modifier = buttonModifier
        ) { Icon(Icons.Filled.Settings, contentDescription = "") }
    }
}


sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String)