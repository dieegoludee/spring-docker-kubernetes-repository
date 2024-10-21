package org.diegoludev.springcloud.msvc.cursos.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cursos")
public class Curso {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotEmpty
  private String nombre;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<CursoUsuario> cursoUsuarios;

  public Curso() {
    cursoUsuarios = new ArrayList<>();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public List<CursoUsuario> getCursoUsuario() {
    return cursoUsuarios;
  }

  public void setCursoUsuario(List<CursoUsuario> cursoUsuario) {
    this.cursoUsuarios = cursoUsuario;
  }

  // Método para añadir al ArrayList un usuario
  public void addCursoUsuario(CursoUsuario cursoUsuario) {
    cursoUsuarios.add(cursoUsuario);
  }

  // Método para eliminar un usuario del ArrayList
  public void removeCursoUsuario(CursoUsuario cursoUsuario) {
    cursoUsuarios.remove(cursoUsuario);
  }
}
