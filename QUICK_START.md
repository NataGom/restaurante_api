# 🚀 Guía de Inicio Rápido

## Paso 1: Iniciar la Aplicación

```bash
.\mvnw.cmd spring-boot:run
```

Espera a ver este mensaje:
```
Started RestauranteApiApplication in X.XXX seconds
```

## Paso 2: Probar con Postman

### **Importar Colección**
1. Abre Postman
2. Click en **Import**
3. Selecciona el archivo: `src/main/resources/Universidad_API.postman_collection.json`
4. La colección "Universidad API - Plataforma de Cursos" aparecerá en tu sidebar

### **Configurar Variable**
La variable `baseUrl` ya está configurada: `http://localhost:8080/api`

## Paso 3: Flujo de Prueba Completo

### **1️⃣ Crear Profesor**

**Request:**
```
POST {{baseUrl}}/profesores
```

**Body:**
```json
{
  "nombre": "María García",
  "email": "maria.garcia@umanizales.edu.co",
  "telefono": "3109876543",
  "especialidad": "Bases de Datos",
  "antiguedad": 10
}
```

**Respuesta esperada (201):**
```json
{
  "id": 1,
  "nombre": "María García",
  ...
}
```

---

### **2️⃣ Crear Curso con Profesor**

**Request:**
```
POST {{baseUrl}}/cursos
```

**Body:**
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

**Respuesta esperada (201):**
```json
{
  "id": 1,
  "codigo": "PROG-301",
  "nombre": "Programación Avanzada",
  "creditos": 4,
  "modalidad": "PRESENCIAL",
  "profesor": {
    "id": 1,
    "nombre": "María García",
    ...
  }
}
```

---

### **3️⃣ Crear Estudiante**

**Request:**
```
POST {{baseUrl}}/estudiantes
```

**Body:**
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

**Respuesta esperada (201):**
```json
{
  "id": 1,
  "nombre": "Juan Pérez",
  ...
}
```

---

### **4️⃣ Matricular Estudiante**

**Request:**
```
POST {{baseUrl}}/matriculas/matricular
```

**Body:**
```json
{
  "estudianteId": 1,
  "cursoId": 1,
  "periodo": "2024-2"
}
```

**Respuesta esperada (201):**
```json
{
  "id": 1,
  "estudiante": {
    "id": 1,
    "nombre": "Juan Pérez"
  },
  "curso": {
    "id": 1,
    "nombre": "Programación Avanzada"
  },
  "fechaMatricula": "2024-10-31",
  "estado": "ACTIVA",
  "periodo": "2024-2"
}
```

---

### **5️⃣ Consultar Matrículas del Estudiante**

**Request:**
```
GET {{baseUrl}}/matriculas/estudiante/1
```

**Respuesta esperada (200):**
```json
[
  {
    "id": 1,
    "estudiante": {...},
    "curso": {...},
    "estado": "ACTIVA"
  }
]
```

---

### **6️⃣ Listar Todos los Cursos**

**Request:**
```
GET {{baseUrl}}/cursos
```

---

### **7️⃣ Crear Asignatura**

**Request:**
```
POST {{baseUrl}}/asignaturas
```

**Body:**
```json
{
  "codigo": "ASIG-001",
  "nombre": "Estructuras de Datos",
  "descripcion": "Estudio de estructuras de datos fundamentales",
  "creditos": 3
}
```

---

## Paso 4: Verificar Archivos CSV

Los datos se guardan automáticamente en archivos CSV:

```
data/
├── estudiantes.csv    ✅ Contiene a Juan Pérez
├── profesores.csv     ✅ Contiene a María García
├── cursos.csv         ✅ Contiene Programación Avanzada
├── asignaturas.csv    ✅ Contiene Estructuras de Datos
└── matriculas.csv     ✅ Contiene la matrícula de Juan
```

Abre cualquier archivo CSV con un editor de texto o Excel para ver los datos.

---

## Paso 5: Probar Actualización

### **Actualizar Semestre del Estudiante**

**Request:**
```
PUT {{baseUrl}}/estudiantes/1
```

**Body:**
```json
{
  "nombre": "Juan Pérez",
  "email": "juan.perez@umanizales.edu.co",
  "telefono": "3001234567",
  "matricula": "20241001",
  "carrera": "Ingeniería de Sistemas",
  "semestre": 6
}
```

Verifica que el semestre cambió a 6.

---

## Paso 6: Probar Eliminación

