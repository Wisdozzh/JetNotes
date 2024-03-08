package com.example.jetnotes.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.jetnotes.data.model.NoteModel
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    // List Notes
    @Query("SELECT * FROM tb_note ORDER BY id ASC")
    fun getAllNotes(): Flow<List<NoteModel>>
    // Select Annotation
    @Query("SELECT * FROM tb_note WHERE id=:noteID")
    fun selectNoteID(noteID: Int): Flow<NoteModel?>
    // Insert Notes
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(noteModel: NoteModel)
    // Update Annotation
    @Update
    suspend fun updateNote(noteModel: NoteModel)
    // Delete Annotation
    @Delete
    suspend fun deleteNote(noteModel: NoteModel)
}