package org.diegoludev.springcloud.msvc.usuarios.controllers;

import org.diegoludev.springcloud.msvc.usuarios.models.entity.Usuario;
import org.diegoludev.springcloud.msvc.usuarios.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UsuarioController {

  @Autowired
  private UsuarioService usuarioService;

  @GetMapping
  public List<Usuario> listar() {
    return usuarioService.listar();
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> detalle(@PathVariable Long id) {
    Optional<Usuario> usuarioOptional = usuarioService.buscarPorId(id);
    if (usuarioOptional.isPresent()) {
      return ResponseEntity.ok(usuarioOptional.get());
    }
    return ResponseEntity.notFound().build();
  }

  /* @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Usuario crearUsuario(@RequestBody Usuario usuario) {
    return usuarioService.guardar(usuario);
  } */

  @PostMapping
  public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario) {
    return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.guardar(usuario));
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> editarUsuario(@RequestBody Usuario usuario, @PathVariable Long id) {
    Optional<Usuario> usuarioOptional = usuarioService.buscarPorId(id);
    if (usuarioOptional.isPresent()) {
      Usuario usuarioDb = usuarioOptional.get();
      usuarioDb.setNombre(usuario.getNombre());
      usuarioDb.setEmail(usuario.getEmail());
      usuarioDb.setPassword(usuario.getPassword());
      return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.guardar(usuarioDb));
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
    Optional<Usuario> usuarioOptional = usuarioService.buscarPorId(id);
    if (usuarioOptional.isPresent()) {
      usuarioService.eliminar(id);
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }
}
