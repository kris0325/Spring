package com.google.springboot.service.mysql;

/**
 * @Author kris
 * @Create 2024-06-18 17:05
 * @Description
 */

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class ConsistentHashingWithVirtualNodes {
    private final SortedMap<Integer, String> circle = new TreeMap<>();
    private final List<String> realNodes;
    private final int numberOfReplicas;

    public ConsistentHashingWithVirtualNodes(List<String> nodes, int numberOfReplicas) {
        this.realNodes = new ArrayList<>(nodes);
        this.numberOfReplicas = numberOfReplicas;
        for (String node : realNodes) {
            addNode(node);
        }
    }

    private int hash(String key) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] digest = md5.digest(key.getBytes());
            return ((digest[0] & 0xff) << 24) | ((digest[1] & 0xff) << 16) | ((digest[2] & 0xff) << 8) | (digest[3] & 0xff);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public void addNode(String node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            int hash = hash(node + ":" + i);
            circle.put(hash, node);
        }
    }

    public void removeNode(String node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            int hash = hash(node + ":" + i);
            circle.remove(hash);
        }
    }

    /*
    1. 为什么要执行 circle.tailMap(hash)？
在一致性哈希中，哈希环上的每个节点和数据项都有一个哈希值。
当我们需要确定一个数据项应该存储在哪个节点时，我们会计算数据项的哈希值，
并在环上找到大于等于该哈希值的第一个节点（即顺时针方向的第一个节点）。

tailMap(hash)返回一个包含从hash到TreeMap末尾的所有映射的视图。
如果没有比hash更大的键（即tailMap为空），则我们需要循环回到哈希环的开始位置，因此我们使用circle.firstKey()。这样，我们就能在哈希环上找到最接近数据项哈希值的节点。

2. TreeMap如何实现哈希环？
TreeMap是一个有序的红黑树实现，它可以根据键的自然顺序或通过比较器来维护键的顺序。
在一致性哈希的实现中，TreeMap的有序特性使得我们可以方便地查找大于等于某个哈希值的第一个节点。

以下是关于TreeMap如何实现哈希环的详细解释：

底层数据结构与实现原理
TreeMap是一个基于红黑树的有序映射。红黑树是一种平衡二叉搜索树，具有以下特性：

有序性：红黑树中节点的键是有序的，这使得TreeMap能够高效地执行范围查找操作（如tailMap）。
平衡性：红黑树保持相对平衡，确保插入、删除、查找操作的时间复杂度为O(log N)。
哈希环模拟：通过将节点和数据项映射到一个大整数空间（通常是哈希值的范围），并在该空间上形成一个环。
TreeMap的有序性允许我们高效地找到顺时针方向的第一个节点。
    *
    ***/
    public String getNode(String key) {

        if (circle.isEmpty()) {
            return null;
        }
        int hash = hash(key);
        if (!circle.containsKey(hash)) {
            //目的是要在哈希环上找到最接近数据项key哈希值的节点，要么再最后面，要么取firstKey
            SortedMap<Integer, String> tailMap = circle.tailMap(hash);
            hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
        }
        return circle.get(hash);
    }

    public static void main(String[] args) {
//        List<String> nodes = List.of("cache1", "cache2", "cache3", "cache4");
        List<String> nodes = new ArrayList<>();
        nodes.add("cache1");
        nodes.add("cache2");
        nodes.add("cache3");
        nodes.add("cache4");
        ConsistentHashingWithVirtualNodes consistentHashing = new ConsistentHashingWithVirtualNodes(nodes, 100);

        String dataKey = "user123";
        String node = consistentHashing.getNode(dataKey);
        System.out.println("Data with key '" + dataKey + "' will be stored in '" + node + "'");

        // Adding a new node
        consistentHashing.addNode("cache5");
        node = consistentHashing.getNode(dataKey);
        System.out.println("After adding cache5, data with key '" + dataKey + "' will be stored in '" + node + "'");

        // Removing a node
        consistentHashing.removeNode("cache2");
        node = consistentHashing.getNode(dataKey);
        System.out.println("After removing cache2, data with key '" + dataKey + "' will be stored in '" + node + "'");
    }
}

