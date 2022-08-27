package com.tadev.android.timesheets.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        TodosModel::class
    ], version = 1
)

abstract class AppDatabase : RoomDatabase() {
    abstract val todosDao: TodosDao
}