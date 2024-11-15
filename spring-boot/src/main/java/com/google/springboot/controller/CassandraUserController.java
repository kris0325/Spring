package com.google.springboot.controller;
import com.google.springboot.model.cassandrea.User;
import com.google.springboot.service.cassandra.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author kris
 * @Create 2024-10-19 11:48
 * @Description
 */

@RestController
@RequestMapping("/api/users")
public class CassandraUserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * @RequestParam : 查询参数通常用于可选的、非唯一标识的参数。
     * @PathVariable : 路径参数通常用于必需的、唯一标识资源的参数。
     * 在RESTful API设计中，通常推荐使用路径参数来标识资源，但在某些情况下，使用查询参数也是合适的。
     * 选择建议
     * 如果 id 是用户的唯一标识符，通常更推荐使用路径参数（如 /users/{id}）。
     * 如果您需要支持多种可选的过滤或查询条件，使用查询参数可能更合适。
     * 通过这种方式，您可以灵活地选择最适合您的API设计的参数传递方式。
     * */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/queryUserById")
    public ResponseEntity<User> queryUserById(@RequestParam String id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }


    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
