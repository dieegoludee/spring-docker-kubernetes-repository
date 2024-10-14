package org.diegoludev.springcloud.msvc.usuarios.services;

import org.diegoludev.springcloud.msvc.usuarios.models.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
  List<Usuario> listar();

  Optional<Usuario> buscarPorId(Long id);

  Usuario guardar(Usuario usuario);

  void eliminar(Long id);

  Optional<Usuario> buscarPorEmail(String email);

  boolean existePorEmail(String email);
}
