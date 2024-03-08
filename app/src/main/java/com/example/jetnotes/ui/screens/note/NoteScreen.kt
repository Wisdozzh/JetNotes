package com.example.jetnotes.ui.screens.note

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.jetnotes.data.model.NoteModel
import com.example.jetnotes.viewmodels.NoteViewModel

@Composable
fun NoteScreen(
    navController: NavController,
    noteViewModel: NoteViewModel,
    selected: NoteModel?
) {
    var title by noteViewModel.title
    var description by noteViewModel.description

    Scaffold(
        topBar = {
            if (selected == null) {
                CreateNoteTopBar(navController = navController, noteViewModel = noteViewModel)
            } else {
                UpdateNoteTopBar(navController = navController, noteViewModel = noteViewModel)
            }
        },
    ) { it ->
        NoteContent(
            modifier = Modifier.padding(it),
            title = title,
            onTitle = { title = it},
            description = description,
            onDescription = { description = it }
        )
    }
}