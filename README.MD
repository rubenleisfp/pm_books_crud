# Catálogo de Libros - Aplicación Android

## Descripción
Esta aplicación permite gestionar un catálogo de libros, facilitando operaciones 
CRUD (Crear, Leer, Actualizar y Eliminar) sobre la colección de libros.
La aplicación cuenta con varios modos para visualizar la información, proporcionando 
una experiencia de usuario completa y fluida.

Inicialmente mostrara una pantalla de login. 
Si el login fue correcto, nos redirigirá a la pantalla principal de Books. 
Si el login es incorrecto mostraremos un toast indicando que las credenciales son incorrectas.

### Modos de Visualización

#### IS_LOADING
Durante la carga inicial se muestra un círculo de progreso, simulando la carga de datos
de un API.

#### READ
En el modo READ, se muestra una lista de libros con sus detalles. 
En la parte superior se encuentra un campo de búsqueda para filtrar por una palabra 
que esté contenida en el título o el autor.

Al pulsar sobre el icono de editar, nos dirige al modo MODIFY, donde se copian 
los campos del elemento seleccionado a las cajas de texto (título y autor).

Al pulsar sobre el botón inferior de añadir, nos dirige al modo CREATE.

#### CREATE / MODIFY
En el modo CREATE, se muestra un formulario para crear un nuevo libro. 
En el modo MODIFY, se muestra un formulario para editar un libro existente. 
Ambos modos cuentan con botones inferiores para confirmar o cancelar la operación.

## Características
- **Visualización**: Muestra una lista de libros con detalles como título y autor.
- **Búsqueda**: Permite buscar libros por título o autor.
- **Edición**: Permite modificar los detalles de los libros existentes.
- **Creación**: Se pueden añadir nuevos libros al catálogo.
- **Eliminación**: Se pueden eliminar libros del catálogo.
- **Interfaz Moderna**: Utiliza Jetpack Compose para una experiencia fluida e intuitiva.

## Tecnologías Utilizadas
- **Kotlin**: Lenguaje principal de la aplicación.
- **Jetpack Compose**: Para la interfaz de usuario declarativa.
- **Material 3**: Diseño moderno y accesible.
- **ViewModel**: Para manejar la lógica de negocio.
- **Coroutines**: Para operaciones asíncronas.