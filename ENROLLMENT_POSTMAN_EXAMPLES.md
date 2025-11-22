# Ejemplos de Enrollment para Postman

## Estructura de la clase Enrollment

La clase `Enrollment` tiene los siguientes atributos:

- **id** (long): Identificador único (se genera automáticamente si no lo proporcionas)
- **student** (Student): Objeto Student con al menos el ID
- **course** (Course): Objeto Course con al menos el ID
- **enrollmentDate** (LocalDate): Fecha de inscripción (formato: YYYY-MM-DD)
- **status** (String): Estado del enrollment (ACTIVE, CANCELLED, COMPLETED)
- **period** (String): Período académico (ej: 2024-1, 2024-2)

---

## Ejemplo 1: Enrollment Completo (con ID)

**Endpoint:** `POST /api/tuition`

```json
{
  "id": 1,
  "student": {
    "id": 2
  },
  "course": {
    "id": 1
  },
  "enrollmentDate": "2024-11-21",
  "status": "ACTIVE",
  "period": "2024-1"
}
```

---

## Ejemplo 2: Enrollment sin ID (se genera automáticamente)

**Endpoint:** `POST /api/tuition`

```json
{
  "student": {
    "id": 3
  },
  "course": {
    "id": 2
  },
  "enrollmentDate": "2024-11-21",
  "status": "ACTIVE",
  "period": "2024-1"
}
```

---

## Ejemplo 3: Enrollment Mínimo (solo relaciones requeridas)

**Endpoint:** `POST /api/tuition`

```json
{
  "student": {
    "id": 2
  },
  "course": {
    "id": 1
  }
}
```

**Nota:** Los campos `enrollmentDate` (se asigna la fecha actual), `status` (se asigna "ACTIVE") y `period` se completarán automáticamente.

---

## Ejemplo 4: Enrollment con Período Diferente

**Endpoint:** `POST /api/tuition`

```json
{
  "student": {
    "id": 4
  },
  "course": {
    "id": 2
  },
  "enrollmentDate": "2024-11-15",
  "status": "ACTIVE",
  "period": "2024-2"
}
```

---

## Ejemplo 5: Enrollment Cancelado

**Endpoint:** `POST /api/tuition`

```json
{
  "student": {
    "id": 5
  },
  "course": {
    "id": 1
  },
  "enrollmentDate": "2024-10-01",
  "status": "CANCELLED",
  "period": "2024-1"
}
```

---

## Ejemplo 6: Enrollment Completado

**Endpoint:** `POST /api/tuition`

```json
{
  "student": {
    "id": 6
  },
  "course": {
    "id": 2
  },
  "enrollmentDate": "2024-09-01",
  "status": "COMPLETED",
  "period": "2024-1"
}
```

---

## Otros Endpoints Disponibles

### GET - Obtener todos los enrollments
```
GET /api/tuition
```

### GET - Obtener enrollment por ID
```
GET /api/tuition/{id}
```

### GET - Obtener enrollments de un estudiante
```
GET /api/tuition/student/{studentId}
```

### GET - Obtener enrollments de un curso
```
GET /api/tuition/course/{courseId}
```

### PUT - Actualizar enrollment
```
PUT /api/tuition/{id}
```

Body (mismo formato que POST):
```json
{
  "student": {
    "id": 2
  },
  "course": {
    "id": 1
  },
  "enrollmentDate": "2024-11-21",
  "status": "ACTIVE",
  "period": "2024-1"
}
```

### DELETE - Eliminar enrollment
```
DELETE /api/tuition/{id}
```

---

## Validaciones Importantes

⚠️ **El student y course DEBEN existir en la base de datos**

Si intentas crear un enrollment con un student o course que no existe, recibirás un error:

```json
{
  "error": "Student not found"
}
```

o

```json
{
  "error": "Course not found"
}
```

---

## Estudiantes Disponibles (IDs)

- ID 2: Juan Pérez
- ID 3: Sebastian Jaramillo
- ID 4: Karen Lopez
- ID 5: Valentina Ochoa
- ID 6: Salome Ocampo
- ID 7: Juan Perez

---

## Cursos Disponibles (IDs)

- ID 1: MATH101 - Curso de Matematicas 101
- ID 2: Englesh204 - Curso de ingles 204
