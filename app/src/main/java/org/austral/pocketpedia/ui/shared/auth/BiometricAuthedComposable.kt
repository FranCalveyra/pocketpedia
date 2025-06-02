package org.austral.pocketpedia.ui.shared.auth

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import org.austral.pocketpedia.R

@Composable
fun BiometricAuthedComposable(
    onAuthenticate: (androidx.fragment.app.FragmentActivity) -> Unit,
    isAuthenticated: Boolean,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val activity = context as? androidx.fragment.app.FragmentActivity
    val biometricManager = remember { BiometricManager.from(context) }
    val status =
        remember { biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL) }

    LaunchedEffect(activity) {
        activity?.let { onAuthenticate(it) }
    }

    when (status) {
        BiometricManager.BIOMETRIC_SUCCESS -> {
            if (isAuthenticated) {
                content()
            } else {
                Text(text = stringResource(R.string.auth_needed))
            }
        }

        BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
            Text(text = stringResource(R.string.phone_not_prepared))
        }

        BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
            Text(text = stringResource(R.string.unavailable_biometric))
        }

        BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED -> {
            Text(text = stringResource(R.string.biometric_security_update_needed))
        }

        BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED,
        BiometricManager.BIOMETRIC_STATUS_UNKNOWN,
        BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
            Text(text = stringResource(R.string.cant_use_biometric))
        }
    }
}