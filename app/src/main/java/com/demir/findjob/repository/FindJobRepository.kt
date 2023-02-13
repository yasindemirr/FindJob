package com.demir.findjob.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.demir.findjob.api.RetrofitInstance
import com.demir.findjob.db.FindJobDataBase
import com.demir.findjob.model.FindJob
import com.demir.findjob.model.JobToSave
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FindJobRepository(app:Application) {
    private val db=FindJobDataBase(app)
    private val findJobService = RetrofitInstance.api
    private val findJobResponseLiveData: MutableLiveData<FindJob> = MutableLiveData()
    private val searchFindJobLiveData: MutableLiveData<FindJob> = MutableLiveData()


    init {

        getFindJobResponse()
    }

    private fun getFindJobResponse() {

        findJobService.getRemoteJob().enqueue(
            object : Callback<FindJob> {
                override fun onResponse(call: Call<FindJob>, response: Response<FindJob>) {
                    if (response.body() != null) {
                        findJobResponseLiveData.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<FindJob>, t: Throwable) {
                    //findJobResponseLiveData.postValue()
                    Log.d("error ibm", t.message.toString())
                }
            })
    }


    fun searchRemoteJob(query: String?) {
        findJobService.searchRemoteJob(query).enqueue(
            object : Callback<FindJob> {
                override fun onResponse(call: Call<FindJob>, response: Response<FindJob>) {
                    if (response.body() != null) {
                        searchFindJobLiveData.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<FindJob>, t: Throwable) {
                  //  searchFindJobLiveData.postValue(null)
                    Log.d("error ibm", t.message.toString())
                }

            }
        )

    }


    fun getFindJobResponseLiveData(): LiveData<FindJob> {
        return findJobResponseLiveData
    }

    fun getSearchJobResponseLiveData(): LiveData<FindJob> {
        return searchFindJobLiveData
    }


    suspend fun insertJob(job: JobToSave) = db.getRemoteJobDao().insertJob(job)
    suspend fun deleteJob(job: JobToSave) = db.getRemoteJobDao().deleteJob(job)
    fun getAllJobs() = db.getRemoteJobDao().getAllJob()

}
