package com.example.notesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(color = MaterialTheme.colorScheme.background)
                {
                    NotesScreen()
                }
            }
        }
    }
}

@Composable
fun NotesScreen(vm: NotesViewModel = viewModel()) {
    val notes by vm.notes.collectAsState()
    var input by rememberSaveable { mutableStateOf("") }
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement
        = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = input,
                onValueChange = { input = it },
                modifier = Modifier.weight(1f),
                label = { Text("Catatan baru") }
            )
            Button(onClick = { vm.addNote(input); input = "" }) {
                Text("Tambah")
            }
        }
        Spacer(Modifier.height(12.dp))
        LazyColumn {
            items(notes, key = { it.id }) { note ->
                NoteRowDeletable(note = note, onDelete = { vm.deleteNote(it)
                })
                Divider()
            }
        }
    }
}

@Composable
fun NoteRowDeletable(note: Note, onDelete: (Note) -> Unit) {
    ListItem(
        headlineContent = { Text(note.title) },
        trailingContent = {
            IconButton(onClick = { onDelete(note) }) {
                Icon(Icons.Filled.Delete, contentDescription =
                    "Hapus")
            }
        }
    )
}

@Composable
fun NoteRowReadOnly(note: Note) {
    ListItem(
        headlineContent = { Text(note.title) }
    )
}