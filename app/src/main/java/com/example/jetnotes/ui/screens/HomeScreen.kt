package com.example.jetnotes.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import com.example.jetnotes.R
import com.example.jetnotes.ui.screens.home.HomeContent
import com.example.jetnotes.viewmodels.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navHostController: NavHostController, noteViewModel: NoteViewModel) {
    LaunchedEffect(key1 = Unit) {
        noteViewModel.getNotes()
    }

    val getAllNotes by noteViewModel.getNotes.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .fillMaxWidth(),
                title = {
                    Text(stringResource(id = R.string.home_app_bar), fontWeight = FontWeight.Bold)
                })
        },
        content = {it
            HomeContent(
                modifier = Modifier.padding(it),
                getAllNotes = getAllNotes,
                navController = navHostController,
                onDelete = { event, note ->
                    noteViewModel.dbHandle(event)
                    noteViewModel.updateNotesFields(note)
                })
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navHostController.navigate("note_screen") {
                        popUpTo("note_screen") {
                            inclusive = true
                        }
                    }
                }
            ) {
                Icon(Icons.Default.Add, "Floating action button.")
            }
        }
    )
}