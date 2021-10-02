package com.example.todonotes.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(note:Notes)


    @Delete
    suspend fun deleteNote(note: Notes)

    @Query("SELECT * FROM NOTES_TABLE ORDER BY id DESC")
   fun getAllNotes(): LiveData<List<Notes>>

    @Query("DELETE FROM NOTES_TABLE")
    suspend fun deleteAllNotes()
}