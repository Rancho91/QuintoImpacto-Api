# Ecosistema - Directorio de impacto

## Configuraciones Generales

### Requisitos Previos

- JDK (Java Development Kit) versión 17.x o superior.
- Maven versión 3.9.x o superior.

### Configuración del Entorno

1. **Instalación del JDK**: Descarga e instala el JDK desde [el sitio oficial de Oracle](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html).

2. **Configuración de las Variables de Entorno**: Asegúrate de que las variables de entorno `JAVA_HOME` y `PATH` estén configuradas correctamente para apuntar al directorio de instalación del JDK.

   Ejemplo (para Windows):

JAVA_HOME=C:\Program Files\Java\jdk-XX.X.X
PATH=%JAVA_HOME%\bin;%PATH%

3. **Instalación de Maven**: Si el proyecto utiliza Maven, descarga e instala Maven desde [el sitio oficial de Apache Maven](https://maven.apache.org/download.cgi).

4. **Configuración de Maven**: Asegúrate de que la variable de entorno `M2_HOME` esté configurada correctamente para apuntar al directorio de instalación de Maven, y añade `%M2_HOME%\bin` al `PATH`.

    Ejemplo (para Windows):

M2_HOME=C:\Program Files\Apache\maven
PATH=%M2_HOME%\bin;%PATH%

## Variables de entorno

Para facilitar la configuración del proyecto y garantizar que cada desarrollador tenga una configuración consistente, se establece la práctica de utilizar un archivo de variables de entorno. Este archivo se crea a partir de un archivo de ejemplo llamado .env.example.

1. Crear el Archivo .env a partir del .env.example:
2. Una vez creado el archivo .env, es importante revisar y modificar cada variable de entorno según sea necesario.
3. El archivo .env ya está ignorado en el .gitignore

## Contribuciones

Si deseas contribuir al proyecto, por favor sigue estos pasos:

1. *Clonar* el repositorio.
2. Crea una nueva rama (`git checkout -b feature/nueva-funcionalidad`).
3. Realiza tus cambios y haz *commit* (`git commit -am 'Agrega nueva funcionalidad'`).
4. Haz *push* a la rama (`git push origin feature/nueva-funcionalidad`).

## Consideraciones generales

1. No Realizar Push sobre la Rama Main:

- La rama main es la rama principal del repositorio y debe mantenerse estable en todo momento. Evitar realizar push directamente a esta rama ayuda a prevenir conflictos y errores en el código base.
- En su lugar, todas las contribuciones y cambios deben realizarse a través de ramas secundarias.
- Utilizar la Rama "Develop" como la más Actualizada:

2. Se recomienda crear y utilizar una rama llamada develop como rama principal para el desarrollo continuo.
- Esta rama debe reflejar siempre la última versión del código que está en proceso de desarrollo, integrando todas las nuevas características y correcciones de errores.
- La rama develop sirve como punto de integración para las características desarrolladas por diferentes colaboradores antes de ser fusionadas en la rama main.
- Al mantener la rama develop actualizada, se facilita la colaboración y se reducen los conflictos durante la integración de cambios.