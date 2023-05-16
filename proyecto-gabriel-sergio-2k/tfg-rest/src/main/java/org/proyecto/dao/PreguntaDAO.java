package org.proyecto.dao;

import org.proyecto.Entity.Pregunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PreguntaDAO extends JpaRepository<Pregunta,Integer> {
    List<Pregunta> findByDificultad_Id(Integer id);

    List<Pregunta> findByCategoria_Id(Integer id);

    List<Pregunta> findByDificultad_IdAndCategoria_Id(Integer dificultadId, Integer categoriaId);

    /**
     * Método para obtener un número de preguntas aleatorias de una categoría específica
     * @param cate categoría de las preguntas
     * @param num número de preguntas a elegir
     * @return  Set de preguntas
     */
    @Query(value = "Select * from pregunta where categoria = :cate order by random() LIMIT :num", nativeQuery = true)
    Set<Pregunta> findRandomQuestions(@Param("cate") Integer cate, @Param("num") Integer num);




}
