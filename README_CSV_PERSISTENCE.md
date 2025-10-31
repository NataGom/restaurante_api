# Sistema de Persistencia CSV

## 🎯 Descripción General

El sistema utiliza archivos CSV para almacenar datos de forma persistente. Cada entidad tiene su propio archivo CSV con un repositorio dedicado que hereda de `CsvRepository<T>`.

## 📂 Archivos CSV Generados

```
data/
├── estudiantes.csv      # Datos de estudiantes
├── profesores.csv       # Datos de profesores
├── cursos.csv          # Datos de cursos
├── asignaturas.csv     # Datos de asignaturas
└── matriculas.csv      # Datos de matrículas
```

## 🏗️ Arquitectura del Sistema

### **Clase Base: CsvRepository<T>**

Repositorio genérico que proporciona operaciones CRUD para cualquier entidad:

#### **Métodos Públicos**
- `save(T entity)` - Crea o actualiza una entidad
- `findAll()` - Lista todas las entidades
- `findById(Long id)` - Busca por ID
- `deleteById(Long id)` - Elimina por ID

#### **Métodos Abstractos** (implementados por subclases)
- `getHeaders()` - Define los headers del CSV
- `toCsv(T entity)` - Convierte entidad a línea CSV
- `fromCsv(String csvLine)` - Convierte línea CSV a entidad
- `getId(T entity)` - Obtiene el ID de la entidad
- `setId(T entity, Long id)` - Establece el ID de la entidad

#### **Características**
✅ **Auto-generación de IDs**: IDs únicos con `AtomicLong`
✅ **Escape de caracteres**: Manejo automático de comas y comillas
✅ **Inicialización automática**: Crea archivos con headers si no existen
✅ **Thread-safe**: Generación de IDs segura para concurrencia

## 📋 Estructura de Archivos CSV

### **estudiantes.csv**
```csv
id,nombre,email,telefono,matricula,carrera,semestre
1,Juan Pérez,juan.perez@umanizales.edu.co,3001234567,20241001,Ingeniería de Sistemas,5
2,María López,maria.lopez@umanizales.edu.co,3009876543,20241002,Ingeniería Civil,3
```

### **profesores.csv**
```csv
id,nombre,email,telefono,especialidad,antiguedad
1,María García,maria.garcia@umanizales.edu.co,3109876543,Bases de Datos,10
2,Carlos Ruiz,carlos.ruiz@umanizales.edu.co,3201234567,Programación,15
```

### **cursos.csv**
```csv
id,codigo,nombre,creditos,modalidad,profesorId
1,PROG-301,Programación Avanzada,4,PRESENCIAL,1
2,BD-201,Bases de Datos,3,ONLINE,1
```

### **asignaturas.csv**
```csv
id,codigo,nombre,descripcion,creditos
1,ASIG-001,Estructuras de Datos,"Estudio de estructuras de datos fundamentales",3
2,ASIG-002,Algoritmos,"Análisis de algoritmos y complejidad",4
```

### **matriculas.csv**
```csv
id,estudianteId,cursoId,fechaMatricula,estado,periodo
1,1,1,2024-10-31,ACTIVA,2024-2
2,1,2,2024-10-31,ACTIVA,2024-2
3,2,1,2024-10-30,ACTIVA,2024-2
```

## 🔧 Implementación de Repositorios

### **Ejemplo: EstudianteRepository**

```java
@Repository
public class EstudianteRepository extends CsvRepository<Estudiante> {
    
    private static final String FILE_PATH = "data/estudiantes.csv";

    public EstudianteRepository() {
        super(FILE_PATH);
    }

    @Override
    protected String getHeaders() {
        return "id,nombre,email,telefono,matricula,carrera,semestre";
    }

    @Override
    protected String toCsv(Estudiante estudiante) {
        return String.join(",",
            String.valueOf(estudiante.getId()),
            escapeCsv(estudiante.getNombre()),
            escapeCsv(estudiante.getEmail()),
            escapeCsv(estudiante.getTelefono()),
            escapeCsv(estudiante.getMatricula()),
            escapeCsv(estudiante.getCarrera()),
            String.valueOf(estudiante.getSemestre())
        );
    }

    @Override
    protected Estudiante fromCsv(String csvLine) {
        String[] fields = csvLine.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        
        Estudiante estudiante = new Estudiante();
        estudiante.setId(Long.parseLong(fields[0]));
        estudiante.setNombre(unescapeCsv(fields[1]));
        estudiante.setEmail(unescapeCsv(fields[2]));
        estudiante.setTelefono(unescapeCsv(fields[3]));
        estudiante.setMatricula(unescapeCsv(fields[4]));
        estudiante.setCarrera(unescapeCsv(fields[5]));
        estudiante.setSemestre(Integer.parseInt(fields[6]));
        
        return estudiante;
    }

    @Override
    protected Long getId(Estudiante estudiante) {
        return estudiante.getId();
    }

    @Override
    protected void setId(Estudiante estudiante, Long id) {
        estudiante.setId(id);
    }
}
```

## 🔗 Manejo de Relaciones

### **Problema: Referencias entre entidades**
Las relaciones (como `Curso → Profesor`) no se pueden almacenar directamente en CSV.

### **Solución: Almacenar IDs y resolver en servicios**

#### **En el Repositorio (CSV)**
```csv
# cursos.csv
id,codigo,nombre,creditos,modalidad,profesorId
1,PROG-301,Programación Avanzada,4,PRESENCIAL,1
```

Solo se guarda el `profesorId` (referencia).

