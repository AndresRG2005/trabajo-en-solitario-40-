Sistema de Notas por Usuario
Aplicación de consola en Java que permite registrar usuarios, iniciar sesión y gestionar notas.
Los datos se guardan en ficheros dentro de la carpeta data/.

Funciones principales
Registrar usuario

Iniciar sesión

Crear nota

Listar notas

Ver nota por número

Eliminar nota

Guardado automático en ficheros

Estructura del proyecto
Código
src/
  app/
    Main.java
  service/
    FileManager.java
    UserManager.java
    NoteManager.java
  model/
    Nota.java

data/ (se crea automáticamente al ejecutar)
  users.txt
  usuarios/
    <email_sanitizado>/
      notas.txt
Cómo ejecutar
Abrir el proyecto en tu IDE (VS Code, IntelliJ, etc.)

Ejecutar el archivo:

Código
src/app/Main.java
La carpeta data/ se generará sola al iniciar el programa.

Notas
El email del usuario se convierte en nombre de carpeta reemplazando @ y . por _.

Si borras la carpeta data/, el programa la vuelve a crear automáticamente.