package com.example.jetnotes.data.room

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module// provide instance of certain type
@InstallIn(SingletonComponent::class)// inform in which Android class each module will be used or replaced
object NoteModule {
    @Provides
    @Singleton
    fun getNoteDb(
        @ApplicationContext
        context: Context
    ) = Room.databaseBuilder(
        context = context,
        NoteDatabase::class.java,
        "note.db"
    ).build()
    @Provides
    @Singleton
    fun getNoteDao(db: NoteDatabase) = db.noteDao()
}