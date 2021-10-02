package com.example.todonotes.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todonotes.R
import com.example.todonotes.database.Notes
import com.example.todonotes.database.NotesDatabase
import com.example.todonotes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnNoteClickListener,OnCheckBoxClickListener {
    private lateinit var viewModel: NotesViewModel
    private lateinit var adapter: NotesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        Log.d(TAG, "Database created")
        val databaseDao = NotesDatabase.getInstance(this).notesDatabaseDao
        val viewModelFactory = NotesViewModelFactory(databaseDao)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(NotesViewModel(databaseDao)::class.java)
        Log.d(TAG, "layout manager")
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        Log.d(TAG, "adapter called")
        adapter = NotesAdapter(this,this)
        binding.recyclerView.adapter = adapter
        binding.fab.setOnClickListener {
            Log.d(TAG, "fab called ")
            AddNoteDialog(this, object : OnDialogClickListener {
                override fun onClick(note: Notes) {
                    viewModel.insert(note)
                }
            }).show()
        }



        viewModel.getAllNotes().observe(this, Observer {
            Log.d(TAG, "getAllNotes list submitted ")
            adapter.submitList(it)
        })

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.delete(adapter.currentList[viewHolder.adapterPosition])
                Toast.makeText(this@MainActivity, "Note deleted", Toast.LENGTH_SHORT).show()
            }

        }).attachToRecyclerView(binding.recyclerView)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        MenuInflater(this).inflate(R.menu.menu_items, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.deleteAll -> {
                viewModel.deleteAll()
                Toast.makeText(this, "Deleted all notes", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)

        }
    }

    override fun onClick(position: Int) {
        var updateNote = adapter.currentList[position]
        EditNoteDialog(this, object : OnDialogClickListener {
            override fun onClick(note: Notes) {
                note.id = updateNote.id
                viewModel.insert(note)
            }
        }).show()
    }

    override fun onClick(position: Int, status: Boolean) {
        var updateCbNote = adapter.currentList[position]
        updateCbNote.isChecked = status
        viewModel.insert(updateCbNote)
        Toast.makeText(this,"Note checked",Toast.LENGTH_SHORT).show()
    }
}