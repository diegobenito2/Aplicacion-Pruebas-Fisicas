# Proyecto de Gestión de Pruebas Físicas

Este proyecto tiene como objetivo desarrollar una aplicación que permita gestionar datos personales, realizar pruebas físicas, y calcular resultados asociados a las pruebas.

## Funcionalidades

### 1. Pantalla de Login
- Permite al usuario iniciar sesión(Dos textFields con teclado numérico y teclado de texto con validaciones para el email y la contraseña).
- La contraseña debe poder cambiarse en tiempo de ejecución(Navigación a una pantalla de cambio de contraseña con un textField).

### 2. Gestión de Datos Personales (Pantalla Principal)
- #### Permite introducir y modificar los siguientes datos:
  - ##### Edad
  - ##### Peso
  - ##### Altura
  - ##### Sexo
- **Botón IMC:** Muestra el cálculo del Índice de Masa Corporal (IMC) en una vista nueva.
- **Icono de impresión:** Permite imprimir las marcas y notas obtenidas en las pruebas.

### 3. Vista de Pruebas Físicas
En esta vista se muestran las pruebas físicas con la siguiente información:
- ### CardView
  - ##### Imagen representativa
  - ##### Nombre de la prueba
  - ##### Enlace explicativo sobre cómo se realiza la prueba(Pedido al chatGPT)

Al pulsar sobre una prueba:
- Se navega a una vista donde se introducen los datos necesarios para calcular la nota(Un textField con teclado numérico y icono de un tic para guardar en la base de datos.).
- Un botón para volver a la vista de datos(La principal) en la que está el botón de impresión de las notas.

### 4. Funcionalidades Adicionales en la Vista de Pruebas
- **Buscador:** Implementar un `SearchView` para encontrar pruebas por nombre.
- **Agrupación de pruebas por categorías:**
- **Filtros:(DropDownMenu)**
    - **Por edad**
      - Fuerza muscular (DropDownMenuItem)
      - Flexibilidad (DropDownMenuItem)
      - Velocidad (DropDownMenuItem)
      - Agilidad (DropDownMenuItem)
      - Coordinación (DropDownMenuItem)
      - Resistencia (DropDownMenuItem)
### 6. Otras Características
- **Compatibilidad con modo horizontal y vertical en todas las vistas.**
- **Modo oscuro en todas las vistas:** Activable mediante un switch.
