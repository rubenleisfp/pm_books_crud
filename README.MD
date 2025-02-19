# Catálogo de Libros - Aplicación Android

## Descripción
Esta aplicación permite gestionar un catálogo de libros, facilitando operaciones 
CRUD (Crear, Leer, Actualizar y Eliminar) sobre la colección de libros.
La aplicación cuenta con varios modos para visualizar la información, proporcionando 
una experiencia de usuario completa y fluida.

Inicialmente se muestra una pantalla de inicio de sesión. 
Si el inicio de sesión es correcto, se redirige a la pantalla principal de libros. 
Si el inicio de sesión es incorrecto, se muestra un mensaje indicando que las credenciales son incorrectas.

### Modos de Visualización

#### IS_LOADING
Durante la carga inicial, se muestra un círculo de progreso, simulando la carga de datos
desde un API.

#### READ
En el modo READ, se presenta una lista de libros con sus detalles. 
En la parte superior, hay un campo de búsqueda para filtrar libros por palabras contenidas 
en el título o el autor.

Al pulsar el icono de editar, se cambia al modo MODIFY, donde los campos del libro seleccionado 
se copian a los campos de texto (título y autor).

Al pulsar el botón de añadir, se cambia al modo CREATE.

#### CREATE / MODIFY
En el modo CREATE, se muestra un formulario para crear un nuevo libro. 
En el modo MODIFY, se muestra un formulario para editar un libro existente. 
Ambos modos tienen botones inferiores para confirmar o cancelar la operación.

## Características
- **Visualización**: Muestra una lista de libros con detalles como título y autor.
- **Búsqueda**: Permite buscar libros por título o autor.
- **Edición**: Posibilita modificar los detalles de los libros existentes.
- **Creación**: Permite añadir nuevos libros al catálogo.
- **Eliminación**: Permite eliminar libros del catálogo.
- **Interfaz Moderna**: Utiliza Jetpack Compose para una experiencia de usuario fluida e intuitiva.

## Tecnologías Utilizadas
- **Kotlin**: Lenguaje principal de la aplicación.
- **Jetpack Compose**: Para la interfaz de usuario declarativa.
- **Material 3**: Para un diseño moderno y accesible.
- **ViewModel**: Para manejar la lógica de negocio.
- **Coroutines**: Para operaciones asíncronas.
- **Retrofit**: Para llamadas a servicios API RESTful.