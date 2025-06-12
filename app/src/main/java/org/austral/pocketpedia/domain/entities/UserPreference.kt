package org.austral.pocketpedia.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_preferences")
data class UserPreference(
    @PrimaryKey val key: String,
    val value: String
) 