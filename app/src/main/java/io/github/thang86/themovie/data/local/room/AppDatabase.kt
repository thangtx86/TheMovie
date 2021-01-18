package io.github.thang86.themovie.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.github.thang86.themovie.data.local.entity.DataRoom

/**
 *
 * Created by Thang86
 */
@Database(entities = [DataRoom::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun queries(): QueriesDao

    companion object {

        @Volatile private var INSTANCE: AppDatabase? = null
        fun getAppDatabase(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java, "user-database")
                .build()
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}
