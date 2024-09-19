package org.diegoludev.springcloud.msvc.usuarios.services;

import org.diegoludev.springcloud.msvc.usuarios.models.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
  List<Usuario> listar();
  Optional<Usuario> buscarPorId(Long id);
  Usuario guardar(Usuario usuario);
  Optional<Usuario> eliminar(Long id);
}
