package com.example.todonotes.notes

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatDialog
import androidx.databinding.DataBindingUtil
import com.example.todonotes.R
import com.example.todonotes.database.Notes
import com.example.todonotes.databinding.AddNoteDialogBinding

class AddNoteDialog(context: Context, private val onDialogClickListener: OnDialogClickListener): AppCompatDialog(context) {
    private lateinit var binding : AddNoteDialogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Notes", "onCreate: AddNoteDialog")
        binding =DataBindingUtil.inflate(LayoutInflater.from(context)
            ,R.layout.add_note_dialog,null,false)
        setContentView(binding.root)
        Log.d("Notes", "addNoteDialog view binded")

        binding.tvAdd.setOnClickListener {
            val newNote = binding.etAddNote.text.toString()
            val note = Notes(newNote,false)
            onDialogClickListener.onClick(note)
            dismiss()
        }

        binding.tvCancelAdd.setOnClickListener {
            cancel()
        }
    }


}