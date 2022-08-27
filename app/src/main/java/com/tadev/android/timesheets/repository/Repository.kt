package com.tadev.android.timesheets.repository

import com.tadev.android.timesheets.data.api.ApiService
import com.tadev.android.timesheets.data.db.TodosDao
import com.tadev.android.timesheets.data.db.TodosModel
import com.tadev.android.timesheets.data.model.TodosResponse
import javax.inject.Inject

class Repository @Inject constructor(
    private val api: ApiService,
    private val dao: TodosDao
) {
    suspend fun getTodos() = api.listTodos()

    suspend fun postPosts() = api.postPosts()

    suspend fun getComments() = api.commentsByPostId(2)

    suspend fun insertTodos(list: List<TodosResponse>) {
        val list2: List<TodosModel> = list.map {
            TodosModel(
                id = it.id,
                userId = it.userId,
                title = it.title,
                completed = it.completed
            )
        }
        dao.addTodos(list2)
    }

    suspend fun getJobs() = api.getJobs()
}