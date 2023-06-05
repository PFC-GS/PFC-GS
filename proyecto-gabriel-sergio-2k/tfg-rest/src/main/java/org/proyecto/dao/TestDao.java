package org.proyecto.dao;

import org.proyecto.Entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

/**
 * Interfaz DAO de Test .
 */
@Repository
public interface TestDao extends JpaRepository<Test, Integer> {

    /**
     * Método para obtener el último test realizado por un usuario
     *
     * @param usuarioId id del usuario
     * @return Test
     */
    @Query(value = "SELECT * FROM test WHERE fecha = :fecha AND usuario = :usuarioId ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Test findTestByUserIdAndDate(@Param("usuarioId") Integer usuarioId, @Param("fecha") Timestamp fecha);

    /**
     * Método para obtener todos los test realizados por un usuario
     *
     * @param usuarioId id del usuario
     * @return List de Test
     */
    @Query(value = "SELECT * FROM test WHERE usuario = :usuarioId ORDER BY fecha desc", nativeQuery = true)
    List<Test> findTestByUserId(@Param("usuarioId") Integer usuarioId);
}

