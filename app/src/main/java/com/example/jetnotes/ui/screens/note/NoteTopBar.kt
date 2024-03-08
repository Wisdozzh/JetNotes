package com.example.jetnotes.ui.screens.note

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.jetnotes.R
import com.example.jetnotes.viewmodels.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNoteTopBar(navController: NavController, noteViewModel: NoteViewModel) {
    val context = LocalContext.current
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        title = { Text(text = stringResource(id = R.string.create_app_bar)) },
        navigationIcon = {
            IconButton(onClick = {
                navController.navigate("home_screen") {
                    popUpTo("home_screen") {
                        inclusive = true
                    }
                }
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.icon_arrow_back)
                )
            }
        },
        actions = {
            IconButton(onClick = {
                if (noteViewModel.validateFields()) {
                    val insertResult = noteViewModel.dbHandle("INSERT")
                    if (insertResult == "INSERT") {
                        Toast.makeText(context, "Recorded successfully!!", Toast.LENGTH_LONG).show()
                        navController.navigate("home_screen")
                    } else {
                        Toast.makeText(context, "Recording error!!", Toast.LENGTH_LONG).show()
                    }
                    Log.d("RESULTINSERT", insertResult)
                } else {
                    Toast.makeText(context, "Fill in the fields!!", Toast.LENGTH_LONG).show()
                }
            }) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = stringResource(id = R.string.icon_check)
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateNoteTopBar(navController: NavController, noteViewModel: NoteViewModel) {
    val context = LocalContext.current
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        title = { Text(text = stringResource(id = R.string.update_app_bar)) },
        navigationIcon = {
            IconButton(onClick = {
                navController.navigate("home_screen") {
                    popUpTo("home_screen") {
                        inclusive = true
                    }
                }
            }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(id = R.string.icon_arrow_back)
                )
            }
        },
        actions = {
            IconButton(onClick = {
                if (noteViewModel.validateFields()) {
                    val updateResult = noteViewModel.dbHandle("UPDATE")
                    if (updateResult == "UPDATE") {
                        Toast.makeText(context, "Update successfully!!", Toast.LENGTH_LONG).show()
                        navController.navigate("home_screen")
                    } else {
                        Toast.makeText(context, "Recording error!!", Toast.LENGTH_LONG).show()
                    }
                    Log.d("RESULT_UPDATE", updateResult)
                } else {
                    Toast.makeText(context, "Filled in the fields!!", Toast.LENGTH_LONG).show()
                }
            }) {
                Icon(
                    imageVector = Icons.Default.EditNote,
                    contentDescription = stringResource(id = R.string.icon_check)
                )
            }
        }
    )
}