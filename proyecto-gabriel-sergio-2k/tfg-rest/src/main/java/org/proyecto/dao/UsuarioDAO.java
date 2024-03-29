package org.proyecto.dao;

import org.proyecto.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interfaz DAO de Usuario .
 */
@Repository
public interface UsuarioDAO extends JpaRepository<Usuario, Integer> {
    Usuario findByEmailAndPassword(String email, String password);

    Usuario findByEmail(String email);


}
