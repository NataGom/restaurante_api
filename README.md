# 🎓 Plataforma Universitaria de Cursos en Línea - API REST

API REST desarrollada en **Java 23** con **Spring Boot 3.5.6** para gestionar una plataforma universitaria completa con persistencia en archivos CSV.

## 🚀 Inicio Rápido

### **1. Compilar el proyecto**
```bash
.\mvnw.cmd clean compile
```

### **2. Ejecutar la aplicación**
```bash
.\mvnw.cmd spring-boot:run
```

### **3. Probar la API**
La API estará disponible en: `http://localhost:8080/api`

Importa la colección de Postman: `src/main/resources/Universidad_API.postman_collection.json`

## 📋 Características Principales

✅ **Gestión completa de estudiantes, profesores, cursos, asignaturas y matrículas**
✅ **Persistencia en CSV** - Los datos se guardan en archivos CSV automáticamente
✅ **API RESTful** - Arquitectura REST con operaciones CRUD completas
✅ **Herencia y Polimorfismo** - Modelo orientado a objetos con clase abstracta `Persona`
✅ **Relaciones entre entidades** - Composición y agregación implementadas
✅ **IDs auto-generados** - Sistema de generación automática de IDs únicos
✅ **Validación de relaciones** - Validación de integridad referencial

## 🗂️ Modelo de Datos

### **Jerarquía de Clases**
```
Persona (abstracta)
├── Estudiante
│   ├── matricula, carrera, semestre
│   └── Relación: N:N con Curso
└── Profesor
    ├── especialidad, antiguedad
    └── Relación: 1:N con Curso (composición)

Curso
├── codigo, nombre, creditos, modalidad
├── Relación: N:1 con Profesor (obligatorio)
├── Relación: N:N con Estudiante
└── Relación: 1:N con Asignatura

Asignatura
└── codigo, nombre, descripcion, creditos

Matricula
├── Relación: N:1 con Estudiante
├── Relación: N:1 con Curso
└── estado, periodo, fechaMatricula

Modalidad (Enum)
└── PRESENCIAL, ONLINE
```

## 📡 Endpoints Principales

| Entidad | GET All | GET by ID | POST | PUT | DELETE |
|---------|---------|-----------|------|-----|--------|
| **Estudiantes** | `/api/estudiantes` | `/api/estudiantes/{id}` | `/api/estudiantes` | `/api/estudiantes/{id}` | `/api/estudiantes/{id}` |
| **Profesores** | `/api/profesores` | `/api/profesores/{id}` | `/api/profesores` | `/api/profesores/{id}` | `/api/profesores/{id}` |
| **Cursos** | `/api/cursos` | `/api/cursos/{id}` | `/api/cursos` | `/api/cursos/{id}` | `/api/cursos/{id}` |
| **Asignaturas** | `/api/asignaturas` | `/api/asignaturas/{id}` | `/api/asignaturas` | `/api/asignaturas/{id}` | `/api/asignaturas/{id}` |
| **Matrículas** | `/api/matriculas` | `/api/matriculas/{id}` | `/api/matriculas` | `/api/matriculas/{id}` | `/api/matriculas/{id}` |

### **Endpoints Especiales**

#### **Cursos**
- `POST /api/cursos/{cursoId}/profesor/{profesorId}` - Asignar profesor a curso

#### **Matrículas**
- `GET /api/matriculas/estudiante/{estudianteId}` - Matrículas por estudiante
- `GET /api/matriculas/curso/{cursoId}` - Estudiantes matriculados en un curso
- `POST /api/matriculas/matricular` - Matricular estudiante (simplificado)

## 🗃️ Persistencia de Datos

Los datos se almacenan en archivos CSV en el directorio `data/`:

```
data/
├── estudiantes.csv      # id,nombre,email,telefono,matricula,carrera,semestre
├── profesores.csv       # id,nombre,email,telefono,especialidad,antiguedad
├── cursos.csv          # id,codigo,nombre,creditos,modalidad,profesorId
├── asignaturas.csv     # id,codigo,nombre,descripcion,creditos
└── matriculas.csv      # id,estudianteId,cursoId,fechaMatricula,estado,periodo
```

**Características:**
- ✅ Archivos creados automáticamente con headers
- ✅ IDs únicos auto-generados
- ✅ Escape automático de caracteres especiales
- ✅ Persistencia entre reinicios

## 📦 Arquitectura del Proyecto

