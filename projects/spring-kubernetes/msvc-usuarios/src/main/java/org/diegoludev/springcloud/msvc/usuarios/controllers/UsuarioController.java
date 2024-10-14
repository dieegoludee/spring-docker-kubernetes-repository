package org.diegoludev.springcloud.msvc.usuarios.controllers;

import jakarta.validation.Valid;
import org.diegoludev.springcloud.msvc.usuarios.models.entity.Usuario;
import org.diegoludev.springcloud.msvc.usuarios.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
      return ResponseEntity.ok(usuarioOptional.orElseThrow());
    }
    return ResponseEntity.notFound().build();
  }

  /* @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Usuario crearUsuario(@RequestBody Usuario usuario) {
    return usuarioService.guardar(usuario);
  } */

  @PostMapping
  public ResponseEntity<?> crearUsuario(@Valid @RequestBody Usuario usuario, BindingResult result) {
    if (result.hasErrors()) {
      return validate(result);
    }
    if (!usuario.getEmail().isEmpty() && usuarioService.existePorEmail(usuario.getEmail())) {
      return ResponseEntity.badRequest()
              .body(Collections.singletonMap("mensaje", "Ya existe un usuario con ese correo electrónico"));
    }
    return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.guardar(usuario));
  }


  @PutMapping("/{id}")
  public ResponseEntity<?> editarUsuario(@Valid @RequestBody Usuario usuario, BindingResult result, @PathVariable Long id) {
    if (result.hasErrors()) {
      return validate(result);
    }
    Optional<Usuario> usuarioOptional = usuarioService.buscarPorId(id);
    if (usuarioOptional.isPresent()) {
      Usuario usuarioDb = usuarioOptional.orElseThrow();

      if (!usuario.getEmail().isEmpty() && !usuario.getEmail().equalsIgnoreCase(usuarioDb.getEmail()) && usuarioService.buscarPorEmail(usuario.getEmail()).isPresent()) {
        return ResponseEntity.badRequest()
                .body(Collections.singletonMap("mensaje", "Ya existe un usuario con ese correo electrónico"));
      }

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

  private static ResponseEntity<Map<String, String>> validate(BindingResult result) {
    Map<String, String> errors = new HashMap<>();
    result.getFieldErrors().forEach(error -> {
      errors.put(error.getField(), "El campo " + error.getField() + " " + error.getDefaultMessage());
    });
    return ResponseEntity.badRequest().body(errors);
  }
}
