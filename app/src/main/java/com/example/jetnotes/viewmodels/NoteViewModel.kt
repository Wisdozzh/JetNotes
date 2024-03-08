package com.example.jetnotes.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetnotes.data.model.NoteModel
import com.example.jetnotes.repository.NoteRepo
import com.example.jetnotes.uitl.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteRepo: NoteRepo
): ViewModel(){
    val id: MutableState<Int> = mutableIntStateOf(0)
    val title: MutableState<String> = mutableStateOf("")
    val description: MutableState<String> = mutableStateOf("")

    private val _getNotes = MutableStateFlow<ResultState<List<NoteModel>>>(ResultState.Idle)
    val getNotes: StateFlow<ResultState<List<NoteModel>>> = _getNotes

    private val _selectedNote: MutableStateFlow<NoteModel?> = MutableStateFlow(null)
    val selectedNote: StateFlow<NoteModel?> = _selectedNote

    fun getNotes() {
        viewModelScope.launch {
            val result = try {
                noteRepo.getAllNotes().collect {
                    _getNotes.value = ResultState.Success(it)
                }
            } catch (error: Exception) {
                ResultState.Error(error)
            }
            Log.d("RESULT_STATE", "$result")
        }
    }

    private fun insertNote() {
        viewModelScope.launch(Dispatchers.IO) {
            val note = NoteModel(
                title = title.value,
                description = description.value
            )
            noteRepo.insertNote(note)
        }
    }

    private fun updateNote() {
        viewModelScope.launch(Dispatchers.IO) {
            val note = NoteModel(
                id = id.value,
                title = title.value,
                description = description.value
            )
            noteRepo.updateNote(note)
        }
    }

    private fun deleteNote() {
        viewModelScope.launch(Dispatchers.IO) {
            val note = NoteModel(
                id = id.value,
                title = title.value,
                description = description.value
            )
            noteRepo.deleteNote(note)
        }
    }

    fun getSelectNoteID(noteID: Int) {
        viewModelScope.launch {
            noteRepo.selectNoteID(noteID).collect {
                _selectedNote.value = it
            }
        }
    }

    fun updateNotesFields(selectedNote: NoteModel?) {
        if (selectedNote != null) {
            id.value = selectedNote.id
            title.value = selectedNote.title
            description.value = selectedNote.description
        } else {
            // clear all fields
            id.value = 0
            title.value = ""
            description.value = ""
        }
    }

    fun validateFields(): Boolean {
        return title.value.isNotEmpty() && description.value.isNotEmpty()
    }

    fun dbHandle(action: String): String {
        var result = action
        when(result) {
            "INSERT" -> insertNote()
            "UPDATE" -> updateNote()
            "DELETE" -> deleteNote()
            else -> { result = "NO_EVENT" }
        }
        return result
    }
}