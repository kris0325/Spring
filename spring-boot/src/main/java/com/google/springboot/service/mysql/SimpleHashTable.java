package com.google.springboot.service.mysql;

/**
 * @Author kris
 * @Create 2024-06-18 16:46
 * @Description
 */

import java.util.Arrays;


public class SimpleHashTable {
    private String[] hashTable;

    public SimpleHashTable(int size) {
        hashTable = new String[size];
    }

    private int simpleHash(String key) {
        return key.hashCode() % hashTable.length;
    }

    public void insert(String key, String value) {
        int index = simpleHash(key);
        hashTable[index] = value;

    }

    public String get(String key) {
        int index = simpleHash(key);
        return hashTable[index];
    }



    public static void main(String[] args) {
        SimpleHashTable table = new SimpleHashTable(10);
        table.insert("Alice", "Alice's data");
        table.insert("kris", "kris's data");
        System.out.println("Alice: " + table.get("Alice"));
        System.out.println("kris: " + table.get("kris"));
    }
}
