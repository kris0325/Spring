package com.google.springboot.service.mysql;

/**
 * @Author kris
 * @Create 2024-06-18 16:45
 * @Description: ConsistentHashing
 *
 *
 *
 * 根据你提供的测试结果，“user123”在添加或移除节点时映射到的节点没有发生变化。
 * 出现这种现象的原因可能是由于哈希环的分布不均匀，或者哈希函数返回的值在变化前后恰好落在相同的节点上。
 * 我们可以通过增加虚拟节点（虚拟节点技术）来更均匀地分布节点，从而提高一致性哈希的均衡性和稳定性。见ConsistentHashingWithVirtualNodes
 */
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class ConsistentHashing {
    private final SortedMap<Integer, String> circle = new TreeMap<>();
    private final List<String> nodes;

    public ConsistentHashing(List<String> nodes) {
        this.nodes = new ArrayList<>(nodes);
        for (String node : nodes) {
            addNode(node);
        }
    }

    private int hash(String key) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] digest = md5.digest(key.getBytes());
            return ((digest[0] & 0xff) << 24) | ((digest[1] & 0xff) << 16)
                    | ((digest[2] & 0xff) << 8) | (digest[3] & 0xff);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public void addNode(String node) {
        int hash = hash(node);
        circle.put(hash, node);
    }

    public void removeNode(String node) {
        int hash = hash(node);
        circle.remove(hash);
    }

    public String getNode(String key) {
        if (circle.isEmpty()) {
            return null;
        }
        int hash = hash(key);
        if (!circle.containsKey(hash)) {
            SortedMap<Integer, String> tailMap = circle.tailMap(hash);
            hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
        }
        return circle.get(hash);
    }

    public static void main(String[] args) {
        List<String> nodes = new ArrayList<>();
        nodes.add("cache1");
        nodes.add("cache2");
        nodes.add("cache3");
        nodes.add("cache4");

//        List<String> nodes = List.of("cache1", "cache2", "cache3", "cache4");
        ConsistentHashing consistentHashing = new ConsistentHashing(nodes);

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

