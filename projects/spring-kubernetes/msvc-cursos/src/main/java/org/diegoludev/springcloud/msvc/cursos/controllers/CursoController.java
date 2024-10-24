package org.diegoludev.springcloud.msvc.cursos.controllers;

import feign.FeignException;
import jakarta.validation.Valid;
import org.diegoludev.springcloud.msvc.cursos.models.Usuario;
import org.diegoludev.springcloud.msvc.cursos.models.entity.Curso;
import org.diegoludev.springcloud.msvc.cursos.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

  @PutMapping("/asignar-usuario/{cursoId}")
  public ResponseEntity<?> asignarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId) {
    Optional<Usuario> optionalUsuario = null;
    try {
      optionalUsuario = cursoService.asignarUsuario(usuario, cursoId);
    } catch (FeignException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
              .body(Collections.singletonMap("Mensaje", "No existe el Usuario por el ID " +
                      "o error en la comunicación: " + e.getMessage()));
    }
    if (optionalUsuario.isPresent()) {
      return ResponseEntity.status(HttpStatus.CREATED).body(optionalUsuario.orElseThrow());
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping("/crear-usuario/{cursoId}")
  public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId) {
    Optional<Usuario> optionalUsuario = null;
    try {
      optionalUsuario = cursoService.crearUsuario(usuario, cursoId);
    } catch (FeignException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
              .body(Collections.singletonMap("Mensaje", "No se pudo crear el usuario " +
                      "o error en la comunicación: " + e.getMessage()));
    }
    if (optionalUsuario.isPresent()) {
      return ResponseEntity.status(HttpStatus.CREATED).body(optionalUsuario.orElseThrow());
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/eliminar-usuario/{cursoId}")
  public ResponseEntity<?> eliminarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId) {
    Optional<Usuario> optionalUsuario = null;
    try {
      optionalUsuario = cursoService.eliminarUsuario(usuario, cursoId);
    } catch (FeignException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
              .body(Collections.singletonMap("Mensaje", "No existe el Usuario por el ID " +
                      "o error en la comunicación: " + e.getMessage()));
    }
    if (optionalUsuario.isPresent()) {
      return ResponseEntity.status(HttpStatus.OK).body(optionalUsuario.orElseThrow());
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
