package org.proyecto.dao;

import org.proyecto.Entity.Pregunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreguntaDAO extends JpaRepository<Pregunta,Integer> {
    List<Pregunta> findByDificultad_Id(Integer id);

    List<Pregunta> findByCategoria_Id(Integer id);

    List<Pregunta> findByDificultad_IdAndCategoria_Id(Integer dificultadId, Integer categoriaId);




}
