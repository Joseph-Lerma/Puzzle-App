# Puzzle — Univalle

App Android de rompecabezas deslizante (3×3, 4×4 y 5×5) con modos Clásico, Contrarreloj, Mis fotos y Duelo VS.
Proyecto del curso de Desarrollo de Aplicaciones Móviles, Universidad del Valle.

## Equipo

| Integrante | Área |
|---|---|
| Daniel Hernández Ramírez | Diseño (Figma), acceso, perfil, configuración, pruebas y gestión |
| Jorman Alexis Botero Gaviria | Base técnica, base de datos, Firebase y autenticación |
| Juan David Agudelo | Motor del puzzle y modos de juego |
| Joseph Lerma | Duelo VS y amigos |
| Jhon Sebastián Londoño | Invitado, sin conexión, Mis fotos y ranking |

- Repositorio: https://github.com/Joseph-Lerma/Puzzle-App
- Tablero: https://trello.com/b/tdm4XrDy/puzzle-univalle
- Diseño: https://www.figma.com/design/CWRLAkvJDLQRWRzQkH1QgX

## Tecnologías

- Kotlin + Jetpack Compose (Material 3), arquitectura MVVM
- Navigation Compose
- Pruebas unitarias con JUnit
- Próximamente: Room + DataStore (local) y Firebase Authentication + Firestore (plan Spark)

## Estructura

```
app/     Interfaz (pantallas Compose), ViewModels y datos
motor/   Lógica pura del puzzle (sin Android), probada con JUnit
```

## Cómo abrir el proyecto

1. Instalar Android Studio (versión reciente) con el SDK de Android 37.
2. `File → Open` y elegir la carpeta del repositorio.
3. Esperar a que Gradle sincronice. Android Studio crea `local.properties` solo (no se sube al repositorio).
4. Ejecutar la configuración `app` en un emulador o teléfono.

### Firebase (cuando se configure en T-13)

El archivo `app/google-services.json` **no se sube** al repositorio. Cada integrante lo descarga desde
la consola de Firebase (Configuración del proyecto → Tus apps → Android) y lo copia en la carpeta `app/`.

## Pruebas

```
./gradlew test
```

## Flujo de trabajo

- Git Flow: `main` (entregas), `develop` (integración) y una rama `feature/T-XX-descripcion-corta` por tarjeta de Trello, creada desde `develop`.
- Commits en español con el ID de la tarjeta: `T-24: mover pieza adyacente a la vacía`.
- Todo entra a `develop` por Pull Request revisado por otro integrante; `main` recibe `develop` al cerrar cada sprint.
- Antes de abrir un PR: el proyecto compila y pasan las pruebas.
