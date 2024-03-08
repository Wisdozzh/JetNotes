package com.example.jetnotes.repository

import com.example.jetnotes.data.model.NoteModel
import com.example.jetnotes.data.room.NoteDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepo @Inject constructor(
    private val noteDao: NoteDao
) {
    fun getAllNotes(): Flow<List<NoteModel>> {
        return noteDao.getAllNotes()
    }
    fun selectNoteID(noteID: Int): Flow<NoteModel?> {
        return noteDao.selectNoteID(noteID)
    }
    suspend fun insertNote(note: NoteModel) {
        return noteDao.insertNote(note)
    }
    suspend fun updateNote(note: NoteModel) {
        return noteDao.updateNote(note)
    }
    suspend fun deleteNote(note: NoteModel) {
        return noteDao.deleteNote(note)
    }
}