```
📁 src/main/java/co/edu/umanizales/restaurante_api/
│
├── 📁 model/                    # Modelos de dominio
│   ├── Persona.java            # Clase abstracta base
│   ├── Estudiante.java         # Extiende Persona
│   ├── Profesor.java           # Extiende Persona
│   ├── Curso.java
│   ├── Asignatura.java
│   ├── Calificacion.java
│   ├── Matricula.java
│   └── Modalidad.java          # Enum (PRESENCIAL, ONLINE)
│
├── 📁 repository/               # Persistencia CSV
│   ├── CsvRepository.java      # Repositorio base genérico
│   ├── EstudianteRepository.java
│   ├── ProfesorRepository.java
│   ├── CursoRepository.java
│   ├── AsignaturaRepository.java
│   └── MatriculaRepository.java
│
├── 📁 service/                  # Lógica de negocio
│   ├── EstudianteService.java
│   ├── ProfesorService.java
│   ├── CursoService.java
│   ├── AsignaturaService.java
│   └── MatriculaService.java
│
└── 📁 controller/               # API REST
    ├── EstudianteController.java
    ├── ProfesorController.java
    ├── CursoController.java
    ├── AsignaturaController.java
    └── MatriculaController.java
```

## 🧪 Ejemplo de Uso con Postman

### **1. Crear un Profesor**
```http
POST http://localhost:8080/api/profesores
Content-Type: application/json

{
  "nombre": "María García",
  "email": "maria.garcia@umanizales.edu.co",
  "telefono": "3109876543",
  "especialidad": "Bases de Datos",
  "antiguedad": 10
}
```

**Respuesta (201 Created):**
```json
{
  "id": 1,
  "nombre": "María García",
  "email": "maria.garcia@umanizales.edu.co",
  "telefono": "3109876543",
  "especialidad": "Bases de Datos",
  "antiguedad": 10,
  "cursosImpartidos": []
}
```

### **2. Crear un Curso**
```http
POST http://localhost:8080/api/cursos
Content-Type: application/json

{
  "codigo": "PROG-301",
  "nombre": "Programación Avanzada",
  "creditos": 4,
  "modalidad": "PRESENCIAL",
  "profesor": {"id": 1}
}
```

### **3. Crear un Estudiante**
```http
POST http://localhost:8080/api/estudiantes
Content-Type: application/json

{
  "nombre": "Juan Pérez",
  "email": "juan.perez@umanizales.edu.co",
  "telefono": "3001234567",
  "matricula": "20241001",
  "carrera": "Ingeniería de Sistemas",
  "semestre": 5
}
```

### **4. Matricular Estudiante**
```http
POST http://localhost:8080/api/matriculas/matricular
Content-Type: application/json

{
  "estudianteId": 1,
  "cursoId": 1,
  "periodo": "2024-2"
}
```

### **5. Consultar Matrículas del Estudiante**
```http
GET http://localhost:8080/api/matriculas/estudiante/1
```

**Respuesta:**
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
      "codigo": "PROG-301",
      "nombre": "Programación Avanzada"
    },
    "fechaMatricula": "2024-10-31",
    "estado": "ACTIVA",
    "periodo": "2024-2"
  }
]
```

## 🔧 Tecnologías

| Tecnología | Versión | Propósito |
|------------|---------|-----------|
| Java | 23 | Lenguaje de programación |
| Spring Boot | 3.5.6 | Framework web |
| Lombok | Latest | Reducción de boilerplate |
| Maven | Latest | Gestión de dependencias |

## 📚 Documentación Adicional

- **[API_DOCUMENTATION.md](API_DOCUMENTATION.md)** - Documentación completa de endpoints
- **[MODEL_DOCUMENTATION.md](MODEL_DOCUMENTATION.md)** - Diagramas y relaciones del modelo
- **[README_CSV_PERSISTENCE.md](README_CSV_PERSISTENCE.md)** - Sistema de persistencia CSV

## 📮 Colección de Postman

Importa la colección completa con todos los endpoints:

**Archivo**: `src/main/resources/Universidad_API.postman_collection.json`

**Variable de entorno**:
- `baseUrl`: `http://localhost:8080/api`

## 🎯 Características del Modelo OOP

### **Herencia**
- `Persona` es una clase abstracta con método `getRol()`
- `Estudiante` y `Profesor` extienden `Persona`
- Implementación de `toString()` en clase base

