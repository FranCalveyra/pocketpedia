package org.austral.pocketpedia.ui.screens.profile

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.austral.pocketpedia.domain.repository.Trainer
import org.austral.pocketpedia.domain.repository.UserPreferencesRepository
import org.austral.pocketpedia.security.auth.firebase.FirebaseAuthManager
import javax.inject.Inject


const val TAG = "ProfileViewModel"

@HiltViewModel
class ProfileViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    // Firebase
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val credentialManager = CredentialManager.create(context)
    private val firebaseAuthManager = FirebaseAuthManager(
        auth,
        context,
        viewModelScope,
        credentialManager,
        ::onSuccess,
        ::onFailure,
        onSignOut = ::onFailure
    )

    private val _userData = MutableStateFlow(auth.currentUser)
    val userData = _userData.asStateFlow()

    // Room
    val selectedTrainer = userPreferencesRepository.selectedTrainer.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = userPreferencesRepository.trainers.first()
    )

    val trainers = userPreferencesRepository.trainers

    fun login() = firebaseAuthManager.launchCredentialManager()
    fun logout() = firebaseAuthManager.signOut()

    fun selectTrainer(trainer: Trainer) {
        viewModelScope.launch {
            userPreferencesRepository.saveSelectedTrainer(trainer)
        }
    }


    private fun onSuccess() {
        val user = auth.currentUser
        viewModelScope.launch {
            _userData.emit(user)
        }
    }

    private fun onFailure() = viewModelScope.launch { _userData.emit(null) }

}