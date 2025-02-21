# Proyecto de Gestión de Pruebas Físicas

Este proyecto tiene como objetivo desarrollar una aplicación que permita gestionar datos personales, realizar pruebas físicas, y calcular resultados asociados a las pruebas.

## Funcionalidades

### 1. Pantalla de Login
- Permite al usuario iniciar sesión.
- La contraseña debe poder cambiarse en tiempo de ejecución.

### 2. Gestión de Datos Personales (Persistentes)
Permite introducir y modificar los siguientes datos:
- Edad
- Peso
- Altura
- Sexo

### 3. Vista de Pruebas Físicas
En esta vista se muestran las pruebas físicas con la siguiente información:
- Imagen representativa
- Nombre de la prueba
- Enlace explicativo sobre cómo se realiza la prueba

Al pulsar sobre una prueba:
- Se navega a una vista donde se introducen los datos necesarios para calcular la nota.
- Los datos introducidos deben ser persistentes.

### 4. Funcionalidades Adicionales en la Vista de Pruebas
- **Buscador:** Implementar un `SearchView` para encontrar pruebas por nombre.
- **Agrupación de pruebas por categorías:**
  - Fuerza muscular
  - Flexibilidad
  - Velocidad
  - Agilidad
  - Coordinación
  - Resistencia

### 5. Pantalla Principal
- **Botón IMC:** Muestra el cálculo del Índice de Masa Corporal (IMC) en una vista nueva.
- **Icono de impresión:** Permite imprimir las marcas y notas obtenidas en las pruebas.

### 6. Otras Características
- **Compatibilidad con modo horizontal y vertical.**
- **Modo oscuro:** Activable mediante un switch.
