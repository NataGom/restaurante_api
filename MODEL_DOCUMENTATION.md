# Modelo de la Plataforma Universitaria de Cursos en Línea

## Descripción General
Sistema para gestionar estudiantes, profesores, cursos, asignaturas, calificaciones y matrículas.

## Estructura de Clases

### 1. **Persona** (Clase Abstracta)
- **Paquete**: `co.edu.umanizales.restaurante_api.model`
- **Atributos**:
  - `id`: Long
  - `nombre`: String
  - `email`: String
  - `telefono`: String
- **Métodos**:
  - `getRol()`: String (abstracto)
  - `toString()`: String (override)
- **Propósito**: Superclase abstracta para Estudiante y Profesor

---

### 2. **Estudiante** (extends Persona)
- **Atributos adicionales**:
  - `matricula`: String
  - `carrera`: String
  - `semestre`: int
  - `cursos`: List<Curso> (Agregación)
  - `calificaciones`: List<Calificacion>
- **Métodos**:
  - `getRol()`: Retorna "ESTUDIANTE"
  - `inscribirse(Curso c)`: Inscribe al estudiante en un curso
  - `consultarPromedio()`: Calcula el promedio de calificaciones
  - `agregarCalificacion(Calificacion c)`: Agrega una calificación
- **Relaciones**:
  - Agregación con Curso (puede estar inscrito en varios cursos)

---

### 3. **Profesor** (extends Persona)
- **Atributos adicionales**:
  - `especialidad`: String
  - `antiguedad`: int (años)
  - `cursosImpartidos`: List<Curso>
- **Métodos**:
  - `getRol()`: Retorna "PROFESOR"
  - `asignarCurso(Curso c)`: Asigna un curso al profesor
  - `calificar(Estudiante e, Asignatura a, double nota)`: Califica a un estudiante
- **Relaciones**:
  - Composición con Curso (un curso requiere un profesor)
- **Validaciones**:
  - Las notas deben estar entre 0.0 y 5.0

---

### 4. **Curso**
- **Atributos**:
  - `id`: Long
  - `codigo`: String
  - `nombre`: String
  - `creditos`: int
  - `modalidad`: Modalidad (enum)
  - `profesor`: Profesor (Composición)
  - `asignaturas`: List<Asignatura> (Agregación)
  - `estudiantes`: List<Estudiante> (Agregación)
- **Métodos**:
  - `agregarAsignatura(Asignatura a)`: Agrega una asignatura al curso
  - `listarEstudiantes()`: Lista todos los estudiantes inscritos
  - `agregarEstudiante(Estudiante e)`: Agrega un estudiante al curso
- **Relaciones**:
  - Composición con Profesor (obligatoria)
  - Agregación con Asignatura y Estudiante

---

### 5. **Asignatura**
- **Atributos**:
  - `id`: Long
  - `codigo`: String
  - `nombre`: String
  - `descripcion`: String
  - `creditos`: int
- **Propósito**: Representa una asignatura que puede ser parte de un curso

---

### 6. **Calificacion**
- **Atributos**:
  - `id`: Long
  - `estudiante`: Estudiante
  - `asignatura`: Asignatura
  - `curso`: Curso
  - `nota`: double
  - `fecha`: LocalDate
  - `profesorCalificador`: Profesor
- **Propósito**: Registra las calificaciones de los estudiantes

---

### 7. **Matricula**
- **Atributos**:
  - `id`: Long
  - `estudiante`: Estudiante
  - `curso`: Curso
  - `fechaMatricula`: LocalDate
  - `estado`: String (ACTIVA, CANCELADA, FINALIZADA)
  - `periodo`: String (ej: 2024-1, 2024-2)
- **Propósito**: Gestiona las matrículas de estudiantes en cursos

---

### 8. **Modalidad** (Enum)
- **Valores**:
  - `PRESENCIAL`
  - `ONLINE`
- **Propósito**: Define el tipo de modalidad de un curso

---

## Relaciones entre Clases

### Herencia
- `Estudiante` extends `Persona`
- `Profesor` extends `Persona`

### Composición (relación fuerte)
- `Curso` → `Profesor` (un curso siempre requiere un profesor)

### Agregación (relación débil)
- `Estudiante` → `List<Curso>` (un estudiante puede estar en varios cursos)
- `Profesor` → `List<Curso>` (un profesor puede impartir varios cursos)
- `Curso` → `List<Asignatura>` (un curso puede tener varias asignaturas)
- `Curso` → `List<Estudiante>` (un curso puede tener varios estudiantes)

---

## Anotaciones Lombok Utilizadas

- **@Data**: Genera getters, setters, toString, equals y hashCode
- **@NoArgsConstructor**: Constructor sin argumentos
- **@AllArgsConstructor**: Constructor con todos los argumentos
- **@EqualsAndHashCode(callSuper = true)**: Para clases heredadas, incluye los campos de la superclase

---

## Tecnologías
- **Java**: 23
- **Spring Boot**: 3.5.6
- **Lombok**: Reducción de código boilerplate
- **Maven**: Gestión de dependencias

---

## Próximos Pasos Sugeridos

1. Crear repositorios (JPA) para persistencia
2. Implementar servicios para lógica de negocio
3. Crear controladores REST para exponer endpoints
4. Agregar validaciones con Bean Validation
5. Implementar DTOs para transferencia de datos
6. Configurar base de datos (H2, PostgreSQL, MySQL)
7. Agregar documentación con Swagger/OpenAPI
8. Implementar pruebas unitarias y de integración
