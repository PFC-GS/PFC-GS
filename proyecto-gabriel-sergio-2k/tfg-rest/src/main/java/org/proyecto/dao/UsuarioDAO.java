package org.proyecto.dao;

import org.proyecto.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioDAO extends JpaRepository<Usuario, Integer> {
    Usuario findByEmailAndPassword(String email, String password);
}
