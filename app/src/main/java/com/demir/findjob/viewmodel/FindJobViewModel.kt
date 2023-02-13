package com.demir.findjob.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.demir.findjob.model.JobToSave
import com.demir.findjob.repository.FindJobRepository
import kotlinx.coroutines.launch


class FindJobViewModel(
    app: Application
) : AndroidViewModel(app) {

       val findJobRepository= FindJobRepository(app)


    fun findJobResult() =
        findJobRepository.getFindJobResponseLiveData()

    fun searchJob(query: String?) =
        findJobRepository.searchRemoteJob(query)

    fun searchResult() = findJobRepository.getSearchJobResponseLiveData()

    fun insertJob(job: JobToSave) = viewModelScope.launch {
        findJobRepository.insertJob(job)
    }

    fun deleteJob(job: JobToSave) = viewModelScope.launch {
        findJobRepository.deleteJob(job)
    }

    fun getAllJob() = findJobRepository.getAllJobs()

}