package org.proyecto.dao;

import org.proyecto.Entity.Categoria;
import org.proyecto.Entity.Pregunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaDao extends JpaRepository<Categoria,Integer> {
}
