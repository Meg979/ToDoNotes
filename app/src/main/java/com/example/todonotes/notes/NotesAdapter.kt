package com.example.todonotes.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todonotes.R
import com.example.todonotes.database.Notes
import com.example.todonotes.databinding.NoteViewholderBinding


class NotesAdapter(val onNoteClickListener: OnNoteClickListener) :
    ListAdapter<Notes, NotesAdapter.NotesViewHolder>(NotesDiffUtil()) {

    private val _idCbChecked = MutableLiveData<Boolean>()
    val idCbChecked: LiveData<Boolean>
        get() = _idCbChecked

    inner class NotesViewHolder(
        private val binding: NoteViewholderBinding,
        val onNoteClickListener: OnNoteClickListener
    ) :
        RecyclerView.ViewHolder(binding.root), View.OnLongClickListener {


        fun bindViewHolder(noteItem: Notes) {
            binding.noteTextView.text = noteItem.note
            _idCbChecked.value = noteItem.isChecked
      //      binding.checkBox.isChecked = noteItem.isChecked
            binding.executePendingBindings()
            binding.root.setOnLongClickListener(this)

        }

        override fun onLongClick(v: View?): Boolean {
            onNoteClickListener.onClick(adapterPosition)
            return true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding = DataBindingUtil.inflate<NoteViewholderBinding>(
            LayoutInflater.from(parent.context),
            R.layout.note_viewholder, parent, false
        )
        return NotesViewHolder(binding, onNoteClickListener)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val noteItem = getItem(position)
        holder.bindViewHolder(noteItem)
    }


    fun getNoteAt(position: Int): Notes {
        return getItem(position)
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

