package com.example.jetnotes.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(// represents the table in the database
    // name of the table in the database
    tableName = "tb_note",
    indices = [
        Index("title", unique = true)// Bank rule not to register equal securities
    ]
)
data class NoteModel(
    @PrimaryKey(autoGenerate = true)// primary key
    @ColumnInfo(name = "id")// Annotation specifies the name for the table in the SQLite database, If you want to change it
    val id: Int = 0,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "description")
    val description: String
)