#### **En el Servicio**
```java
@Service
public class CursoService {
    private final CursoRepository cursoRepository;
    private final ProfesorService profesorService;

    public Curso findById(Long id) {
        Optional<Curso> cursoOpt = cursoRepository.findById(id);
        cursoOpt.ifPresent(curso -> {
            // Resolver la relación: cargar el profesor completo
            if (curso.getProfesor() != null && curso.getProfesor().getId() != null) {
                profesorService.findById(curso.getProfesor().getId())
                    .ifPresent(curso::setProfesor);
            }
        });
        return cursoOpt;
    }
}
```

El servicio resuelve las relaciones cargando las entidades relacionadas.

## 🛡️ Escape de Caracteres Especiales

### **Problema**
Los valores pueden contener comas o comillas que rompen el formato CSV:
```
"García, María" → García, María  (2 campos en lugar de 1)
```

### **Solución: Métodos de escape**

#### **escapeCsv(String field)**
```java
protected String escapeCsv(String field) {
    if (field == null) return "";
    if (field.contains(",") || field.contains("\"") || field.contains("\n")) {
        return "\"" + field.replace("\"", "\"\"") + "\"";
    }
    return field;
}
```

**Ejemplo:**
```
García, María → "García, María"
Juan "El Rey" → "Juan ""El Rey"""
```

#### **unescapeCsv(String field)**
```java
protected String unescapeCsv(String field) {
    if (field == null || field.isEmpty()) return "";
    if (field.startsWith("\"") && field.endsWith("\"")) {
        return field.substring(1, field.length() - 1).replace("\"\"", "\"");
    }
    return field;
}
```

### **Parsing CSV con Regex**
```java
String[] fields = csvLine.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
```

Este regex divide por comas **solo si están fuera de comillas**.

## 🔢 Generación de IDs

### **AtomicLong para IDs únicos**

```java
protected final AtomicLong idGenerator;

private void initializeIdGenerator() {
    List<T> entities = findAll();
    if (!entities.isEmpty()) {
        long maxId = entities.stream()
            .mapToLong(this::getId)
            .max()
            .orElse(0);
        idGenerator.set(maxId + 1);
    }
}

public T save(T entity) {
    if (getId(entity) == null) {
        setId(entity, idGenerator.getAndIncrement());
    }
    // ... guardar
}
```

### **Ventajas**
✅ IDs únicos garantizados
✅ Thread-safe con `AtomicLong`
✅ Continuidad entre reinicios (lee el max ID existente)

## 📊 Flujo de Operaciones

### **Guardar una Entidad**
```mermaid
1. save(estudiante)
2. ¿Tiene ID? → No → Asignar ID único
3. findAll() → Cargar todos
4. Reemplazar/agregar en lista
5. writeAll() → Escribir todos al CSV
6. Retornar entidad guardada
```

### **Leer Todas las Entidades**
```mermaid
1. findAll()
2. Abrir archivo CSV
3. Leer línea por línea (skip header)
4. fromCsv(línea) → Convertir a objeto
5. Retornar lista de entidades
```

### **Eliminar por ID**
```mermaid
1. deleteById(id)
2. findAll() → Cargar todos
3. Filtrar (removeIf con el ID)
4. writeAll() → Guardar cambios
5. Retornar true/false
```

## ⚠️ Limitaciones y Consideraciones

### **Limitaciones**
❌ No optimizado para grandes volúmenes de datos
❌ Operaciones de lectura/escritura completas en cada cambio
❌ No hay transacciones ni rollback
❌ Relaciones complejas requieren múltiples queries

### **Cuándo Usar Este Sistema**
✅ Prototipos y proyectos educativos
✅ Aplicaciones con pocos datos (<10,000 registros)
✅ Cuando no se requiere una BD relacional
✅ Para debugging (archivos legibles por humanos)

### **Cuándo NO Usarlo**
❌ Aplicaciones en producción con alta carga
❌ Datos sensibles (sin encriptación)
❌ Necesidad de consultas complejas (JOINs)
❌ Requerimientos de concurrencia alta

## 🚀 Migrar a Base de Datos

Si decides migrar a JPA/Hibernate:

1. **Agregar dependencia** (H2, MySQL, PostgreSQL)
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

2. **Anotar modelos**
```java
@Entity
@Table(name = "estudiantes")
public class Estudiante extends Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToMany
    private List<Curso> cursos;
}
```

3. **Cambiar repositorios**
```java
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
    // Queries automáticas
}
```

4. **Servicios sin cambios** (misma interfaz)

## 📝 Ejemplo de Uso Completo

```java
// 1. Crear estudiante
Estudiante estudiante = new Estudiante();
estudiante.setNombre("Juan Pérez");
estudiante.setEmail("juan@example.com");
estudiante.setMatricula("20241001");
estudiante.setCarrera("Ingeniería");
estudiante.setSemestre(5);

// 2. Guardar (ID auto-generado)
Estudiante guardado = estudianteRepository.save(estudiante);
System.out.println("ID asignado: " + guardado.getId()); // 1

// 3. Buscar todos
List<Estudiante> todos = estudianteRepository.findAll();

// 4. Buscar por ID
Optional<Estudiante> encontrado = estudianteRepository.findById(1L);

// 5. Actualizar
encontrado.ifPresent(e -> {
    e.setSemestre(6);
    estudianteRepository.save(e);
});

// 6. Eliminar
estudianteRepository.deleteById(1L);
```

## 🎓 Resumen

Este sistema de persistencia CSV es una solución **simple pero efectiva** para proyectos educativos y prototipos. Proporciona:

- ✅ Persistencia de datos entre reinicios
- ✅ Datos legibles por humanos (debugging fácil)
- ✅ Sin dependencias de bases de datos
- ✅ Fácil de entender y mantener
- ✅ Compatible con cualquier entorno

Para aplicaciones en producción, se recomienda migrar a una base de datos relacional (PostgreSQL, MySQL) con JPA/Hibernate.
