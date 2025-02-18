package com.castelaofp.books.ui.screens.book

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.castelaofp.books.R
import com.castelaofp.books.data.repository.Datasource
import com.castelaofp.books.ui.theme.BooksTheme

/**
 * Created by Your name on 11/02/2025.
 */

@OptIn(ExperimentalMaterial3Api::class)
/**
 * Obtiene informacion del viewModel: state y eventos
 *
 * Pasa esta informacion a BookScreen, la cual es la encarga de pintar
 * la pantalla y llamar a los eventos recibidos
 *
 * Obtenemos  lo que necesitamos del viewModel: uiState y eventos y los pasamos
 * al resto de métodos que los requieran
 *
 * @bookViewModel viewModel que contiene toda la logica del app
 */
@Composable
fun BookScreen(
    navController: NavController,
    bookViewModel: BookViewModel,
    modifier: Modifier = Modifier
) {
    val bookState by bookViewModel.uiState.collectAsState()

    BooksScaffold(
        bookState = bookState,
        onNewBookTitleChange = { bookViewModel.setNewBookTitle(it) },
        onNewBookAuthorChange = { bookViewModel.setNewBookAuthor(it) },
        onAddBook = { bookViewModel.addBook() },
        onEditAction = { book -> bookViewModel.editAction(book) },
        onUpdateBook = {
            bookViewModel.updateBook()
        },
        onRemoveBook = { book -> bookViewModel.removeBook(book) },
        onNuevoAction = { bookViewModel.nuevoAction() },
        onCancelAction = { bookViewModel.cancelAction() },
        onSearchAction = { searchWord: String -> bookViewModel.searchAction(searchWord) },
        modifier = modifier
    )
}


/**
 * Sirve para renderizar toda nuestra pantalla.
 * Durante la carga inicial se mostrara un CircleProgressBar, simulando la carga
 * de la info de un API
 * Desligada del viewModel para poder hacer previews de la pantalla.
 *
 * Tenemos varios modos de ver la UI (Steps)
 * IS_LOADING:
 * -Circulo de carga
 *
 * READ:
 * -Contiene un campo de búsqueda para filtrar por autor o titulo
 * -La lista con los libros incluidos hasta ahora
 * -El boton de añadir
 *
 * MODIFY/CREATE:
 * -Vemos 2 cajas de texto, para ingresar el autor y titulo que queremos dar de alta
 * o modificar
 * -Debajo tendra botones de aceptar y cancelar
 *
 * @param bookState estado con la lista de libros y el nuevo libro
 * @param onNewBookTitleChange llamada cuando el usuario cambia el título del nuevo libro
 * @param onNewBookAuthorChange llamada cuando el usuario cambia el autor del nuevo libro
 * @param onAddBook llamada cuando el usuario pulsa el botón de agregar un nuevo libro
 * @param onUpdateBook llamada cuando el usuario pulsa el botón de actualizar un libro
 * @param onRemoveBook llamada cuando el usuario pulsa el botón de borrar un libro
 * @param onSearchAction llamada cuando el usuario pulsa el botón de buscar un libro
 * @param modifier modifier para el composable
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BooksScaffold(
    bookState: BookState,
    onNewBookTitleChange: (String) -> Unit,
    onNewBookAuthorChange: (String) -> Unit,
    onAddBook: () -> Unit,
    onEditAction: (Book) -> Unit,
    onUpdateBook: () -> Unit,
    onRemoveBook: (Book) -> Unit,
    onNuevoAction: () -> Unit,
    onCancelAction: () -> Unit,
    onSearchAction: (searchWord: String) -> Unit,
    modifier: Modifier = Modifier,

    ) {

    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.app_name), fontWeight = FontWeight.Bold
                )
            }, colors = TopAppBarDefaults.largeTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        )
    }) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.tertiaryContainer),
            color = MaterialTheme.colorScheme.background
        ) {
            when (bookState.action) {
                ActionEnum.IS_LOADING ->
                    IsLoading()


                ActionEnum.READ ->
                    BooksReadAction(
                        bookState = bookState,
                        onEditAction = onEditAction,
                        onRemoveBook = onRemoveBook,
                        onNuevoAction = onNuevoAction,
                        onSearchAction = onSearchAction,
                        modifier = modifier
                    )

                ActionEnum.CREATE -> BookEditableAction(
                    bookState = bookState,
                    onNewBookTitleChange = onNewBookTitleChange,
                    onNewBookAuthorChange = onNewBookAuthorChange,
                    onAddBook = onAddBook,
                    onUpdateBook = onUpdateBook,
                    onCancelAction = onCancelAction
                )

                ActionEnum.MODIFY -> BookEditableAction(
                    bookState = bookState,
                    onNewBookTitleChange = onNewBookTitleChange,
                    onNewBookAuthorChange = onNewBookAuthorChange,
                    onAddBook = onAddBook,
                    onUpdateBook = onUpdateBook,
                    onCancelAction = onCancelAction
                )

                ActionEnum.ERROR -> ErrorScreen()
            }
        }
    }
}

@Composable
fun ErrorScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.errorContainer),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(R.string.error_message),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onErrorContainer
            )
        }
    }


}

/**
 * Muestra los campos de texto para que el usuario pueda editar un libro.
 * Segun el estado de la app, se muestra un boton para dar de alta o modificar un libro
 *
 * @param bookState estado de la app
 * @param onNewBookTitleChange llamada cuando el usuario cambia el titulo del libro
 * @param onNewBookAuthorChange llamada cuando el usuario cambia el autor del libro
 * @param onAddBook llamada cuando el usuario pulsa el boton de dar de alta
 * @param onUpdateBook llamada cuando el usuario pulsa el boton de modificar
 */
