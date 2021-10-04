package com.example.todonotes.notes

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todonotes.database.NotesDao
import java.lang.IllegalArgumentException

class NotesViewModelFactory(val notesDao: NotesDao):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
      if(modelClass.isAssignableFrom(NotesViewModel::class.java)) {
         return NotesViewModel(notesDao) as T
      }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}