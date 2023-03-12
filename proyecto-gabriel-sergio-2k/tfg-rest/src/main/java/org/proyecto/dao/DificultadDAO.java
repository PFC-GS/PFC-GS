package org.proyecto.dao;

import org.proyecto.Entity.Dificultad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DificultadDAO extends JpaRepository<Dificultad,Integer> {
}
