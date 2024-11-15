package com.google.springboot.model.cassandrea;
import jakarta.persistence.Entity;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * @Author kris
 * @Create 2024-10-19 11:42
 * @Description
 */

@Entity
@Table("users")  // 表名
public class User {

    @PrimaryKey
    private String id;
    private String name;
    private int age;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    // Getters and Setters
}
