package com.example.jetnotes.ui.screens.note

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.example.jetnotes.R
import com.example.jetnotes.ui.theme.ALL_PADDING
import com.example.jetnotes.ui.theme.SPACER_HEIGHT
import com.example.jetnotes.ui.theme.TEXT_DEFAULT
import com.example.jetnotes.ui.theme.surface

@Composable
fun NoteContent(
    modifier: Modifier,
    title: String,
    onTitle: (String) -> Unit,
    description: String,
    onDescription: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(surface)
            .padding(ALL_PADDING)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = title,
            onValueChange = {onTitle(it)},
            label = { Text(text = stringResource(id = R.string.note_title), fontSize = TEXT_DEFAULT) },
            placeholder = { Text(text = stringResource(id = R.string.place_holder_note_title), fontSize = TEXT_DEFAULT)},
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )
        Spacer(modifier = Modifier.height(SPACER_HEIGHT))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = description,
            onValueChange = {onDescription(it)},
            label = { Text(text = stringResource(id = R.string.note_description), fontSize = TEXT_DEFAULT) },
            placeholder = { Text(text = stringResource(id = R.string.place_holder_note_description), fontSize = TEXT_DEFAULT)},
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions (
                onDone = {
                    keyboardController?.hide()
                }
            )
        )
    }
}