package com.example.jetnotes.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jetnotes.data.model.NoteModel

// The @Database annotation requires arguments so that Room can create the database
// After list the database entities and the version number
@Database(
    // list App entities
    entities = [
        NoteModel::class
    ],
    // database version
    version = 1,
    // export DB declare with false to not keep DB
    exportSchema = false
)
abstract class NoteDatabase: RoomDatabase() {
    // function to return NoteDao
    abstract fun noteDao(): NoteDao
}