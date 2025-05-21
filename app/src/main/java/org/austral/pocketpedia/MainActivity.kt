package org.austral.pocketpedia

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import dagger.hilt.android.AndroidEntryPoint
import org.austral.pocketpedia.ui.shared.navigation.BottomNavbar
import org.austral.pocketpedia.ui.shared.navigation.NavHostComposable
import org.austral.pocketpedia.ui.theme.BackgroundGradient
import org.austral.pocketpedia.ui.theme.PocketPediaTheme

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
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