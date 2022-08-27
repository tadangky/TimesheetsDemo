package com.tadev.android.timesheets.data.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TodosDao {
    @Query("select * from todosTable")
    fun getTodos(): LiveData<List<TodosModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTodo(todos: TodosModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTodos(todos: List<TodosModel>)
}

@Entity(tableName = "todosTable")
data class TodosModel(
    @PrimaryKey()
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "userId") val userId: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "completed") val completed: Boolean
)