package com.example.todonotes.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Notes_table")
data class Notes(
    @ColumnInfo(name ="note")
    var note: String,

    @ColumnInfo(name ="checkbox")
    var isChecked: Boolean
){
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}
