package com.google.springboot.service.postgresql;

import com.google.springboot.Application;
import com.google.springboot.model.postgresql.ApiResponse;
import com.google.springboot.model.postgresql.Employee;
import com.google.springboot.postgresqlrepository.EmployeeRepository;
import com.google.springboot.service.apiratelimiter.mallRheaRateLimiter.RateLimitResource;
import com.google.springboot.service.cache.LocalCacheMechanismV2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @Author kris
 * @Create 2024-09-23 22:50
 * @Description
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {
    private static final Logger log = LoggerFactory.getLogger(Application.class);
    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @RateLimitResource(value = "employee:findById", limit = 10, timeUnit = TimeUnit.SECONDS)
    public Optional<Employee> findById(Long id) {
        return employeeRepository.findById(id);
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void deleteById(Long id) {
        employeeRepository.deleteById(id);
    }

    @Retryable(value = {RuntimeException.class}, maxAttempts = 3, backoff = @Backoff(delay = 1000, multiplier = 2))
    public ApiResponse callApi() {
        log.info("Attempting API call");
        // 模擬 API 調用，有 70% 的概率失敗
        if (Math.random() < 0.9) {
            log.error("API call failed, will retry");
            throw new RuntimeException("API call failed");
        }
        return new ApiResponse("Success");

    }
}
