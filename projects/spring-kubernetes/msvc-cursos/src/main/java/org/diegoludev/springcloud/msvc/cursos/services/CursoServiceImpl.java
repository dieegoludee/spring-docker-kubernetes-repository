package org.diegoludev.springcloud.msvc.cursos.services;

import org.diegoludev.springcloud.msvc.cursos.models.Usuario;
import org.diegoludev.springcloud.msvc.cursos.models.entity.Curso;
import org.diegoludev.springcloud.msvc.cursos.repositories.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CursoServiceImpl implements CursoService {

  @Autowired
  private CursoRepository cursoRepository;

  @Override
  @Transactional(readOnly = true)
  public List<Curso> listarCursos() {
    return (List<Curso>) cursoRepository.findAll();
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Curso> buscarPorId(Long id) {
    return cursoRepository.findById(id);
  }

  @Override
  @Transactional
  public Curso guardarCurso(Curso curso) {
    return cursoRepository.save(curso);
  }

  @Override
  @Transactional
  public void eliminarCurso(Long id) {
    cursoRepository.deleteById(id);
  }

  @Override
  public Optional<Usuario> asignarUsuario(Usuario usuario, Long idCurso) {
    return Optional.empty();
  }

  @Override
  public Optional<Usuario> crearUsuario(Usuario usuario, Long idCurso) {
    return Optional.empty();
  }

  @Override
  public Optional<Usuario> eliminarUsuario(Usuario usuario, Long idCurso) {
    return Optional.empty();
  }
}