@Composable
fun BookEditableAction(
    bookState: BookState,
    onNewBookTitleChange: (String) -> Unit,
    onNewBookAuthorChange: (String) -> Unit,
    onAddBook: () -> Unit,
    onUpdateBook: () -> Unit,
    onCancelAction: () -> Unit,
) {
    Column {
        CamposTexto(bookState.newBook, onNewBookTitleChange, onNewBookAuthorChange)
        Row() {
            if (bookState.action == ActionEnum.CREATE) {
                Button(
                    onClick = { onAddBook() },
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text(stringResource(R.string.add_button))
                }
            }
            if (bookState.action == ActionEnum.MODIFY) {
                Button(
                    onClick = {
                        onUpdateBook(
                        )
                    },
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text(stringResource(R.string.modify_button))
                }
            }
            Button(
                onClick = onCancelAction,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(stringResource(R.string.cancel_button))
            }
        }
    }
}

/**
 * Muestra un CircularProgressIndicator en el centro de la pantalla,
 * indicando que se esta cargando informacion.
 */
@Composable
fun IsLoading() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

/**
 * Contiene los botones de accion para la pantalla de lectura de los libros.
 *
 * En ella se encuentra un boton para agregar un nuevo libro
 * y la lista de libros existentes con sus botones de editar y eliminar.
 *
 * En la parte superior se encuentra un campo de búsqueda para filtrar por autor o titulo
 *
 * @param bookState estado con la lista de libros y el libro a dar de alta/actualizar
 * @param onEditAction llamada cuando el usuario pulsa el botón de editar un libro
 * @param onRemoveBook llamada cuando el usuario pulsa el botón de eliminar un libro
 * @param onNuevoAction llamada cuando el usuario pulsa el botón de agregar un nuevo libro
 * @param onSearchAction llamada cuando el usuario pulsa el botón de buscar un libro
 * @param modifier modifier para el composable
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BooksReadAction(
    bookState: BookState,
    onEditAction: (Book) -> Unit,
    onRemoveBook: (Book) -> Unit,
    onNuevoAction: () -> Unit,
    onSearchAction: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Row {
            OutlinedTextField(
                value = bookState.searchWord,
                onValueChange = onSearchAction,
                label = { Text(stringResource(R.string.search_bar)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }
        Spacer(modifier = Modifier.size(30.dp))
        BookList(
            list = bookState.books,
            onEditAction = { book -> onEditAction(book) },
            onRemoveBook = { book -> onRemoveBook(book) }
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = onNuevoAction,
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                .fillMaxWidth()
        ) {
            Text(stringResource(R.string.acept_button))
        }
    }
}

/**
 * Contiene los campos de texto titulo y autor, que el usuario usará para
 * dar el alta/actualizar un libro
 *
 * @param newBook libro que se va a dar de alta/actualizar
 * @param onNewBookTitleChange llamada cuando el usuario cambia el titulo del libro
 * @param onNewBookAuthorChange llamada cuando el usuario cambia el autor del libro
 */
