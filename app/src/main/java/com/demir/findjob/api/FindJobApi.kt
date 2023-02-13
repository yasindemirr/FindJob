package com.demir.findjob.api

import com.demir.findjob.model.FindJob
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FindJobApi {

    @GET("remote-jobs")
    fun getRemoteJob(): Call<FindJob>

    @GET("remote-jobs")
    fun searchRemoteJob(@Query("search") query: String?): Call<FindJob>

}