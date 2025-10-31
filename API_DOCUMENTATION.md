# API REST - Plataforma Universitaria de Cursos en Línea

## 📋 Descripción
API REST desarrollada en **Java 23** con **Spring Boot 3.5.6** para gestionar una plataforma universitaria de cursos en línea. Incluye gestión de estudiantes, profesores, cursos, asignaturas y matrículas con persistencia en archivos CSV.

## 🗂️ Persistencia de Datos

### **Sistema de Archivos CSV**
Los datos se almacenan en archivos CSV en el directorio `data/`:

```
data/
├── estudiantes.csv
├── profesores.csv
├── cursos.csv
├── asignaturas.csv
└── matriculas.csv
```

### **Características de la Persistencia**
- ✅ Auto-generación de IDs únicos
- ✅ Escape automático de caracteres especiales (comas, comillas)
- ✅ Inicialización automática de archivos con headers
- ✅ Lectura/escritura thread-safe con AtomicLong
- ✅ Validación de relaciones entre entidades

## 🚀 Iniciar la Aplicación

### **Compilar el proyecto**
```bash
.\mvnw.cmd clean compile
```

### **Ejecutar la aplicación**
```bash
.\mvnw.cmd spring-boot:run
```

La API estará disponible en: `http://localhost:8080`

## 📡 Endpoints de la API

### **Base URL**: `http://localhost:8080/api`

---

## 👨‍🎓 Estudiantes

### **GET** `/api/estudiantes`
Lista todos los estudiantes.

**Respuesta exitosa (200):**
```json
[
  {
    "id": 1,
    "nombre": "Juan Pérez",
    "email": "juan.perez@umanizales.edu.co",
    "telefono": "3001234567",
    "matricula": "20241001",
    "carrera": "Ingeniería de Sistemas",
    "semestre": 5,
    "cursos": [],
    "calificaciones": []
  }
]
```

### **GET** `/api/estudiantes/{id}`
Obtiene un estudiante por ID.

### **POST** `/api/estudiantes`
Crea un nuevo estudiante.

**Request Body:**
```json
{
  "nombre": "Juan Pérez",
  "email": "juan.perez@umanizales.edu.co",
  "telefono": "3001234567",
  "matricula": "20241001",
  "carrera": "Ingeniería de Sistemas",
  "semestre": 5
}
```

### **PUT** `/api/estudiantes/{id}`
Actualiza un estudiante existente.

### **DELETE** `/api/estudiantes/{id}`
Elimina un estudiante (204 No Content).

---

## 👨‍🏫 Profesores

### **GET** `/api/profesores`
Lista todos los profesores.

**Respuesta exitosa (200):**
```json
[
  {
    "id": 1,
    "nombre": "María García",
    "email": "maria.garcia@umanizales.edu.co",
    "telefono": "3109876543",
    "especialidad": "Bases de Datos",
    "antiguedad": 10,
    "cursosImpartidos": []
  }
]
```

### **GET** `/api/profesores/{id}`
Obtiene un profesor por ID.

### **POST** `/api/profesores`
Crea un nuevo profesor.

**Request Body:**
```json
{
  "nombre": "María García",
  "email": "maria.garcia@umanizales.edu.co",
  "telefono": "3109876543",
  "especialidad": "Bases de Datos",
  "antiguedad": 10
}
```

### **PUT** `/api/profesores/{id}`
Actualiza un profesor existente.

### **DELETE** `/api/profesores/{id}`
Elimina un profesor.

---

## 📚 Cursos

### **GET** `/api/cursos`
Lista todos los cursos con sus profesores asignados.

**Respuesta exitosa (200):**
```json
[
  {
    "id": 1,
    "codigo": "PROG-301",
    "nombre": "Programación Avanzada",
    "creditos": 4,
    "modalidad": "PRESENCIAL",
    "profesor": {
      "id": 1,
      "nombre": "María García",
      "email": "maria.garcia@umanizales.edu.co",
      "especialidad": "Bases de Datos"
    },
    "asignaturas": [],
    "estudiantes": []
  }
]
```

### **GET** `/api/cursos/{id}`
Obtiene un curso por ID.

### **POST** `/api/cursos`
Crea un nuevo curso.

