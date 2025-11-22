package co.edu.umanizales.restaurante_api.controller;

import co.edu.umanizales.restaurante_api.model.Tuition;
import co.edu.umanizales.restaurante_api.service.TuitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tuition")
@RequiredArgsConstructor
public class TuitionController {
    
    private final TuitionService tuitionService;

    @GetMapping
    public ResponseEntity<List<Tuition>> getAllTuitions() {
        return ResponseEntity.ok(tuitionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tuition> getTuitionById(@PathVariable long id) {
        Tuition tuition = tuitionService.findById(id);
        if (tuition != null) {
            return ResponseEntity.ok(tuition);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Tuition>> getTuitionsByStudent(@PathVariable long studentId) {
        return ResponseEntity.ok(tuitionService.findByStudentId(studentId));
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Tuition>> getTuitionsByCourse(@PathVariable long courseId) {
        return ResponseEntity.ok(tuitionService.findByCourseId(courseId));
    }

    @PostMapping
    public ResponseEntity<?> createTuition(@RequestBody Tuition tuition) {
        try {
            Tuition saved = tuitionService.save(tuition);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/enroll")
    public ResponseEntity<Tuition> enrollStudent(@RequestBody Map<String, Object> request) {
        try {
            long studentId = Long.parseLong(request.get("studentId").toString());
            long courseId = Long.parseLong(request.get("courseId").toString());
            
            Tuition tuition = tuitionService.enrollStudent(studentId, courseId);
            return ResponseEntity.status(HttpStatus.CREATED).body(tuition);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tuition> updateTuition(@PathVariable long id, @RequestBody Tuition tuition) {
        try {
            Tuition updated = tuitionService.update(id, tuition);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTuition(@PathVariable long id) {
        boolean deleted = tuitionService.deleteById(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
