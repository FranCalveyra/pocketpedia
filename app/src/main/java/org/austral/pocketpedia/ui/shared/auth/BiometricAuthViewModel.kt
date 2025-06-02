package org.austral.pocketpedia.ui.shared.auth

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.austral.pocketpedia.R
import org.austral.pocketpedia.security.auth.biometric.BiometricAuthManager
import javax.inject.Inject

@HiltViewModel
class BiometricAuthViewModel @Inject constructor(
    private val biometricAuthManager: BiometricAuthManager,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated = _isAuthenticated.asStateFlow()

    fun authenticate(activity: FragmentActivity) {
        biometricAuthManager.authenticate(
            activity,
            onError = {
                _isAuthenticated.value = false
                Toast.makeText(
                    context,
                    context.getString(R.string.authentication_error),
                    Toast.LENGTH_SHORT
                ).show()
            },
            onSuccess = {
                _isAuthenticated.value = true
            },
            onFail = {
                _isAuthenticated.value = false
                Toast.makeText(
                    context,
                    context.getString(R.string.authentication_failed),
                    Toast.LENGTH_SHORT
                ).show()
            }
        )
    }
}