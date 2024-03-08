package com.example.jetnotes.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.jetnotes.data.model.NoteModel
import com.example.jetnotes.ui.screens.EmptyContent
import com.example.jetnotes.ui.theme.ALL_PADDING
import com.example.jetnotes.ui.theme.CARD_SHAPE
import com.example.jetnotes.ui.theme.ICON_SWIPE
import com.example.jetnotes.ui.theme.ICON_THRESHOLD
import com.example.jetnotes.ui.theme.Purple80
import com.example.jetnotes.ui.theme.PurpleGrey40
import com.example.jetnotes.ui.theme.TEXT_DEFAULT
import com.example.jetnotes.ui.theme.TITLE
import com.example.jetnotes.uitl.ResultState
import kotlinx.coroutines.launch
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun HomeContent(
    modifier: Modifier,
    getAllNotes: ResultState<List<NoteModel>>,
    navController: NavController,
    onDelete: (String, NoteModel) -> Unit
) {
    val listNotes: List<NoteModel>

    if (getAllNotes is ResultState.Success) {
        listNotes = getAllNotes.data
        if (listNotes.isEmpty()) {
            EmptyContent()
        } else {
            LazyColumn (
                modifier = modifier
            ){
                items(listNotes) {note ->
                    Card(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(all = ALL_PADDING),
                        shape = RoundedCornerShape(size = CARD_SHAPE)
                    ) {
                        SwipeNote(note = note, navController = navController) {
                            onDelete(
                                "DELETE",
                                note
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SwipeNote(
    note: NoteModel,
    navController: NavController,
    onSwipe: () -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    val deleteSwipe = SwipeAction(
        onSwipe = {
            scope.launch {
                onSwipe()
            }
        },
        icon = {
            Icon(
                modifier = Modifier
                    .padding(ALL_PADDING)
                    .size(ICON_SWIPE),
                imageVector = Icons.Default.DeleteForever,
                contentDescription = "",
                tint = Color.White
            )
        },
        background = Color.Red
    )
    val updateSwipe = SwipeAction(
        onSwipe = {
            navController.navigate("note_screen/${note.id}")
        },
        icon = {
            Icon(
                modifier = Modifier
                    .padding(ALL_PADDING)
                    .size(ICON_SWIPE),
                imageVector = Icons.Default.EditNote,
                contentDescription = "",
                tint = Color.White
            )
        },
        background = PurpleGrey40
    )
    SwipeableActionsBox(
        swipeThreshold = ICON_THRESHOLD,
        startActions = listOf(updateSwipe),
        endActions = listOf(deleteSwipe)
    ) {
        NoteItem(note)
    }
}

@Composable
fun NoteItem(note: NoteModel) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Purple80)
            .padding(all = ALL_PADDING)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = note.title, fontSize = TITLE, fontWeight = FontWeight.Bold)
            if (expanded) {
                Text(text = note.description, fontSize = TEXT_DEFAULT)
            }
        }
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                contentDescription = if (expanded) {
                    "Menos"
                }else { "Mais" }
            )
        }
    }
}