### **Eliminar Asignatura**

**Request:**
```
DELETE {{baseUrl}}/asignaturas/1
```

**Respuesta esperada:** `204 No Content`

---

## 🎯 Endpoints Clave de la Colección

La colección de Postman incluye **28 requests** organizados en 5 carpetas:

1. **Estudiantes** (5 requests)
   - GET All, GET by ID, POST, PUT, DELETE

2. **Profesores** (5 requests)
   - GET All, GET by ID, POST, PUT, DELETE

3. **Cursos** (6 requests)
   - GET All, GET by ID, POST, PUT, DELETE
   - **POST** Asignar profesor a curso

4. **Asignaturas** (5 requests)
   - GET All, GET by ID, POST, PUT, DELETE

5. **Matrículas** (8 requests)
   - GET All, GET by ID, POST, PUT, DELETE
   - **GET** Matrículas por estudiante
   - **GET** Matrículas por curso
   - **POST** Matricular (simplificado)

---

## 🔍 Códigos de Estado Esperados

| Operación | Código | Significado |
|-----------|--------|-------------|
| GET exitoso | 200 | OK |
| POST exitoso | 201 | Created |
| PUT exitoso | 200 | OK |
| DELETE exitoso | 204 | No Content |
| Recurso no encontrado | 404 | Not Found |
| Datos inválidos | 400 | Bad Request |

---

## ⚡ Pruebas Rápidas con cURL

Si prefieres cURL en lugar de Postman:

### **Listar Estudiantes**
```bash
curl http://localhost:8080/api/estudiantes
```

### **Crear Profesor**
```bash
curl -X POST http://localhost:8080/api/profesores \
  -H "Content-Type: application/json" \
  -d "{\"nombre\":\"Carlos Ruiz\",\"email\":\"carlos@umanizales.edu.co\",\"telefono\":\"3201234567\",\"especialidad\":\"Algoritmos\",\"antiguedad\":5}"
```

### **Obtener Curso por ID**
```bash
curl http://localhost:8080/api/cursos/1
```

---

## 🛠️ Solución de Problemas

### **Error: "Address already in use"**
El puerto 8080 está ocupado. Cambia el puerto en `application.properties`:
```properties
server.port=8081
```

### **Error: "Profesor no encontrado"**
Asegúrate de crear el profesor antes de crear el curso.

### **Error 404 en todos los endpoints**
Verifica que la aplicación esté corriendo y usa la URL correcta:
`http://localhost:8080/api/...`

### **Los datos no persisten**
Verifica que el directorio `data/` se haya creado y contenga archivos CSV.

---

## 📊 Datos de Prueba Adicionales

### **Segundo Estudiante**
```json
{
  "nombre": "María López",
  "email": "maria.lopez@umanizales.edu.co",
  "telefono": "3009876543",
  "matricula": "20241002",
  "carrera": "Ingeniería Civil",
  "semestre": 3
}
```

### **Segundo Profesor**
```json
{
  "nombre": "Carlos Ruiz",
  "email": "carlos.ruiz@umanizales.edu.co",
  "telefono": "3201234567",
  "especialidad": "Algoritmos",
  "antiguedad": 15
}
```

### **Segundo Curso (ONLINE)**
```json
{
  "codigo": "BD-201",
  "nombre": "Bases de Datos",
  "creditos": 3,
  "modalidad": "ONLINE",
  "profesor": {"id": 1}
}
```

---

## ✅ Checklist de Pruebas

- [ ] ✅ Crear profesor
- [ ] ✅ Crear curso con profesor
- [ ] ✅ Crear estudiante
- [ ] ✅ Matricular estudiante en curso
- [ ] ✅ Listar matrículas del estudiante
- [ ] ✅ Crear asignatura
- [ ] ✅ Actualizar información del estudiante
- [ ] ✅ Listar todos los cursos
- [ ] ✅ Asignar profesor a curso existente
- [ ] ✅ Ver estudiantes matriculados en un curso
- [ ] ✅ Verificar archivos CSV en directorio `data/`
- [ ] ✅ Reiniciar aplicación y verificar persistencia

---

## 🎉 ¡Listo!

Tu API REST está completamente funcional con:
- ✅ Persistencia en CSV
- ✅ CRUD completo para todas las entidades
- ✅ Relaciones entre entidades funcionando
- ✅ Endpoints listos para Postman
- ✅ Datos que persisten entre reinicios

**¡Disfruta probando tu API!** 🚀
