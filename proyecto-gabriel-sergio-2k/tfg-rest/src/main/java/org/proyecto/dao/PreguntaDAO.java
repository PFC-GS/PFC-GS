package org.proyecto.dao;

import org.proyecto.Entity.Pregunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreguntaDAO extends JpaRepository<Pregunta,Integer> {
}