### **Composición**
- **Curso → Profesor**: Un curso siempre requiere un profesor (obligatorio)

### **Agregación**
- **Estudiante → Cursos**: Un estudiante puede estar en varios cursos
- **Curso → Estudiantes**: Un curso puede tener varios estudiantes
- **Curso → Asignaturas**: Un curso puede tener varias asignaturas

### **Encapsulación**
- Uso de Lombok para getters/setters automáticos
- Validaciones en capa de servicio
- Lógica de negocio encapsulada

## 🔍 Validaciones Implementadas

✅ Validación de existencia de profesor al crear curso
✅ Validación de existencia de estudiante y curso al matricular
✅ Prevención de IDs duplicados
✅ Asignación automática de fechas
✅ Estados por defecto (ACTIVA)
✅ Resolución automática de relaciones

## 💡 Casos de Uso Implementados

### **UC-01: Inscribirse a Curso**
```java
// Método en Estudiante
public void inscribirse(Curso curso) {
    if (!cursos.contains(curso)) {
        cursos.add(curso);
        curso.agregarEstudiante(this);
    }
}
```

### **UC-02: Asignar Curso a Profesor**
```java
// Método en Profesor
public void asignarCurso(Curso curso) {
    if (!cursosImpartidos.contains(curso)) {
        cursosImpartidos.add(curso);
        curso.setProfesor(this);
    }
}
```

### **UC-03: Calificar Estudiante**
```java
// Método en Profesor
public Calificacion calificar(Estudiante estudiante, Asignatura asignatura, double nota) {
    // Validación de nota (0.0 - 5.0)
    // Creación de calificación
    // Registro en historial del estudiante
}
```

### **UC-04: Consultar Promedio**
```java
// Método en Estudiante
public double consultarPromedio() {
    return calificaciones.stream()
        .mapToDouble(Calificacion::getNota)
        .average()
        .orElse(0.0);
}
```

## 🚨 Códigos de Respuesta HTTP

| Código | Significado | Uso |
|--------|-------------|-----|
| 200 | OK | Operación exitosa (GET, PUT) |
| 201 | Created | Recurso creado (POST) |
| 204 | No Content | Eliminación exitosa (DELETE) |
| 400 | Bad Request | Datos inválidos o relación no encontrada |
| 404 | Not Found | Recurso no encontrado |

## 📊 Estructura de Archivos del Proyecto

```
restaurante_api/
├── src/
│   ├── main/
│   │   ├── java/co/edu/umanizales/restaurante_api/
│   │   │   ├── controller/          # Controladores REST
│   │   │   ├── model/               # Modelos de dominio
│   │   │   ├── repository/          # Repositorios CSV
│   │   │   ├── service/             # Servicios
│   │   │   └── RestauranteApiApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── Universidad_API.postman_collection.json
├── data/                            # Archivos CSV (auto-generados)
├── pom.xml
├── README.md                        # Este archivo
├── API_DOCUMENTATION.md
├── MODEL_DOCUMENTATION.md
└── README_CSV_PERSISTENCE.md
```

## 🔄 Flujo de Datos

```
Cliente (Postman)
    ↓
Controller (REST API)
    ↓
Service (Lógica de negocio + resolución de relaciones)
    ↓
Repository (Persistencia CSV)
    ↓
CSV Files (data/*.csv)
```

## ⚠️ Consideraciones Importantes

1. **Los archivos CSV se crean automáticamente** en el directorio `data/` al iniciar la aplicación
2. **No incluyas IDs en POST** - se generan automáticamente
3. **Las relaciones se guardan como IDs** en CSV y se resuelven en servicios
4. **Los datos persisten** entre reinicios de la aplicación
5. **Para migrar a BD relacional** - solo cambia los repositorios, los servicios no cambian

## 🎓 Proyecto Educativo

Este proyecto fue desarrollado como parte del curso de Programación 3, implementando:
- ✅ Programación Orientada a Objetos
- ✅ Herencia y Polimorfismo
- ✅ Composición y Agregación
- ✅ API RESTful
- ✅ Arquitectura en capas (MVC)
- ✅ Persistencia de datos
- ✅ Inyección de dependencias

## 📧 Contacto

Universidad de Manizales - Facultad de Ingeniería
Curso: Programación 3
Año: 2024

---

**¡Listo para usar!** 🚀

Ejecuta `.\mvnw.cmd spring-boot:run` e importa la colección de Postman para comenzar a probar la API.
