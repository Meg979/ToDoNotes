package com.example.todonotes.notes

import android.app.Application
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todonotes.R
import com.example.todonotes.database.Notes
import com.example.todonotes.databinding.NoteViewholderBinding

const val TAG = "Notes"
class NotesAdapter(private val onNoteClickListener: OnNoteClickListener,
                   private val onCheckBoxClickListener: OnCheckBoxClickListener) :
    ListAdapter<Notes, NotesAdapter.NotesViewHolder>(NotesDiffUtil()) {


    inner class NotesViewHolder(
        private val binding: NoteViewholderBinding,
        private val onNoteClickListener: OnNoteClickListener,
        private val onCheckBoxClickListener: OnCheckBoxClickListener
    ) :
        RecyclerView.ViewHolder(binding.root), View.OnLongClickListener {
        init {
            binding.root.setOnLongClickListener(this)
        }

        fun bindViewHolder(noteItem: Notes) {
            binding.noteTextView.text = noteItem.note
            binding.checkBox.isChecked = noteItem.isChecked
            binding.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                    onCheckBoxClickListener.onClick(adapterPosition,isChecked)
            }
            binding.executePendingBindings()

        }

        override fun onLongClick(v: View?): Boolean {
            onNoteClickListener.onClick(adapterPosition)
            return true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        Log.d(TAG, "onCreateViewHolder: ViewHolder created")
        val binding = DataBindingUtil.inflate<NoteViewholderBinding>(
            LayoutInflater.from(parent.context),
            R.layout.note_viewholder, parent, false
        )
        return NotesViewHolder(binding, onNoteClickListener,onCheckBoxClickListener)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: notes assigned to the viewHolder")
        val noteItem = getItem(position)
        holder.bindViewHolder(noteItem)
    }




}

class NotesDiffUtil : DiffUtil.ItemCallback<Notes>() {
    override fun areItemsTheSame(oldItem: Notes, newItem: Notes): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Notes, newItem: Notes): Boolean {
        return oldItem == newItem
    }

}

interface OnNoteClickListener {
    fun onClick(position: Int)
}

interface OnCheckBoxClickListener{
    fun onClick(position: Int,status:Boolean)
}

