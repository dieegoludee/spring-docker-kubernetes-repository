package org.diegoludev.springcloud.msvc.usuarios.services;

import org.diegoludev.springcloud.msvc.usuarios.models.entity.Usuario;
import org.diegoludev.springcloud.msvc.usuarios.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Override
  @Transactional(readOnly = true)
  public List<Usuario> listar() {
    return (List<Usuario>) usuarioRepository.findAll();
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Usuario> buscarPorId(Long id) {
    return usuarioRepository.findById(id);
  }

  @Override
  @Transactional
  public Usuario guardar(Usuario usuario) {
    return usuarioRepository.save(usuario);
  }

  @Override
  @Transactional
  public void eliminar(Long id) {
    usuarioRepository.deleteById(id);
  }

  @Override
  public Optional<Usuario> buscarPorEmail(String email) {
    return usuarioRepository.findByEmail(email);
  }

  @Override
  public boolean existePorEmail(String email) {
    return usuarioRepository.existsByEmail(email);
  }
}
