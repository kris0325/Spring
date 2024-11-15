package com.google.springboot.service.postgresql;

import com.google.springboot.model.postgresql.ApiResponse;
import com.google.springboot.model.postgresql.Employee;

import java.util.List;
import java.util.Optional;

/**
 * @Author kris
 * @Create 2024-09-23 22:49
 * @Descriptiondd
 */
//@Service
public interface EmployeeService {

    public List<Employee> findAll();

    public Optional<Employee> findById(Long id);

    public Employee save(Employee employee);

    public void deleteById(Long id);

    ApiResponse callApi();

}
