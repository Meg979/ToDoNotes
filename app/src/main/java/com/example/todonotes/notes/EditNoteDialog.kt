package com.example.todonotes.notes

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatDialog
import androidx.databinding.DataBindingUtil
import com.example.todonotes.R
import com.example.todonotes.database.Notes
import com.example.todonotes.databinding.EditNoteDialogBinding

class EditNoteDialog(context: Context, private val onDialogClickListener: OnDialogClickListener) :AppCompatDialog(context) {
    private lateinit var binding :EditNoteDialogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.edit_note_dialog,
        null,false)
        setContentView(binding.root)
        binding.tvUpdate.setOnClickListener {
            val editedNote = binding.etEditNote.text.toString()
            val note = Notes(editedNote,false)
            //onNoteClickListener
            onDialogClickListener.onClick(note)
            dismiss()
        }

        binding.tvCancelEdit.setOnClickListener {
            cancel()
        }
    }
}