**Request Body:**
```json
{
  "codigo": "PROG-301",
  "nombre": "Programación Avanzada",
  "creditos": 4,
  "modalidad": "PRESENCIAL",
  "profesor": {
    "id": 1
  }
}
```

**Modalidades disponibles:** `PRESENCIAL`, `ONLINE`

### **POST** `/api/cursos/{cursoId}/profesor/{profesorId}`
Asigna un profesor a un curso existente.

### **PUT** `/api/cursos/{id}`
Actualiza un curso existente.

### **DELETE** `/api/cursos/{id}`
Elimina un curso.

---

## 📖 Asignaturas

### **GET** `/api/asignaturas`
Lista todas las asignaturas.

**Respuesta exitosa (200):**
```json
[
  {
    "id": 1,
    "codigo": "ASIG-001",
    "nombre": "Estructuras de Datos",
    "descripcion": "Estudio de estructuras de datos fundamentales",
    "creditos": 3
  }
]
```

### **GET** `/api/asignaturas/{id}`
Obtiene una asignatura por ID.

### **POST** `/api/asignaturas`
Crea una nueva asignatura.

**Request Body:**
```json
{
  "codigo": "ASIG-001",
  "nombre": "Estructuras de Datos",
  "descripcion": "Estudio de estructuras de datos fundamentales",
  "creditos": 3
}
```

### **PUT** `/api/asignaturas/{id}`
Actualiza una asignatura existente.

### **DELETE** `/api/asignaturas/{id}`
Elimina una asignatura.

---

## 📝 Matrículas

### **GET** `/api/matriculas`
Lista todas las matrículas con relaciones completas.

**Respuesta exitosa (200):**
```json
[
  {
    "id": 1,
    "estudiante": {
      "id": 1,
      "nombre": "Juan Pérez",
      "matricula": "20241001"
    },
    "curso": {
      "id": 1,
      "nombre": "Programación Avanzada",
      "codigo": "PROG-301"
    },
    "fechaMatricula": "2024-10-31",
    "estado": "ACTIVA",
    "periodo": "2024-2"
  }
]
```

### **GET** `/api/matriculas/{id}`
Obtiene una matrícula por ID.

### **GET** `/api/matriculas/estudiante/{estudianteId}`
Lista todas las matrículas de un estudiante específico.

### **GET** `/api/matriculas/curso/{cursoId}`
Lista todos los estudiantes matriculados en un curso.

### **POST** `/api/matriculas`
Crea una nueva matrícula (método completo).

**Request Body:**
```json
{
  "estudiante": {
    "id": 1
  },
  "curso": {
    "id": 1
  },
  "periodo": "2024-2",
  "estado": "ACTIVA"
}
```

### **POST** `/api/matriculas/matricular`
Matricula un estudiante en un curso (método simplificado).

**Request Body:**
```json
{
  "estudianteId": 1,
  "cursoId": 1,
  "periodo": "2024-2"
}
```

**Estados disponibles:** `ACTIVA`, `CANCELADA`, `FINALIZADA`

### **PUT** `/api/matriculas/{id}`
Actualiza una matrícula existente.

### **DELETE** `/api/matriculas/{id}`
Elimina una matrícula.

---

## 📦 Estructura del Proyecto

```
src/main/java/co/edu/umanizales/restaurante_api/
├── model/                      # Modelos de dominio
│   ├── Persona.java           # Clase abstracta base
│   ├── Estudiante.java        # Extiende Persona
│   ├── Profesor.java          # Extiende Persona
│   ├── Curso.java
│   ├── Asignatura.java
│   ├── Calificacion.java
│   ├── Matricula.java
│   └── Modalidad.java         # Enum
├── repository/                 # Capa de persistencia CSV
│   ├── CsvRepository.java     # Repositorio base genérico
│   ├── EstudianteRepository.java
│   ├── ProfesorRepository.java
│   ├── CursoRepository.java
│   ├── AsignaturaRepository.java
│   └── MatriculaRepository.java
├── service/                    # Lógica de negocio
│   ├── EstudianteService.java
│   ├── ProfesorService.java
│   ├── CursoService.java
│   ├── AsignaturaService.java
│   └── MatriculaService.java
└── controller/                 # Controladores REST
    ├── EstudianteController.java
    ├── ProfesorController.java
    ├── CursoController.java
    ├── AsignaturaController.java
    └── MatriculaController.java
```

