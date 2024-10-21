package org.diegoludev.springcloud.msvc.cursos.services;

import org.diegoludev.springcloud.msvc.cursos.models.entity.Curso;

import java.util.List;
import java.util.Optional;

public interface CursoService {

  List<Curso> listarCursos();

  Optional<Curso> buscarPorId(Long id);

  Curso guardarCurso(Curso curso);

  void eliminarCurso(Long id);
}
