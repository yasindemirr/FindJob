package com.demir.findjob.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.demir.findjob.model.JobToSave

@Dao
interface FindJobDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJob(job: JobToSave): Long

    @Query("SELECT * FROM job ORDER BY id DESC")
    fun getAllJob(): LiveData<List<JobToSave>>

    @Delete
    suspend fun deleteJob(job: JobToSave)
}