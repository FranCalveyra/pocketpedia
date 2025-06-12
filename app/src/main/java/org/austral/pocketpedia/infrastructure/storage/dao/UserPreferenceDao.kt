package org.austral.pocketpedia.infrastructure.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.austral.pocketpedia.domain.entities.UserPreference

@Dao
interface UserPreferenceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userPreference: UserPreference)

    @Query("SELECT * FROM user_preferences WHERE `key` = :key")
    fun get(key: String): Flow<UserPreference?>
} 