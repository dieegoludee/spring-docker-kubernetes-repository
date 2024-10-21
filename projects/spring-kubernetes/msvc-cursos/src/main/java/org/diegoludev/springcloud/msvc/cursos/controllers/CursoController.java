package org.diegoludev.springcloud.msvc.cursos.controllers;

import jakarta.validation.Valid;
import org.diegoludev.springcloud.msvc.cursos.models.entity.Curso;
import org.diegoludev.springcloud.msvc.cursos.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class CursoController {

  @Autowired
  private CursoService cursoService;

  @GetMapping
  public ResponseEntity<List<Curso>> listar() {
    return ResponseEntity.ok(cursoService.listarCursos());
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> listarPorId(@PathVariable Long id) {
    Optional<Curso> optionalCurso = cursoService.buscarPorId(id);
    if (optionalCurso.isPresent()) {
      return ResponseEntity.ok(optionalCurso.orElseThrow());
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping
  public ResponseEntity<?> crearCurso(@Valid @RequestBody Curso curso, BindingResult result) {
    if (result.hasErrors()) {
      return validate(result);
    }
    return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.guardarCurso(curso));
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> editarCurso(@Valid @RequestBody Curso curso, BindingResult result, @PathVariable Long id) {
    if (result.hasErrors()) {
      return validate(result);
    }
    Optional<Curso> optionalCurso = cursoService.buscarPorId(id);
    if (optionalCurso.isPresent()) {
      Curso cursoDb = optionalCurso.orElseThrow();
      cursoDb.setNombre(curso.getNombre());
      return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.guardarCurso(cursoDb));
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> eliminarCurso(@PathVariable Long id) {
    Optional<Curso> optionalCurso = cursoService.buscarPorId(id);
    if (optionalCurso.isPresent()) {
      cursoService.eliminarCurso(id);
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }

  private static ResponseEntity<Map<String, String>> validate(BindingResult result) {
    Map<String, String> errors = new HashMap<>();
    result.getFieldErrors().forEach(error -> {
      errors.put(error.getField(), "El campo " + error.getField() + " " + error.getDefaultMessage());
    });
    return ResponseEntity.badRequest().body(errors);
  }
}
