package com.example.audiopodcasts.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface ResponseDao {

    @Query("SELECT * FROM responseEntity WHERE id = :id")
    suspend fun getResponseEntity(id: String): ResponseEntity?

    @Upsert
    suspend fun upsertResponse(entity: ResponseEntity)

    @Query("DELETE FROM responseEntity")
    suspend fun clearAll()

}