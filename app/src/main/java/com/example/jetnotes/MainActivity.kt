package com.example.jetnotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
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
                    HomeScreen(navHostController = navHostController, noteViewModel = noteViewModel)
//                    NoteScreen(navController = navHostController, noteViewModel = noteViewModel, selected = null)
//                    NavHost(navController = navHostController, startDestination = "login") {
//                        composable("login") { HomeScreen(navHostController = navHostController, noteViewModel = noteViewModel) }
//                        composable("signup") {NoteScreen(navController = navHostController, noteViewModel = noteViewModel, selected = null) }
//                    }
                }
            }
        }
    }
}