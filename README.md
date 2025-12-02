Sociedad Médica Altamira (App Móvil Android)
🏥 Sociedad Médica Altamira — App Móvil

Aplicación móvil desarrollada en Android Studio, utilizando Kotlin, Jetpack Compose, MVVM, persistencia local con DataStore, y consumo de APIs REST para la gestión de usuarios, reservas y contacto.

Este proyecto corresponde al desarrollo del módulo móvil de la plataforma Sociedad Médica Altamira, permitiendo a los usuarios:

Registrarse e iniciar sesión

Crear reservas médicas

Visualizar sus reservas

Acceder a su perfil

Usar modo especial (configuración persistente)

Navegar entre pantallas de manera fluida mediante Compose Navigation

🛠️ Tecnologías utilizadas
Frontend móvil

Kotlin

Jetpack Compose

Material 3

Compose Navigation

ViewModel (MVVM)

StateFlow / MutableStateFlow

DataStore Preferences

Coil (carga de imágenes)

Backend consumido

La app se conecta a tres microservicios:

API Usuario → Registro, login, roles

API Reserva → Creación y listado de reservas

API Contacto → Envío de formulario desde la app

🧱 Arquitectura

La app implementa el patrón MVVM completo:

📦 data/
├── remote/ (RetrofitClient + servicios API)
├── repository/ (ReservaRepository, UsuarioRepository, etc.)
📦 ui/
├── screens/ (Compose UI)
📦 viewmodel/
├── MainViewModel
├── ReservaViewModel
├── LoginViewModel
📦 datastore/
├── SettingsDataStore

Ventajas de MVVM aplicadas al proyecto

UI reactiva con StateFlow

ViewModels mantienen estado aunque cambie la configuración

Repositorios permiten desacoplar la lógica de datos del UI

Tests unitarios fáciles de implementar (como lo exige la guía)

🔐 Login & Manejo de Sesión

El login persiste:

ID de usuario

correo

token

rol

Todo esto gestionado por:

Session.kt
MainViewModel.kt
SettingsDataStore.kt

📅 Reservas

El usuario puede:

Crear una nueva reserva

Ver sus reservas propias

Los administradores pueden ver TODAS las reservas (rol = ADMIN)

La pantalla está compuesta por:

ReservaScreen.kt
ReservaViewModel.kt
ReservaRepository.kt

🎨 Interfaz de Usuario

Construida 100% en Jetpack Compose

Componentes Material 3

Navegación tipo NavHost:

Home

Login

Registro

Reserva

Mis Reservas

Perfil

Administrador

📍 Persistencia Local — DataStore

Se utiliza DataStore para guardar:

Modo especial (boolean persistente)

Sesión del usuario

Preferencias simples

Esto permite que la app recuerde configuraciones incluso después de cerrar.

🧪 Pruebas (Tests)

Este proyecto incluye pruebas unitarias siguiendo la guía 3.2.2 DUOC:

✔ Tests ViewModel (Unit Tests)

Archivo:
ReservaViewModelTest.kt

Cubren:

Crear reserva (flujo completo, successMessage, reset de formulario)

Cargar reservas del usuario

Manejo de errores, loading y estados del UI

✔ Tests Repository (MockK + Retrofit mockeado)

Archivo:
ReservaRepositoryTest.kt

Cubren:

crearReserva() retorna el objeto esperado

obtenerReservasPorUsuario() retorna lista esperada

Mock de RetrofitClient y ReservaApiService

✔ Tests de lógica (Parte 7)

Archivo:
MainViewModelTest.kt

currentUser inicial = null

setCurrentUser() asigna usuario

logout() limpia sesión

✔ Tests UI (Opcional / Entregables)

Archivo:
ReservaScreenTest.kt

Visualización de campos en pantalla

Escritura en TextField

Validación de “Confirmar Reserva”

Nota: los tests UI requieren emulador para ejecutarse.

✔ Todos los tests compilados y documentados.

📱 Generación de APK

Para obtener el APK:

Build → Build Bundle(s) / APK(s) → Build APK(s)


El archivo generado queda disponible en:

app/build/outputs/apk/debug/app-debug.apk

🚀 Cómo ejecutar el proyecto

Clonar repositorio

Abrir en Android Studio (Giraffe o superior)

Instalar dependencias con Gradle Sync

Crear el archivo local.properties con:

sdk.dir=C:\\Users\\TU_USUARIO\\AppData\\Local\\Android\\Sdk


Ejecutar en emulador o dispositivo físico

📚 Requisitos cumplidos (según Encargo DUOC)

✔ MVVM implementado
✔ Repository + Retrofit + APIs externas
✔ Jetpack Compose completo
✔ DataStore
✔ Lógica distribuida
✔ Navegación
✔ Pruebas unitarias (ViewModel, Repository, Lógica)
✔ APK generado

👨‍💻 Autores

Desarrollado por Genesis Rojas
Carrera: Ingeniería en Informática
Institución: DUOC UC
Asignatura: DSY1105 — Desarrollo de Software Móvil