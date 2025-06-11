package org.austral.pocketpedia.infrastructure.storage.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.austral.pocketpedia.domain.entities.PokemonEntity
import org.austral.pocketpedia.domain.entities.PokemonInTeam
import org.austral.pocketpedia.domain.entities.TeamEntity
import org.austral.pocketpedia.infrastructure.storage.dao.TeamDao


@Database(
    entities = [
        TeamEntity::class,
        PokemonEntity::class,
        PokemonInTeam::class
    ],
    version = 1,
    exportSchema = false
)
abstract class PocketPediaDatabase : RoomDatabase() {
    abstract fun teamDao(): TeamDao

    companion object {
        @Volatile
        private var INSTANCE: PocketPediaDatabase? = null

        fun getDatabase(context: Context): PocketPediaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PocketPediaDatabase::class.java,
                    "learning_android_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}