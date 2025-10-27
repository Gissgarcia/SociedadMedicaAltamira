# Proyecto: Sociedad Médica Altamira

## Integrantes del equipo
- **Gissel Garcia**
- **Genesis Rojas**

## Descripción general del proyecto
**Sociedad Médica Altamira** es una aplicación móvil desarrollada en **Kotlin** con **Jetpack Compose**, diseñada para mejorar la experiencia de los pacientes y profesionales de salud de la clínica “Altamira”.  
Su objetivo principal es ofrecer una interfaz moderna, intuitiva y segura para **gestionar reservas médicas, mantener perfiles de usuario, acceder a servicios médicos y configurar preferencias personalizadas**.  

El proyecto fue desarrollado siguiendo el patrón **MVVM (Model–View–ViewModel)** y buenas prácticas de arquitectura, lo que facilita la mantenibilidad, modularidad y escalabilidad del código.  
Además, la aplicación hace uso de recursos nativos del dispositivo (como **cámara** y **almacenamiento**) e implementa **persistencia local** mediante **DataStore Preferences**.  

## Funcionalidades implementadas

### Autenticación de usuarios
- Registro e inicio de sesión con validaciones de campos y mensajes visuales de error.  
- Persistencia de sesión activa durante el uso de la app.  

### Gestión de reservas médicas
- Creación, edición y eliminación de reservas médicas.  
- Validaciones automáticas de datos (fecha, hora, especialidad).  
- Confirmaciones visuales con animaciones.  

### Perfil del usuario
- Visualización y edición de información personal.  
- Integración con cámara y galería del dispositivo para foto de perfil.  

### Configuración de la aplicación
- Activación del **Modo Especial** con almacenamiento persistente mediante **DataStore**.  

### Navegación y estructura visual
- Navegación fluida entre pantallas con **NavHostController**.  
- Diseño modular con pantallas independientes (`AuthScreen`, `HomeScreen`, `ReservaScreen`, `ProfileScreen`, `SettingsScreen`).  
- Uso de **Material Design 3** y temas personalizados.  

### Persistencia local
- **DataStore Preferences** para guardar configuraciones del usuario.  
- Estructura lista para integrar **Room** como base de datos local.  

### Recursos nativos integrados
- **Cámara:** captura de imagen de perfil con `FileProvider`.  
- **Almacenamiento:** selección de imágenes desde la galería.  

## Arquitectura del proyecto

Estructura modular siguiendo el patrón **MVVM**:

```
com.example.sociedadmedicaaltamira_grupo13
│
├── data/                → Persistencia local (DataStore)
├── model/               → Modelos de datos
├── repository/          → Lógica de negocio
├── ui/
│   ├── screens/         → Pantallas principales (Compose)
│   ├── components/      → Elementos visuales reutilizables
│   └── theme/           → Colores, tipografía y estilos
├── viewmodel/           → Manejo de estado y validaciones
└── navigation/          → Control de rutas
```

---

## Pasos para ejecutar el proyecto

### **Requisitos previos**
1. Tener instalado **Android Studio**.  
2. Configurar **Android SDK 24+**.  
3. Conexión a Internet para sincronizar dependencias.  
4. Emulador Android o dispositivo físico con cámara habilitada.  

### **Ejecución paso a paso**
1. Clonar o importar el proyecto:
   ```bash
   git clone https://github.com/Gissgarcia/SociedadMedicaAltamira.git
   ```
2. Sincronizar con Gradle → *File → Sync Project with Gradle Files*.  
3. Verificar dependencias de Compose, Navigation, DataStore y ViewModel.  
4. Conceder permisos de cámara y almacenamiento.  
5. Ejecutar el proyecto con *Run App*.  
6. Probar: registrar usuario, crear reserva, actualizar perfil y activar modo especial.  


## Herramientas utilizadas
- **Lenguaje:** Kotlin  
- **Framework:** Jetpack Compose  
- **Arquitectura:** MVVM  
- **Persistencia:** DataStore Preferences  
- **Control de versiones:** Git y GitHub  
- **Planificación:** Trello  
- **Diseño:** Material Design 3  
