package org.austral.pocketpedia.security.auth.biometric

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import org.austral.pocketpedia.R
import javax.inject.Inject

class BiometricAuthManager @Inject constructor() {

    fun authenticate(
        activity: androidx.fragment.app.FragmentActivity,
        onError: () -> Unit,
        onSuccess: () -> Unit,
        onFail: () -> Unit
    ) {
        val executor = ContextCompat.getMainExecutor(activity)
        val biometricPrompt = BiometricPrompt(
            activity,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    // handle authentication error here
                    onError()
                }

                @RequiresApi(Build.VERSION_CODES.R)
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    // handle authentication success here
                    onSuccess()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    // handle authentication failure here
                    onFail()
                }
            }
        )

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
            .setTitle(activity.getString(R.string.biometric_authentication))
            .setSubtitle(activity.getString(R.string.log_in_with_biometric))
            .build()

        biometricPrompt.authenticate(promptInfo)
    }
}