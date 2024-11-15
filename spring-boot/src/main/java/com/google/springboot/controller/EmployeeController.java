package com.google.springboot.controller;

import com.google.gson.Gson;
import com.google.springboot.model.postgresql.ApiResponse;
import com.google.springboot.model.postgresql.Employee;
import com.google.springboot.service.apiratelimiter.mallRheaRateLimiter.RateLimitException;
import com.google.springboot.service.postgresql.EmployeeService;
import com.google.springboot.service.rabbitmq.TaskProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @Author kris
 * @Create 2024-09-23 22:52
 * @Description
 */
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private TaskProducer taskProducer;

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.findAll();
    }

    @GetMapping("get/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        // Optional<Employee> employee = employeeService.findById(id);
        // return employee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        try {
            // 測試限流 10次/s
            for (int i = 0; i < 15; i++) {
                try {
                    Optional<Employee> employee = employeeService.findById(id);
                    System.out.println("Request " + (i + 1) + " succeeded");
                } catch (RateLimitException e) {
                    System.out.println("Request " + (i + 1) + " rate limited: " + e.getMessage());
                    // 不要立即返回，繼續循環
                    continue;
                }
            }
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.TOO_MANY_REQUESTS)
                    .body(null);
        }

    }

    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.save(employee);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
        Optional<Employee> employee = employeeService.findById(id);
        if (employee.isPresent()) {
            Employee existingEmployee = employee.get();
            existingEmployee.setName(employeeDetails.getName());
            existingEmployee.setPosition(employeeDetails.getPosition());
            existingEmployee.setSalary(employeeDetails.getSalary());
            return ResponseEntity.ok(employeeService.save(existingEmployee));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/callApi")
    public ResponseEntity<ApiResponse> callApi(@RequestBody Employee employee) {
        ApiResponse response = employeeService.callApi();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/rabbitmq/taskProducer")
    public ResponseEntity<String> taskProducer(@RequestBody Employee employee) {
        Gson gson = new Gson();
        employee.setName(UUID.randomUUID().toString());
        taskProducer.sendTask(gson.toJson(employee));
        return ResponseEntity.ok("200");
    }



}
