package com.example.jetnotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jetnotes.ui.screens.HomeScreen
import com.example.jetnotes.ui.screens.note.NoteScreen
import com.example.jetnotes.ui.theme.JetNotesTheme
import com.example.jetnotes.viewmodels.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navHostController: NavHostController
    private val noteViewModel: NoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetNotesTheme {
                navHostController = rememberNavController()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ScreenMain(navHostController, noteViewModel)
                }
            }
        }
    }
}

@Composable
private fun ScreenMain(navHostController: NavHostController, noteViewModel: NoteViewModel) {
    NavHost(navController = navHostController, startDestination = "home_screen") {
        composable("home_screen") {
            HomeScreen(navHostController = navHostController, noteViewModel = noteViewModel)
        }
        composable("note_screen") {
            NoteScreen(navController = navHostController, noteViewModel = noteViewModel, selected = null)
        }
        composable("note_screen/{note_id}", arguments = listOf(
            navArgument("note_id") {
                type = NavType.IntType
            }
        )) {
            val selected = it.arguments?.getInt("note_id")
            NoteScreen(navController = navHostController, noteViewModel = noteViewModel, selected = selected)
        }
    }
}