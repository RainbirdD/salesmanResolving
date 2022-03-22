package com.salesman.Repository;

import com.salesman.Entity.Coordinates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CoordinatesRepository extends JpaRepository<Coordinates, Long> {
}
