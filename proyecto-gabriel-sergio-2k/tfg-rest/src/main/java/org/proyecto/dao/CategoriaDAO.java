package org.proyecto.dao;

import org.proyecto.Entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interfaz DAO de Categoria .
 */

@Repository
public interface CategoriaDAO extends JpaRepository<Categoria, Integer> {
}
