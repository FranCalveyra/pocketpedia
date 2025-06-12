package org.austral.pocketpedia

import android.app.NotificationChannel
import android.app.NotificationManager
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
import org.austral.pocketpedia.infrastructure.notification.notificationChannelID
import org.austral.pocketpedia.ui.shared.navigation.BottomNavbar
import org.austral.pocketpedia.ui.shared.navigation.NavHostComposable
import org.austral.pocketpedia.ui.theme.BackgroundGradient
import org.austral.pocketpedia.ui.theme.PocketPediaTheme

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        createNotificationChannel()
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

    private fun createNotificationChannel() {
        val notificationChannel = NotificationChannel(
            notificationChannelID,
            getString(R.string.pocketpedia_notification),
            NotificationManager.IMPORTANCE_HIGH
        )
        val notificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(notificationChannel)
    }
}