@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun CamposTexto(
    newBook: Book,
    onNewBookTitleChange: (String) -> Unit,
    onNewBookAuthorChange: (String) -> Unit
) {
    Row {
        TextField(
            value = newBook.title,
            onValueChange = onNewBookTitleChange,
            singleLine = true,
            label = { Text(stringResource(R.string.input_titulo)) },
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        )

        TextField(
            value = newBook.author,
            onValueChange = onNewBookAuthorChange,
            singleLine = true,
            label = { Text(stringResource(R.string.input_author)) },
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        )
    }
}

/**
 * Mostramos la lista de libros
 *
 * @param books lista de libros a mostrar
 * @param onModifyBook llamada cuando el usuario pulsa el botón de modificar un libro
 * @param onRemoveBook llamada cuando el usuario pulsa el botón de borrar un libro
 * @param modifier modifier para el composable
 */
@Composable
fun BookList(
    list: List<Book>,
    onEditAction: (Book) -> Unit,
    onRemoveBook: (Book) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(
            items = list,
            key = { book -> book.id }
        ) { book ->
            BookItem(
                book = book,
                onModify = { onEditAction(book) },
                onRemoveBook = { onRemoveBook(book) }
            )
            Divider()
        }
    }
}

/**
 * Mostramos un elemento libro, con su titulo, autor y los iconos de accion
 *
 * @param book libro a mostrar
 * @param onModify llamada cuando el usuario pulsa el botón de modificar un libro
 * @param onRemoveBook llamada cuando el usuario pulsa el botón de eliminar un libro
 * @param modifier modifier para el composable
 */
@Composable
fun BookItem(
    book: Book,
    onModify: () -> Unit,
    onRemoveBook: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp),
            text = book.title,

            )
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp),
            text = book.author,

            )
        IconButton(onClick = onModify) {
            Icon(Icons.Filled.Edit, contentDescription = "Modify")
        }
        IconButton(onClick = onRemoveBook) {
            Icon(Icons.Filled.Close, contentDescription = "Close")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun BookScreenPreview() {
    val newBook = Book(id = 0, title = "", author = "")
    val bookViewModel = BookViewModel()

    BooksScaffold(
        bookState = BookState(
            books = Datasource().getBooks(),
            newBook = newBook,
            action = ActionEnum.READ,
            searchWord = ""
        ),
        onNewBookTitleChange = { bookViewModel.setNewBookTitle(it) },
        onNewBookAuthorChange = { bookViewModel.setNewBookAuthor(it) },
        onAddBook = { bookViewModel.addBook() },
        onEditAction = { book -> bookViewModel.editAction(book) },
        onUpdateBook = {
            bookViewModel.updateBook()
        },
        onRemoveBook = { book -> bookViewModel.removeBook(book) },
        onNuevoAction = { bookViewModel.nuevoAction() },
        onCancelAction = { bookViewModel.cancelAction() },
        onSearchAction = { searchWord -> bookViewModel.searchAction(searchWord) }
    )
}


@Preview
@Composable
fun BookItemPreview() {
    BooksTheme() {
        BookItem(
            book = Book(id = 1, title = "Sample Title", author = "Sample Author"),
            onModify = {},
            onRemoveBook = {},
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        )
    }
}

@Preview
@Composable
fun BookListPreview() {
    BooksTheme() {
        BookList(
            list = Datasource().getBooks(),
            onEditAction = {},
            onRemoveBook = {},
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        )
    }
}

@Preview
@Composable
fun CamposTextoPreview() {
    BooksTheme() {
        CamposTexto(
            newBook = Book(id = 1, title = "Sample Title", author = "Sample Author"),
            onNewBookTitleChange = {},
            onNewBookAuthorChange = {},

            )
    }
}

@Preview
@Composable
fun BooksReadActionPreview() {
    BooksTheme() {
        BooksReadAction(
            bookState = BookState(
                books = Datasource().getBooks(),
                newBook = Book(id = 1, title = "Sample Title", author = "Sample Author"),
                action = ActionEnum.READ,
                searchWord = ""
            ),
            onEditAction = {},
            onRemoveBook = {},
            onNuevoAction = {},
            onSearchAction = {},
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        )
    }
}

@Preview
@Composable
fun BooksEditableActionPreview() {
    BooksTheme {
        BookEditableAction(
            bookState = BookState(
                books = Datasource().getBooks(),
                newBook = Book(id = 1, title = "Sample Title", author = "Sample Author"),
                action = ActionEnum.CREATE,
                searchWord = ""
            ),
            onNewBookTitleChange = {},
            onNewBookAuthorChange = {},
            onAddBook = {},
            onUpdateBook = {},
            onCancelAction = {}
        )
    }
}
