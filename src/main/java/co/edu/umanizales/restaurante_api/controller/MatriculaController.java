package co.edu.umanizales.restaurante_api.controller;

import co.edu.umanizales.restaurante_api.model.Matricula;
import co.edu.umanizales.restaurante_api.service.MatriculaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/matriculas")
@RequiredArgsConstructor
public class MatriculaController {
    
    private final MatriculaService matriculaService;

    @GetMapping
    public ResponseEntity<List<Matricula>> getAllMatriculas() {
        return ResponseEntity.ok(matriculaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Matricula> getMatriculaById(@PathVariable Long id) {
        return matriculaService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/estudiante/{estudianteId}")
    public ResponseEntity<List<Matricula>> getMatriculasByEstudiante(@PathVariable Long estudianteId) {
        return ResponseEntity.ok(matriculaService.findByEstudianteId(estudianteId));
    }

    @GetMapping("/curso/{cursoId}")
    public ResponseEntity<List<Matricula>> getMatriculasByCurso(@PathVariable Long cursoId) {
        return ResponseEntity.ok(matriculaService.findByCursoId(cursoId));
    }

    @PostMapping
    public ResponseEntity<Matricula> createMatricula(@RequestBody Matricula matricula) {
        try {
            Matricula saved = matriculaService.save(matricula);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/matricular")
    public ResponseEntity<Matricula> matricularEstudiante(@RequestBody Map<String, Object> request) {
        try {
            Long estudianteId = Long.parseLong(request.get("estudianteId").toString());
            Long cursoId = Long.parseLong(request.get("cursoId").toString());
            String periodo = request.get("periodo").toString();
            
            Matricula matricula = matriculaService.matricularEstudiante(estudianteId, cursoId, periodo);
            return ResponseEntity.status(HttpStatus.CREATED).body(matricula);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Matricula> updateMatricula(@PathVariable Long id, @RequestBody Matricula matricula) {
        try {
            Matricula updated = matriculaService.update(id, matricula);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatricula(@PathVariable Long id) {
        boolean deleted = matriculaService.deleteById(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
