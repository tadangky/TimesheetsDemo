package com.tadev.android.timesheets.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tadev.android.timesheets.data.model.Job
import com.tadev.android.timesheets.data.model.TodosResponse
import com.tadev.android.timesheets.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DemoViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {
    val jobList = MutableLiveData<List<Job>>()
    val loading = MutableLiveData<Boolean>()

    fun getJobs() {
        viewModelScope.launch {
            loading.postValue(true)
            withContext(Dispatchers.IO) {
                try {
                    val result = repository.getJobs()
                    jobList.postValue(result)
                } catch (e: Exception) {
                    loading.postValue(false)
                    e.printStackTrace()
                }
            }
        }
    }
}