package com.demir.findjob.model

import com.google.gson.annotations.SerializedName

data class FindJob(
    @SerializedName("job-count")
    val jobCount: Int?,
    val jobs: List<Job>?,
    val legalNotice: String?
)