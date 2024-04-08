package com.phucbruh.giua_ki.presentation

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.phucbruh.giua_ki.data.Book
import com.phucbruh.giua_ki.data.bookList
import com.phucbruh.giua_ki.ui.theme.Nguyenducgiaphuc_22ad037Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookScreen(
    books: List<Book>,
    searchQuery: String,
    onAdd: () -> Unit,
    onEditBook: (Book) -> Unit,
    onDeleteBook: (Book) -> Unit,
    onSearch: (String) -> Unit
) {
    var searchQuery by remember { mutableStateOf(searchQuery) }
    var active by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                SearchBar(
                    query = searchQuery,
                    onQueryChange = {
                        searchQuery = it
                        if (searchQuery.isEmpty())
                            onSearch(searchQuery)
                    },
                    onSearch = {
                        onSearch(searchQuery)
                    },
                    active = false,
                    onActiveChange = {},
                    placeholder = { Text(text = "Search for book you want") },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                modifier = Modifier.clickable {
                                    if (searchQuery.isNotEmpty()) {
                                        searchQuery = ""
                                        onSearch(searchQuery)
                                    } else {
                                        active = false
                                    }
                                }
                            )
                        }
                    },
                    shape = RectangleShape,
                ) {
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                shape = MaterialTheme.shapes.large.copy(CornerSize(percent = 50)),
                onClick = onAdd,
            ) {
                Icon(Icons.Default.Add, contentDescription = null) // FAB icon
            }
        },
        content = { padding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp)
                    .padding(top = padding.calculateTopPadding() + 12.dp)
                    .padding(bottom = padding.calculateBottomPadding() + 24.dp)
            ) {
                items(books) { book ->
                    BookItem(
                        book = book,
                        onEditBook = onEditBook,
                        onDeleteBook = onDeleteBook,
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                    )
                }
            }
        }
    )
}

@Composable
fun BookItem(
    book: Book,
    onEditBook: (Book) -> Unit,
    onDeleteBook: (Book) -> Unit,
    modifier: Modifier = Modifier
) {
    var isShowDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    if (isShowDialog) {
        YesNoDialog(
            onDismissRequest = { isShowDialog = false },
            onConfirmation = {
                isShowDialog = false
                onDeleteBook(book)
                Toast.makeText(
                    context, "The book with id=${book.id} is deleted", Toast.LENGTH_SHORT
                ).show()
            },
            dialogTitle = "Delete book",
            dialogText = "Are you sure to delete the book with id=${book.id}?"
        )
    }
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {
            Column(
                Modifier
                    .padding(vertical = 8.dp)
                    .widthIn(0.dp, 200.dp)
            ) {
                Text(
                    text = "${book.id}. ${book.name}",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${book.author} - ${book.releasedYear}",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
            Row(
                horizontalArrangement = Arrangement.End,
            ) {
                IconButton(onClick = { onEditBook(book) }) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "edit",
                        tint = Color.Blue
                    )
                }
                IconButton(onClick = { isShowDialog = true }) {
                    Icon(
                        imageVector = Icons.Rounded.Delete,
                        contentDescription = "delete",
                        tint = Color.Red
                    )
                }
            }
        }
    }
}

@Composable
fun YesNoDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
) {
    AlertDialog(
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Yes")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("No")
            }
        }
    )
}


@Preview(showSystemUi = true)
@Composable
fun BookScreenPreview() {
    Nguyenducgiaphuc_22ad037Theme {
        BookScreen(
            books = bookList,
            searchQuery = "",
            onAdd = {},
            onEditBook = {},
            onDeleteBook = {},
            onSearch = {}
        )
    }
}
