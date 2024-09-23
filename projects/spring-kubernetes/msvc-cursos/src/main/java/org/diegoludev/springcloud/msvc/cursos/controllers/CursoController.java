package org.diegoludev.springcloud.msvc.cursos.controllers;

import org.diegoludev.springcloud.msvc.cursos.entities.Curso;
import org.diegoludev.springcloud.msvc.cursos.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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
}
