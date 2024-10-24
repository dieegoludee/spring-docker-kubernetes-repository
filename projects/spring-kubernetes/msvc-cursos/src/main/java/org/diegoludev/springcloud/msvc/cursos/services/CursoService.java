package org.diegoludev.springcloud.msvc.cursos.services;

import org.diegoludev.springcloud.msvc.cursos.models.Usuario;
import org.diegoludev.springcloud.msvc.cursos.models.entity.Curso;

import java.util.List;
import java.util.Optional;

public interface CursoService {

  List<Curso> listarCursos();

  Optional<Curso> buscarPorId(Long id);

  Curso guardarCurso(Curso curso);

  void eliminarCurso(Long id);

  // Asigna un usuario ya existente a un curso
  Optional<Usuario> asignarUsuario(Usuario usuario, Long idCurso);

  // Crea el usuario y lo asigna a un curso en particular
  Optional<Usuario> crearUsuario(Usuario usuario, Long idCurso);

  // Elimina la relacion de un usuario y un curso
  Optional<Usuario> eliminarUsuario(Usuario usuario, Long idCurso);
}
