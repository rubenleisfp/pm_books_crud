# Catálogo de Libros - Aplicación Android

## Descripción
Esta aplicación permite gestionar un catálogo de libros, facilitando operaciones
CRUD (Crear, Leer, Actualizar y Eliminar) sobre la colección de libros.
Con respecto a la rama edit_action, tendremos varios modos para visualizar la informacion

### READ
En el modo READ, se muestra una lista de libros con sus detalles.

Al pulsar sobre el icono editar, nos dirige al modo modify, se copian los campos del elemento seleccionado
a las cajas de texto (titulo y autor)

Al pulsar sobre el boton inferior de añadir, nos dirige al modo CREATE

### CREATE / MODIFY
En el modo CREATE, se muestra un formulario para crear un nuevo libro.
En el modo MODIFY, se muestra un formulario para editar un libro existente.
Tendrán en la parte inferior 2 botones para confirmar o cancelar la operación.


## Características
- **Visualización**: Muestra una lista de libros con sus detalles.
- **Edición**: Permite modificar los detalles de los libros existentes.
- **Eliminación**: Se pueden eliminar libros del catálogo.
- **Interfaz Moderna**: Utiliza Jetpack Compose para una experiencia fluida e intuitiva.

## Tecnologías Utilizadas
- **Kotlin**: Lenguaje principal de la aplicación.
- **Jetpack Compose**: Para la interfaz de usuario declarativa.
- **Material 3**: Diseño moderno y accesible.
- **ViewModel**: Para manejar la lógica de negocio.

