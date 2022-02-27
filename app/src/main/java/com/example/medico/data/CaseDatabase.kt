package com.example.medico.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Case::class], version = 1, exportSchema = false)
abstract class CaseDatabase: RoomDatabase() {

    abstract fun getCaseDao(): CaseDao

    companion object{

        @Volatile
        private var INSTANCE: CaseDatabase?=null

        fun getDatabase(context: Context): CaseDatabase{
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CaseDatabase::class.java,
                    "case_database"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}