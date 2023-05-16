package org.proyecto.dao;

import org.proyecto.Entity.Pregunta;
import org.proyecto.Entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Set;

@Repository
public interface TestDao extends JpaRepository<Test,Integer> {
    @Query(value = "SELECT * FROM test WHERE fecha = :fecha AND usuario = :usuarioId ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Test findTestByUserIdAndDate(@Param("usuario")Integer usuarioId, @Param("fecha") Timestamp fecha);
}
