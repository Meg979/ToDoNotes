package com.example.todonotes.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todonotes.database.Notes
import com.example.todonotes.database.NotesDao
import kotlinx.coroutines.launch

class NotesViewModel(private val databaseDao: NotesDao) : ViewModel() {


    fun insert(note: Notes) {
        viewModelScope.launch {
            addNote(note)
        }
    }

    fun delete(note: Notes) {
        viewModelScope.launch {
            deleteNote(note)
        }
    }


   /* fun getAll() {
        viewModelScope.launch {
            getAllNotes()
        }
    }*/

    fun deleteAll(){
        viewModelScope.launch {
            deleteAllNotes()
        }
    }

    suspend fun addNote(note: Notes) {
        databaseDao.addNote(note)
    }

    suspend fun deleteNote(note: Notes) {
        databaseDao.deleteNote(note)
    }

    fun getAllNotes():LiveData<List<Notes>> {
     return databaseDao.getAllNotes()
    }

    suspend fun deleteAllNotes() {
        databaseDao.deleteAllNotes()
    }
}