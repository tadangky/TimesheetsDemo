package com.tadev.android.timesheets.ui

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tadev.android.timesheets.data.model.TodosResponse
import com.tadev.android.timesheets.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    private val sharedPreferences: SharedPreferences,
    private val editor: SharedPreferences.Editor
) : ViewModel() {
    val todoList = MutableLiveData<List<TodosResponse>>()

    fun getTodos() {
        Timber.e("MainViewModel getTodos")
        sharedPreferences.edit().putBoolean("test", false).apply()
        sharedPreferences.edit().putLong("testaa", System.currentTimeMillis()).apply()
        editor.putLong("tyeruhguer", System.currentTimeMillis()).apply()

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val result = repository.getTodos()
                    Timber.e("MainViewModel getTodos result: $result")
                    repository.insertTodos(result)
                    todoList.postValue(result)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun getJobs() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    repository.getJobs()
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }
    }
}