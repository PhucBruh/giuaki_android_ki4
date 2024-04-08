package com.phucbruh.giua_ki.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.phucbruh.giua_ki.data.Book
import com.phucbruh.giua_ki.presentation.components.TextIconButton
import com.phucbruh.giua_ki.ui.theme.Nguyenducgiaphuc_22ad037Theme

@Composable
fun AddBookScreen(
    onClose: () -> Unit,
    onSave: (Book) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var releasedYear by remember { mutableStateOf("") }
    val context = LocalContext.current

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                shape = MaterialTheme.shapes.large.copy(CornerSize(percent = 50)),
                onClick = onClose
            ) {
                Icon(Icons.Default.Close, contentDescription = null) // FAB icon
            }
        },
        content = { padding ->
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(text = "Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
                TextField(
                    value = author,
                    onValueChange = { author = it },
                    label = { Text(text = "Author") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
                TextField(
                    value = releasedYear,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = {
                        if (it.toIntOrNull() != null || it.isEmpty())
                            releasedYear = it
                    },
                    label = { Text(text = "Released at") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextIconButton(
                        text = "Refresh",
                        icon = Icons.Default.Refresh,
                        onClick = {
                            name = ""
                            author = ""
                            releasedYear = ""
                        },
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(1f)
                    )
                    TextIconButton(
                        text = "Save",
                        icon = Icons.Default.Save,
                        onClick = {
                            if (name.isEmpty() || author.isEmpty() || releasedYear.isEmpty()) {
                                Toast.makeText(
                                    context, "You must fill the input", Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                onSave(Book(name, author, releasedYear.toInt(), null))
                            }
                        },
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(1f)
                    )
                }
            }
        }
    )
}

@Preview(showSystemUi = true)
@Composable
fun AddBookScreenPreview() {
    Nguyenducgiaphuc_22ad037Theme {
        AddBookScreen(onClose = {}, onSave = {})
    }
}