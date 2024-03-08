package com.example.jetnotes.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SentimentVeryDissatisfied
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.jetnotes.R
import com.example.jetnotes.ui.theme.ICON_EMPTY
import com.example.jetnotes.ui.theme.Purple40
import com.example.jetnotes.ui.theme.surface

@Composable
fun EmptyContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(surface),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier
                .size(ICON_EMPTY),
            imageVector = Icons.Default.SentimentVeryDissatisfied,
            contentDescription = stringResource(id = R.string.icon_empty),
            tint = Purple40
        )
        Text(
            text = "\n\n" + stringResource(id = R.string.text_empty)
        )
    }
}