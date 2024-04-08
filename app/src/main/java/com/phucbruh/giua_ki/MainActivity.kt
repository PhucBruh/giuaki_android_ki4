package com.phucbruh.giua_ki

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.phucbruh.giua_ki.data.BookRepository
import com.phucbruh.giua_ki.data.MockBookRepositoryImpl
import com.phucbruh.giua_ki.presentation.AddBookScreen
import com.phucbruh.giua_ki.presentation.navigation.Routes
import com.phucbruh.giua_ki.presentation.BookScreen
import com.phucbruh.giua_ki.presentation.EditBookScreen
import com.phucbruh.giua_ki.presentation.EquationScreen
import com.phucbruh.giua_ki.presentation.navigation.BottomNavigationBar
import com.phucbruh.giua_ki.presentation.PrimeScreen
import com.phucbruh.giua_ki.ui.theme.Nguyenducgiaphuc_22ad037Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bookRepository = MockBookRepositoryImpl()

        setContent {
            val navController = rememberNavController()
            Nguyenducgiaphuc_22ad037Theme(
                darkTheme = false
            ) {
                Scaffold(bottomBar = { BottomNavigationBar(navController = navController) },
                    content = { padding ->
                        NavHostContainer(
                            bookRepository = bookRepository,
                            navController = navController,
                            padding = padding
                        )
                    })
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ScaffoldPreview() {
    val bookRepository = MockBookRepositoryImpl()
    val navController = rememberNavController()
    Nguyenducgiaphuc_22ad037Theme {
        Scaffold(bottomBar = { BottomNavigationBar(navController = navController) },
            content = { padding ->
                NavHostContainer(
                    bookRepository = bookRepository,
                    navController = navController,
                    padding = padding
                )
            })
    }
}

@Composable
fun NavHostContainer(
    bookRepository: BookRepository,
    navController: NavHostController,
    padding: PaddingValues
) {
    var books by rememberSaveable { mutableStateOf(bookRepository.getAllBooks()) }
    var searchQuery by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current

    NavHost(navController = navController,
        startDestination = Routes.Prime.route,
        modifier = Modifier.padding(paddingValues = padding),
        builder = {
            composable(Routes.Prime.route) {
                PrimeScreen()
            }

            composable(Routes.Equation.route) {
                EquationScreen()
            }

            composable(Routes.Book.route) {
                BookScreen(
                    books = books,
                    searchQuery = searchQuery,
                    onAdd = { navController.navigate(Routes.Book.route + "/add") },
                    onEditBook = { navController.navigate(Routes.Book.route + "/${it.id}") },
                    onDeleteBook = { book ->
                        bookRepository.deleteBook(book)
                        books =
                            if (searchQuery.isNotEmpty()) bookRepository.filterBooks(searchQuery)
                            else bookRepository.getAllBooks()
                    },
                    onSearch = { query ->
                        searchQuery = query
                        books = bookRepository.filterBooks(query)
                    }
                )
            }

            composable(Routes.Book.route + "/add") {
                AddBookScreen(
                    onClose = { navController.navigate(Routes.Book.route) },
                    onSave = { book ->
                        val newId = bookRepository.addBooks(book)
                        books =
                            if (searchQuery.isNotEmpty()) bookRepository.filterBooks(
                                searchQuery
                            )
                            else bookRepository.getAllBooks()
                        Toast.makeText(
                            context, "Book with id $newId is added", Toast.LENGTH_SHORT
                        ).show()
                    })
            }

            composable(Routes.Book.route + "/{id}") { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id")
                id?.let {
                    val book = bookRepository.getBookById(id.toInt())
                    book?.let {
                        EditBookScreen(book = book,
                            onClose = { navController.navigate(Routes.Book.route) },
                            onEdit = { book ->
                                bookRepository.editBook(book)
                                books =
                                    if (searchQuery.isNotEmpty()) bookRepository.filterBooks(
                                        searchQuery
                                    )
                                    else bookRepository.getAllBooks()
                                navController.navigate(Routes.Book.route)
                                Toast.makeText(
                                    context, "Book with id ${book.id} is edited", Toast.LENGTH_SHORT
                                ).show()
                            })
                    }
                }
            }
        })
}
