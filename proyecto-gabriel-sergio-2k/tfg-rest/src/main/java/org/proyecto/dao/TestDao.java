package org.proyecto.dao;

import org.proyecto.Entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestDao extends JpaRepository<Test,Integer> {
}
