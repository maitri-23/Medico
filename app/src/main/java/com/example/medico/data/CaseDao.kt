package com.example.medico.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CaseDao {

    @Query("Select * from `case` order by id Desc")
    fun getAllCases(): Flow<List<Case>>

    @Query("SELECT * from `case` WHERE id = :id")
    fun getCase(id: Int): Flow<Case>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(case: Case)

    @Delete
    suspend fun delete(case: Case)

    @Update
    suspend fun update(case: Case)
}