---

## 🔧 Tecnologías Utilizadas

- **Java**: 23
- **Spring Boot**: 3.5.6
- **Lombok**: Reducción de boilerplate
- **Maven**: Gestión de dependencias
- **CSV**: Persistencia de datos

---

## 📮 Colección de Postman

Importa la colección en Postman:
- **Archivo**: `src/main/resources/Universidad_API.postman_collection.json`
- **Variable global**: `baseUrl = http://localhost:8080/api`

La colección incluye:
- ✅ Todas las operaciones CRUD para cada entidad
- ✅ Ejemplos de request bodies
- ✅ Endpoints especializados (matricular, asignar profesor)

---

## 🧪 Flujo de Prueba Sugerido

### **1. Crear un profesor**
```bash
POST /api/profesores
{
  "nombre": "María García",
  "email": "maria.garcia@umanizales.edu.co",
  "telefono": "3109876543",
  "especialidad": "Bases de Datos",
  "antiguedad": 10
}
```

### **2. Crear un curso con el profesor**
```bash
POST /api/cursos
{
  "codigo": "PROG-301",
  "nombre": "Programación Avanzada",
  "creditos": 4,
  "modalidad": "PRESENCIAL",
  "profesor": {"id": 1}
}
```

### **3. Crear un estudiante**
```bash
POST /api/estudiantes
{
  "nombre": "Juan Pérez",
  "email": "juan.perez@umanizales.edu.co",
  "telefono": "3001234567",
  "matricula": "20241001",
  "carrera": "Ingeniería de Sistemas",
  "semestre": 5
}
```

### **4. Matricular al estudiante en el curso**
```bash
POST /api/matriculas/matricular
{
  "estudianteId": 1,
  "cursoId": 1,
  "periodo": "2024-2"
}
```

### **5. Verificar la matrícula**
```bash
GET /api/matriculas/estudiante/1
```

---

## 📊 Códigos de Estado HTTP

| Código | Significado |
|--------|-------------|
| 200 | OK - Operación exitosa |
| 201 | Created - Recurso creado exitosamente |
| 204 | No Content - Eliminación exitosa |
| 400 | Bad Request - Datos inválidos |
| 404 | Not Found - Recurso no encontrado |
| 500 | Internal Server Error - Error del servidor |

---

## 🔍 Validaciones Implementadas

- ✅ Validación de IDs en relaciones (profesor, estudiante, curso)
- ✅ Prevención de duplicados en colecciones
- ✅ Auto-asignación de fechas actuales
- ✅ Estados por defecto (ACTIVA para matrículas)
- ✅ Resolución automática de relaciones en queries

---

## 💡 Características Avanzadas

### **Composición vs Agregación**
- **Composición**: Curso → Profesor (obligatorio)
- **Agregación**: Estudiante → Cursos, Curso → Estudiantes

### **Herencia**
- `Persona` (abstracta) → `Estudiante`, `Profesor`
- Método `getRol()` implementado en cada subclase

### **Persistencia Inteligente**
- IDs auto-incrementales persistidos
- Escape de caracteres especiales en CSV
- Relaciones resueltas automáticamente en servicios

---

## 📝 Notas Importantes

1. Los archivos CSV se crean automáticamente en el directorio `data/`
2. Las relaciones se guardan como IDs en CSV y se resuelven en la capa de servicio
3. El ID se genera automáticamente al crear entidades (no incluir en POST)
4. Los datos persisten entre reinicios de la aplicación

---

## 🎯 Próximas Mejoras

- [ ] Validaciones con Bean Validation (@NotNull, @Email, etc.)
- [ ] DTOs para separar capa de presentación
- [ ] Manejo de excepciones global con @ControllerAdvice
- [ ] Documentación Swagger/OpenAPI
- [ ] Paginación para listados grandes
- [ ] Filtros y búsquedas avanzadas
- [ ] Sistema de calificaciones completo
- [ ] Autenticación y autorización
