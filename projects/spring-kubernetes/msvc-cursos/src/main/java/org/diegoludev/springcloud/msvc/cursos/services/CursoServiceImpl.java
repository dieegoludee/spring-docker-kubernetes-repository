package org.diegoludev.springcloud.msvc.cursos.services;

import org.diegoludev.springcloud.msvc.cursos.clients.UsuarioClientRest;
import org.diegoludev.springcloud.msvc.cursos.models.Usuario;
import org.diegoludev.springcloud.msvc.cursos.models.entity.Curso;
import org.diegoludev.springcloud.msvc.cursos.models.entity.CursoUsuario;
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

  @Autowired
  private UsuarioClientRest client;

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
  @Transactional
  public Optional<Usuario> asignarUsuario(Usuario usuario, Long idCurso) {
    Optional<Curso> cursoOptional = cursoRepository.findById(idCurso);
    if (cursoOptional.isPresent()) {
      Usuario user = client.detalle(usuario.getId());
      Curso curso = cursoOptional.orElseThrow();

      CursoUsuario cursoUsuario = new CursoUsuario();
      cursoUsuario.setUsuarioId(user.getId());
      curso.addCursoUsuario(cursoUsuario);
      cursoRepository.save(curso);
      return Optional.of(user);
    }
    return Optional.empty();
  }

  @Override
  @Transactional
  public Optional<Usuario> crearUsuario(Usuario usuario, Long idCurso) {
    Optional<Curso> cursoOptional = cursoRepository.findById(idCurso);
    if (cursoOptional.isPresent()) {
      Usuario newUser = client.crearUsuario(usuario);
      Curso curso = cursoOptional.orElseThrow();

      CursoUsuario cursoUsuario = new CursoUsuario();
      cursoUsuario.setUsuarioId(newUser.getId());

      curso.addCursoUsuario(cursoUsuario);
      cursoRepository.save(curso);
      return Optional.of(newUser);
    }
    return Optional.empty();
  }

  @Override
  @Transactional
  public Optional<Usuario> eliminarUsuario(Usuario usuario, Long idCurso) {
    Optional<Curso> cursoOptional = cursoRepository.findById(idCurso);
    if (cursoOptional.isPresent()) {
      Usuario newUser = client.crearUsuario(usuario);
      Curso curso = cursoOptional.orElseThrow();

      CursoUsuario cursoUsuario = new CursoUsuario();
      cursoUsuario.setUsuarioId(newUser.getId());

      curso.removeCursoUsuario(cursoUsuario);
      cursoRepository.save(curso);
      return Optional.of(newUser);
    }
    return Optional.empty();
  }
}
