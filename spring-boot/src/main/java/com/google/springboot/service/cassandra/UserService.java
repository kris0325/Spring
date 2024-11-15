package com.google.springboot.service.cassandra;

import com.google.springboot.model.cassandrea.User;

import java.util.List;

/**
 * @Author kris
 * @Create 2024-10-19 11:45
 * @Description
 */
public interface UserService {

    List<User> getAllUsers();

    User getUserById(String id);

    User createUser(User user);

    void deleteUser(String id);
}
