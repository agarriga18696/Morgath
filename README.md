# 🕯️ Morgath

  

**Morgath** es una **aventura RPG por comandos** inspirada en los clásicos juegos de texto como *Zork I: The Great Underground Empire*.

El jugador explora un mundo oscuro y misterioso mediante texto, introduciendo comandos que controlan sus acciones, exploración e interacción con el entorno.

  

---

  

## ⚙️ Estado del proyecto

  

🚧 **En desarrollo activo.**

El juego todavía no se encuentra en su versión final.

Puedes probarlo, modificarlo o ampliarlo, pero aún faltan misiones, eventos y zonas por implementar.

  

---

  

## 🧩 Características

  

- Aventura textual con ambientación RPG y exploración libre.

- Interfaz **gráfica en Java** (Swing/AWT), **sin librerías externas**.

- Sistema de **comandos** al estilo clásico (moverse, explorar, mirar, interactuar con objetos, etc.).

- Mapa dinámico, inventario, misiones, y personajes interactivos.

- Sistema de **temas visuales** y fuentes retro incluidas.

  

---

  

## 🔓 Licencia

  

**Código abierto**, puedes:

- 📘 Usarlo libremente.

- 🧠 Estudiarlo o modificarlo.

- 🛠️ Crear tus propias versiones derivadas.

  

❌ **No está permitido distribuirlo ni venderlo** de forma total o parcial, sin el permiso del autor original.

  

---

  

## 🧭 Cómo jugar

  

Una vez ejecutado el juego, escribe los comandos directamente en la consola de Morgath.

A continuación se muestra la lista completa de comandos disponibles:

  

### 🧭 DIRECCIÓN

  

| Comando | Descripción |
|----------|--------------|
| `IR <DIRECCION>` | Te mueves en la dirección indicada. |
| `VOLVER` | Retrocedes a la ubicación anterior. |

  

### 🔍 EXPLORACIÓN

  

| Comando | Descripción |
|----------|--------------|
| `LUGAR` | Muestra la descripción de tu ubicación actual. |
| `EXPLORAR` | Exploras el lugar en busca de caminos a seguir. |
| `MIRAR` | Observas el alrededor para detectar personajes o enemigos. |
| `BUSCAR` | Encuentra objetos en tu ubicación. |
| `COGER <OBJETO>` | Coges un objeto de la ubicación actual y lo añades a tu inventario. |
| `SOLTAR <OBJETO>` | Tiras un objeto de tu inventario al suelo de la ubicación actual. |
| `ALMACENAR <OBJETO> <CONTENEDOR>` | Almacenas un objeto de tu inventario dentro de un contenedor. |
| `SACAR <OBJETO> <CONTENEDOR>` | Sacas un objeto de un contenedor y lo devuelves al inventario. |
| `DESTRUIR <OBJETO>` | Destruye un objeto de tu inventario permanentemente. |
| `INVENTARIO` | Muestra los objetos que estás portando. |
| `MISION` | Muestra la misión actual. |
| `DIARIO` | Muestra las misiones que has completado. |
| `LEVANTARSE` | Te levantas del suelo si estás tumbado. |

  

### 🎮 JUEGO

  

| Comando | Descripción |
|----------|--------------|
| `TERMINAR` | Terminas la partida. |
| `REINICIAR` | Reinicias el juego. |
| `GUARDAR` | Guardas el progreso del juego. |
| `TEMA <TEMA>` | Cambia el esquema de color de la interfaz. |
| `CREDITOS` | Muestra información del juego y del creador. |
| `VERSION` | Muestra la versión actual del juego. |

  

---

  

## 💾 Instalación y ejecución

  

### 🔸 Requisitos previos

- Tener instalado **Java JDK 17 o superior**.

- Tener configurada la variable de entorno `JAVA_HOME`.

  

---

  

### 🖥️ Ejecución desde consola

  

1.  **Clona el repositorio:**

```bash

git clone https://github.com/agarriga18696/Morgath

```

  

2.  **Accede al directorio del proyecto:**

```bash

cd Morgath

```

  

3.  **Compila el código fuente:**

```bash

javac -d bin src/juego/Principal.java

```

  

4.  **Ejecuta el juego:**

```bash

java -cp bin juego.Principal

```

  

---

  

### 💡 Ejecución desde Eclipse

  

1. Abre **Eclipse IDE**.

2. Selecciona **File > Import > Existing Projects into Workspace**.

3. Navega hasta la carpeta del proyecto `/Morgath` y pulsa **Finish**.

4. Abre el archivo `Principal.java` dentro del paquete `juego`.

5. Haz clic derecho → **Run As > Java Application**.

  

---

  

## 🧱 Estructura del proyecto

  

```

/Morgath/
│ .classpath
│ .gitignore
│ .project
│
├───.settings/
│ org.eclipse.core.resources.prefs
│ org.eclipse.jdt.core.prefs
│ org.eclipse.m2e.core.prefs
│
├───resources/
│ ├───fonts/ → Fuentes utilizadas por el juego
│ └───icons/ → Iconos gráficos del interfaz
│
└───src/
| ├───comandos/ → Sistema de comandos del jugador
| ├───configuracion/ → Configuración general, temas y fuentes
| ├───juego/ → Núcleo del juego y punto de entrada (`Principal.java`)
| ├───localizaciones/→ Mapas y habitaciones
| ├───misiones/ → Gestión de misiones y objetivos
| ├───objetos/ → Sistema de objetos, armas, llaves y contenedores
| ├───personajes/ → Clases de personajes, enemigos y NPCs
| └───utilidades/ → Funciones auxiliares

```

  

---

  

## 👤 Autor

  

**Andreu Garriga Cendán**

📧 *https://github.com/agarriga18696*

  

---

  

## ✨ Créditos

  

Fuentes y recursos visuales incluidos en `/resources/` son de libre uso o bajo licencias compatibles.

El juego ha sido diseñado y programado **desde cero en Java**, sin frameworks ni librerías externas.

  

---

  

> *“En las profundidades de Morgath, cada decisión importa. Las sombras observan… ¿estás preparado para adentrarte?”*

> — *El Oráculo de las Catacumbas*
