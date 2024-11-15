package com.google.springboot.postgresqlrepository;

import com.google.springboot.model.postgresql.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author kris
 * @Create 2024-09-23 22:47
 * @Description
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
