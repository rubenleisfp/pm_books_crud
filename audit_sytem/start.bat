@echo off

rem Introducir el nombre por el usuario
rem Si no se introduce un nombre, se muestra un mensaje de error y se sale
set "nombre="
echo "Introduzca su nombre, separado con guiones bajos. Ejemplo: ruben_leis_rodriguez"
set /p "nombre="
if "%nombre%" == "" (
    echo "Debe introducir un nombre."
    exit /b 1
)

rem Ejecutar el JAR con el nombre como segundo argumento
java -jar audit.jar start %nombre%

rem Mostrar un mensaje de éxito
echo "Se ha iniciado la auditoria para %nombre%."

rem Pausa para que el usuario pueda ver el mensaje
pause