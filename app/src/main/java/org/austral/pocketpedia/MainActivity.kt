package org.austral.pocketpedia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import org.austral.pocketpedia.ui.shared.navigation.BottomNavbar
import org.austral.pocketpedia.ui.shared.navigation.NavHostComposable
import org.austral.pocketpedia.ui.theme.BackgroundGradient
import org.austral.pocketpedia.ui.theme.PocketPediaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            PocketPediaTheme {
                Box(
                    modifier = Modifier
                        .background(BackgroundGradient)
                        .fillMaxSize()
                ) {
                    Scaffold(
                        bottomBar = { BottomNavbar(navController::navigate) },
                    ) { innerPadding -> NavHostComposable(innerPadding, navController) }
                }
            }
        }
    }
}