package org.austral.pocketpedia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import org.austral.pocketpedia.ui.shared.navigation.BottomNavbar
import org.austral.pocketpedia.ui.shared.navigation.NavHostComposable
import org.austral.pocketpedia.ui.theme.PocketPediaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            PocketPediaTheme {
                Scaffold(
                    bottomBar =  {
                        BottomNavbar(navController::navigate)
                    },
                    modifier = Modifier
                    .fillMaxSize()
                    .safeContentPadding()) { innerPadding ->
                    NavHostComposable(innerPadding, navController)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PocketPediaTheme {
        Greeting("Android")
    }
}