package org.austral.pocketpedia.domain.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.austral.pocketpedia.R
import org.austral.pocketpedia.domain.entities.UserPreference
import org.austral.pocketpedia.infrastructure.storage.dao.UserPreferenceDao
import org.austral.pocketpedia.infrastructure.storage.local.PocketPediaDatabase
import javax.inject.Inject
import javax.inject.Singleton

data class Trainer(val name: String, val drawable: Int)

const val SELECTED_TRAINER_KEY = "selected_trainer"

@Singleton
class UserPreferencesRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private val userPreferenceDao: UserPreferenceDao =
        PocketPediaDatabase.getDatabase(context).userPreferenceDao()

    val trainers = listOf(
        Trainer("Red", R.drawable.red),
        Trainer("Ethan", R.drawable.ethan),
        Trainer("Brendan", R.drawable.brendan),
        Trainer("Lucas", R.drawable.lucas),
        Trainer("Hilbert", R.drawable.hilbert_sprite),
        Trainer("Nate", R.drawable.nate),
        Trainer("N", R.drawable.n_sprite),
    )

    val selectedTrainer: Flow<Trainer> =
        userPreferenceDao.get(SELECTED_TRAINER_KEY).map { userPreference ->
            val trainerName = userPreference?.value ?: trainers.first().name
            trainers.find { it.name == trainerName } ?: trainers.first()
        }

    suspend fun saveSelectedTrainer(trainer: Trainer) {
        userPreferenceDao.insert(UserPreference(SELECTED_TRAINER_KEY, trainer.name))
    }
} 