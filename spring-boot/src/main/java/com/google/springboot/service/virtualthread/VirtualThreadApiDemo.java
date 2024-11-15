package com.google.springboot.service.virtualthread;

/**
 * @Author kris
 * @Create 2024-10-13 15:58
 * @Description
 */
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class VirtualThreadApiDemo {
    public static void main(String[] args) throws Exception {
        // 使用普通线程池
        CommonThread();

        // 使用虚拟线程池
        VirtualThread();

    }
    public static void VirtualThread() throws Exception {
        // 使用虚拟线程池
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

        // 需要并发执行的API请求数
        int requestCount = 10;

        for (int i = 0; i < requestCount; i++) {
//            System.out.println("Task " + i + " start: ");
            int taskId = i;
            executor.submit(() -> {
                try {
                    HttpClient client = HttpClient.newHttpClient();
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create("https://jsonplaceholder.typicode.com/posts/" + taskId))
                            .build();
                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                    // 检查响应状态码
                    if (response.statusCode() == 200) {
                        System.out.println("VirtualThread " + taskId + " Response: " + response.body());
                    } else {
                        System.out.println("VirtualThread " + taskId + " failed with status code: " + response.statusCode());
                    }
                } catch (Exception e) {
                    System.out.println("VirtualThread " + taskId + " encountered an error: " + e.getMessage());
                }
            });
        }
        System.out.println("VirtualThread Task " + " finished: ");

        // 关闭虚拟线程池
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);

    }

    //普通多线程实现
    public static void CommonThread() throws InterruptedException {
        // 创建一个固定线程池，最大线程数为 100
        ExecutorService executor = Executors.newFixedThreadPool(100);

        // 模拟 1000 个并发任务
        for (int i = 0; i < 10; i++) {
            final int taskId = i;
            executor.submit(() -> {
                try {
                    // 模拟 HTTP 请求
                    HttpClient client = HttpClient.newHttpClient();
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create("https://jsonplaceholder.typicode.com/posts/" + taskId))
                            .build();
                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    System.out.println("CommonThread " + taskId + " Response: " + response.body());
                } catch (Exception e) {
                    System.out.println("CommonThread " + taskId + " encountered an error: " + e.getMessage());
                }
            });
        }
        System.out.println("CommonThread Task " + " finished: ");
        // 关闭线程池
        executor.shutdown();
        // 等待所有线程执行完毕，最长等待 30 秒
        executor.awaitTermination(30, TimeUnit.SECONDS);
    }
}
