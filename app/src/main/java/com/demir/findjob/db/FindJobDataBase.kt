package com.demir.findjob.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.demir.findjob.model.JobToSave

@Database(entities = [JobToSave::class], version = 1)
abstract class FindJobDataBase : RoomDatabase() {

    abstract fun getRemoteJobDao(): FindJobDao

    companion object {
        @Volatile
        private var instance: FindJobDataBase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                FindJobDataBase::class.java,
                "remoteJob_db2"
            ).build()
    }
}